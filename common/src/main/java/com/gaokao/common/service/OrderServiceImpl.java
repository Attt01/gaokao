package com.gaokao.common.service;

import com.alibaba.fastjson.JSON;
import com.gaokao.common.config.WxPayProperties;
import com.gaokao.common.dao.OrderDao;
import com.gaokao.common.dao.OrderPayDao;
import com.gaokao.common.dao.OrderRefundDao;
import com.gaokao.common.dao.UserMemberDao;
import com.gaokao.common.enums.OrderStatus;
import com.gaokao.common.exceptions.BusinessException;
import com.gaokao.common.meta.bo.H5SceneInfo;
import com.gaokao.common.meta.po.Order;
import com.gaokao.common.meta.po.OrderPay;
import com.gaokao.common.meta.po.OrderRefund;
import com.gaokao.common.meta.po.UserMember;
import com.gaokao.common.meta.vo.order.*;
import com.gaokao.common.utils.CreateSignUtils;
import com.gaokao.common.utils.RandomUtils;
import com.github.binarywang.wxpay.bean.request.WxPayOrderCloseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserMemberDao userMemberDao;

    @Autowired
    private IdService idService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private OrderPayDao orderPayDao;

    @Autowired
    private OrderRefundDao orderRefundDao;

    @Autowired
    private WxPayService wxService;

    @Autowired
    private WxPayProperties wxPayProperties;

    private final static String RECEIVE_ORDER = "YES";

    private final static String NOT_RECEIVE_ORDER = "NO";

    @Override
    public PreOrderResult preOrder(PreOrderParam param, Long userId) {
        PreOrderResult preOrderResult = new PreOrderResult();
        UserMember userMember= userMemberDao.findUserMemberById(userId);
        if (userMember != null) {
            preOrderResult.setUserId(userMember.getId());
            preOrderResult.setTotalPrice(param.getTotalPrice());
        }
        else {
            throw new UsernameNotFoundException("用户不存在");
        }
        return preOrderResult;
    }

    @Override
    public Long submit(SubmitOrderParam param, Long userId) {
        Long orderId = idService.genOrderId(userId);
        Long currentTime = System.currentTimeMillis();
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setTotalPrice(param.getTotalPrice());
        order.setStatus(OrderStatus.READY_FOR_PAY.getValue());
        order.setCreateTime(currentTime);
        return orderId;
    }

        @Override
    public PayResult pay(PayParam param, String clientIp, Long userId) {
        Order order = orderDao.findById(param.getOrderId()).orElse(null);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }

        if (order.getStatus() != OrderStatus.READY_FOR_PAY.getValue()) {
            throw new BusinessException("订单状态不对");
        }

        H5SceneInfo sceneInfo = new H5SceneInfo();
        H5SceneInfo.H5 h5_info = new H5SceneInfo.H5();
        h5_info.setType("Wap");
        h5_info.setWapUrl(wxPayProperties.getWapUrl());
        h5_info.setWapName(wxPayProperties.getWapName());
        sceneInfo.setH5Info(h5_info);


        String outTradeNo = RandomUtils.randomUUID();
        UserMember userMember = userMemberDao.findUserMemberById(userId);

        //https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_20&index=1
        WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
                .body("收银台-会员订单支付")
                .outTradeNo(outTradeNo)
                .totalFee(order.getRealPrice())
                .spbillCreateIp(clientIp)
                .notifyUrl(wxPayProperties.getPayCallbackUrl())
                .tradeType(WxPayConstants.TradeType.JSAPI)
                .openid(userMember.getWxOpenId())
                .sceneInfo(JSON.toJSONString(sceneInfo))
                .build();

        WxPayUnifiedOrderResult payUnifiedOrderResult;
        try {
            payUnifiedOrderResult = this.wxService.unifiedOrder(request);
        } catch (Exception e) {
            log.error("[pay] pay failed.request={}", request, e);
            throw new BusinessException("微信支付失败");
        }

        order.setPayType(param.getPayType().getValue());
        order.setPayMoney(order.getRealPrice());
        order.setPayTime(System.currentTimeMillis());
        order.setOutTradeNo(outTradeNo);
        orderDao.save(order);
        OrderPay orderPay = orderPayDao.findByOrderId(order.getId());
        if (orderPay == null) {
            orderPay = new OrderPay();
            orderPay.setId(idService.genOrderPayId(param.getOrderId()));
        }
        orderPay.setStatus(OrderStatus.READY_FOR_PAY.getValue());
        orderPay.setOrderId(param.getOrderId());
        orderPay.setPayType(param.getPayType().getValue());
        orderPay.setPayMoney(order.getRealPrice());
        orderPay.setPayTime(System.currentTimeMillis());
        orderPay.setOutTradeNo(outTradeNo);
        orderPayDao.save(orderPay);
        // 使用MD5加密prepay_id等字段返回前端供发起支付
        SortedMap<String, String> finalPackage = new TreeMap<>();
        String packages = "prepay_id=" + payUnifiedOrderResult.getPrepayId();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        finalPackage.put("appId", wxPayProperties.getAppId());
        finalPackage.put("timeStamp", timeStamp);
        finalPackage.put("nonceStr", payUnifiedOrderResult.getNonceStr());
        finalPackage.put("package", packages);
        finalPackage.put("signType", "MD5");
        String paySign = CreateSignUtils.createSign(finalPackage, wxPayProperties.getMchKey());

        PayResult payResult = new PayResult();
        payResult.setPayType(param.getPayType());
        payResult.setNonceStr(finalPackage.get("nonceStr"));
        payResult.setTimeStamp(finalPackage.get("timeStamp"));
        payResult.setPrepayId(payUnifiedOrderResult.getPrepayId());
        payResult.setSignType("MD5");
        payResult.setPaySign(paySign);
        return payResult;
    }

    @Override
    public String wxPayNotify(String content) {
        return null;
    }

    @Override
    public void close(Long orderId, Long userId) {
        Order order = orderDao.findById(orderId).orElse(null);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != OrderStatus.READY_FOR_PAY.getValue()) {
            throw new BusinessException("订单状态不对");
        }
        OrderPay orderPay = orderPayDao.findByOrderId(orderId);
        if (orderPay == null) {
            throw new BusinessException("该订单没有关联的支付单！");
        }
        if (orderPay.getStatus() != OrderStatus.READY_FOR_PAY.getValue()) {
            throw new BusinessException("订单状态不对");
        }

        //先删除支付订单记录表中的记录
        orderPayDao.deleteByOrderId(orderId);

        WxPayOrderCloseResult payOrderCloseResult;
        WxPayOrderCloseRequest wxPayOrderCloseRequest = WxPayOrderCloseRequest.newBuilder()
                .outTradeNo(orderPay.getOutTradeNo())
                .build();
        try {
            payOrderCloseResult = this.wxService.closeOrder(wxPayOrderCloseRequest);
        } catch (Exception e) {
            log.error("[close] close failed.request={}", wxPayOrderCloseRequest, e);
            throw new BusinessException("关闭订单失败:" + e);
        }

        order.setStatus(OrderStatus.CLOSED.getValue());
        orderDao.save(order);
    }

    @Override
    public void applyRefund(RefundParam refundParam) {
        // 1.1 获取要退款的订单
        // 通过传入的退款参数中的orderId，利用orderDao实现查找要退款的订单
        Order order = orderDao.findById(refundParam.getOrderId()).orElse(null);
        // 1.2 获取订单后，需对获取的订单进行检查
        // a. 检查订单是否存在，检查订单对应用户与退款参数中的用户是不是同一个
        if (order == null || !order.getUserId().equals(refundParam.getUserId())) {
            throw new BusinessException("订单不存在");
        }
        // b.检查订单状态是否是申请退款状态
        if (order.getStatus() != OrderStatus.APPLY_FOR_REFUND.getValue()) {
            throw new BusinessException("订单状态不对");
        }
        OrderPay orderPay = orderPayDao.findByOrderId(refundParam.getOrderId());
        // 2.2 检查获取的支付信息是否有问题
        // a.检查订单支付信息是否为空
        if (orderPay ==null) {
            throw new BusinessException("该订单没有关联的支付单！");
        }
        // b.检查支付信息状态是否是申请退款或者支付成功
        if (orderPay.getStatus() != OrderStatus.APPLY_FOR_REFUND.getValue() && orderPay.getStatus() != OrderStatus.PAID_SUCCESS.getValue() ){
            throw new BusinessException("订单状态不对");
        }

        // 3. 创建新的退款信息，通过订单信息和支付信息为退款信息设置属性
        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setOrderId(order.getId());
        orderRefund.setRefundMoney(orderPay.getPayMoney());
        orderRefund.setStatus(OrderStatus.REFUND_WAITING_NOTIFY.getValue());
        orderRefundDao.save(orderRefund);
        //保存订单支付结果
        orderPay.setStatus(OrderStatus.REFUND_WAITING_NOTIFY.getValue());
        orderPayDao.save(orderPay);
        //保存订单
        orderDao.save(order);
    }

    @Override
    public String refundCallback(String content) {
        return null;
    }

    @Override
    public void cancel(Long orderId, Long userId) {
        Order order = orderDao.findById(orderId).orElse(null);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != OrderStatus.READY_FOR_PAY.getValue()) {
            throw new BusinessException("订单状态不对");
        }
        order.setStatus(OrderStatus.CANCELED.getValue());
        orderDao.save(order);
    }

    @Override
    public void completeOrder(Long orderId, Long userId) {
        Order order = orderDao.findById(orderId).orElse(null);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != OrderStatus.COMPLETED.getValue()) {
            throw new BusinessException("订单状态不对");
        }
        order.setStatus(OrderStatus.EVALUATED.getValue());
        orderDao.save(order);
        //完成之后变为vip
        UserMember userMember=userMemberDao.findUserMemberById(userId);
        userMember.setVipIsOrNot(true);
        userMemberDao.save(userMember);

    }

    @Override
    public OrderVO getByOrderId(Long id, Long userId) {
        return null;
    }

    @Override
    public Long allOrderNum() {
        return null;
    }

    @Override
    public int todaySaleVolume(Long todayZeroTime) {
        return 0;
    }

    @Override
    public void rejectRefund(Long orderId, String reasons) {
        Order order=orderDao.findById(orderId).orElse(null);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != OrderStatus.APPLY_FOR_REFUND.getValue()) {
            throw new BusinessException("订单状态不对");
        }
        order.setStatus(OrderStatus.REFUND_REJECT.getValue());
        order.setRefundReason(reasons);
        orderDao.save(order);
    }
}
