package com.gaokao.common.service;

import com.gaokao.common.meta.po.OrderRefund;
import com.gaokao.common.meta.vo.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public PreOrderResult preOrder(PreOrderParam param, Long userId) {
        return null;
    }

    @Override
    public Long submit(SubmitOrderParam param, Long userId) {
        return null;
    }

    @Override
    public PayResult pay(PayParam param, String clientIp, Long userId) {
        return null;
    }

    @Override
    public String wxPayNotify(String content) {
        return null;
    }

    @Override
    public void close(Long orderId, Long userId) {

    }

    @Override
    public void applyRefund(OrderRefund refundParam) {

    }

    @Override
    public String refundCallback(String content) {
        return null;
    }

    @Override
    public void cancel(Long orderId, Long userId) {

    }

    @Override
    public void completeOrder(Long orderId, Long userId) {

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

    }
}
