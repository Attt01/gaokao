package com.gaokao.controller;



import com.alibaba.druid.util.StringUtils;
import com.gaokao.common.config.WxPayProperties;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.po.Order;
import com.gaokao.common.meta.vo.order.*;
import com.gaokao.common.service.OrderService;
import com.gaokao.common.utils.IPUtils;
import com.gaokao.common.utils.JsonResult;
import com.gaokao.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author attack204
 * date:  2021/7/20
 * email: 757394026@qq.com
 * desc: 订单相关
 *
 * //TODO 订单这块没有配置，先不做
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private WxPayProperties wxPayProperties;

    @RequestMapping(value = "/notify/wxpay")
    public void wxpay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("支付成功后的微信支付异步通知");
        // 获取微信支付结果
        PayResult payResult = orderService.getWxPayResult(request.getInputStream());

        boolean isPaid = payResult.getReturn_code().equals("SUCCESS") ? true : false;
        // 查询该笔订单在微信那边是否成功支付
        // 支付成功，处理后同步返回给微信参数
        PrintWriter writer = response.getWriter();
        if (isPaid) {
            String merchantOrderId = payResult.getOut_trade_no();            // 商户订单号
            String wxFlowId = payResult.getTransaction_id();
            Integer paidAmount = payResult.getTotal_fee();

            System.out.println("================================= 支付成功 =================================");
            log.info("* 商户订单号: {}", merchantOrderId);
            log.info("* 微信订单号: {}", wxFlowId);
            log.info("* 实际支付金额: {}", paidAmount);
            log.info("*****************************************************************************");
            // 通知订单服务，该订单已支付
            System.out.println("================================= 通知订单服务，该订单已支付 =================================");

            // 通知微信已经收到消息，不要再给我发消息了，否则微信会10连击调用本接口
            String noticeStr = setXML("SUCCESS", "");
            writer.write(noticeStr);
            writer.flush();
        } else {
            System.out.println("================================= 支付失败 =================================");

            // 支付失败
            String noticeStr = setXML("FAIL", "");
            writer.write(noticeStr);
            writer.flush();
        }
    }
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

    /**
     * 微信扫码支付页面
     * @return
     */
    @PostMapping(value = "/getWXPayQRCode")
    public JsonResult getWXPayQRCode(String merchantUserId, String merchantOrderId) throws Exception {
        // 根据订单ID和用户ID查询订单详情
//        Orders waitPayOrder = paymentOrderService.queryOrderByStatus(merchantOrderId,merchantUserId,PaymentStatus.WAIT_PAY.type);
        Order waitPayOrder = new Order();
        // 商品描述
        String body = "付款用户[" + merchantUserId + "]";
        // 商户订单号
        String out_trade_no = merchantOrderId;
        // 使用map代替redis
        Map<String, String> redisCacheMap = new HashMap<>();
        // 从redis中去获得这笔订单的微信支付二维码，如果订单状态没有支付没有就放入，这样的做法防止用户频繁刷新而调用微信接口
        if (waitPayOrder != null) {
            String qrCodeUrl = redisCacheMap.get(wxPayProperties.getQrcodeKey() + ":" + merchantOrderId);

            if (StringUtils.isEmpty(qrCodeUrl)) {
                // 订单总金额，单位为分
                String total_fee = "1"; // 测试金额
                // 统一下单
                PreOrderResult preOrderResult = orderService.preOrder(out_trade_no, total_fee);
                qrCodeUrl = preOrderResult.getCode_url();
            }

            PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
            paymentInfoVO.setAmount(1);
            paymentInfoVO.setMerchantOrderId(merchantOrderId);
            paymentInfoVO.setMerchantUserId(merchantUserId);
            paymentInfoVO.setQrCodeUrl(qrCodeUrl);

            redisCacheMap.put(wxPayProperties.getQrcodeKey() + ":" + merchantOrderId, qrCodeUrl);


            return JsonResult.ok(paymentInfoVO);
        } else {
            return JsonResult.errorMsg("该订单不存在，或已经支付");
        }
    }

    @PostMapping("/submit")
    public AjaxResult<String> submit(@RequestBody SubmitOrderParam param) {
        Long userId = UserUtils.getUserId();
        Long orderId = orderService.submit(param, userId);
        return AjaxResult.SUCCESS(String.valueOf(orderId));
    }

//    h5支付使用 先注释掉
//    @PostMapping("/pay")
//    public AjaxResult<PayResult> pay(@RequestBody PayParam param, HttpServletRequest request) {
//        Long userId = UserUtils.getUserId();
//        String ip = IPUtils.getRequestClientRealIP(request);
//        PayResult payResult = orderService.pay(param, ip, userId);
//        return AjaxResult.SUCCESS(payResult);
//    }

    @PostMapping("/success")
    public AjaxResult<Boolean> success(@RequestParam String orderId) {
        //获取当前用户id
        Long userId = UserUtils.getUserId();
        orderService.completeOrder(Long.valueOf(orderId), userId);
        AjaxResult<Boolean> result = AjaxResult.SUCCESS(true);
        result.setMsg("成功");
        return result;
    }

    @PostMapping("/close")
    public AjaxResult<Boolean> close(@RequestParam String orderId) {
        Long userId = UserUtils.getUserId();
        orderService.close(Long.valueOf(orderId), userId);
        return AjaxResult.SUCCESS(true);
    }

    @PostMapping("/cancel")
    public AjaxResult<Boolean> cancel(@RequestParam String orderId) {
        Long userId = UserUtils.getUserId();
        orderService.cancel(Long.valueOf(orderId), userId);
        AjaxResult<Boolean> result = AjaxResult.SUCCESS(true);
        result.setMsg("取消成功");
        return result;
    }

    @PostMapping("/wxPayNotify")
    public String wxPayNotify(@RequestBody String xmlData) {
        return orderService.wxPayNotify(xmlData);
    }

    @GetMapping("/")
    @PreAuthorize("hasPermission('admin','view')")
    public AjaxResult<Page<OrderVO>> list(@RequestParam(required = false,defaultValue = "") Long orderId,
                                              @RequestParam(required = false, defaultValue = "") Long userId,
                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return AjaxResult.SUCCESS(orderService.list(orderId, userId, page, size));
    }
}
