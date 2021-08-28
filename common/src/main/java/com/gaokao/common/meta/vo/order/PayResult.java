package com.gaokao.common.meta.vo.order;

import com.gaokao.common.enums.PayType;
import lombok.Data;

/**
 * @author wyc-0705
 * date: 2021/8/23
 * desc: 订单支付结果
 */
@Data
public class PayResult {
    /**
     * 支付方法
     */
    private PayType payType;
    /**
     *时间戳
     */
    private String timeStamp;
    /**
     *生成签名的随机字符串
     */
    private String nonceStr;
    /**
     *预支付id
     */
    private String prepayId;
    /**
     *
     */
    private String paySign;
    /**
     *
     */
    private String signType;
}
