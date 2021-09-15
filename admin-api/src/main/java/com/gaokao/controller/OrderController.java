package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.order.*;
import com.gaokao.common.meta.vo.user.UserMemberVO;
import com.gaokao.common.service.OrderService;
import com.gaokao.common.utils.IPUtils;
import com.gaokao.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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


    @PostMapping("/preOrder")
    public AjaxResult<PreOrderResult> preOrder(@RequestBody PreOrderParam param) {
        Long userId = UserUtils.getUserId();
        PreOrderResult preOrder = orderService.preOrder(param, userId);
        return AjaxResult.SUCCESS(preOrder);
    }

    @PostMapping("/submit")
    public AjaxResult<String> submit(@RequestBody SubmitOrderParam param) {
        Long userId = UserUtils.getUserId();
        Long orderId = orderService.submit(param, userId);
        return AjaxResult.SUCCESS(String.valueOf(orderId));
    }

    @PostMapping("/pay")
    public AjaxResult<PayResult> pay(@RequestBody PayParam param, HttpServletRequest request) {
        Long userId = UserUtils.getUserId();
        String ip = IPUtils.getRequestClientRealIP(request);
        PayResult payResult = orderService.pay(param, ip, userId);
        return AjaxResult.SUCCESS(payResult);
    }

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
