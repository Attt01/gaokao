package com.gaokao.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付密钥
     */
    private String mchKey;


    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;


    private String payCallbackUrl;


    private boolean sanbox;

    /**
     * 二维码key和过期时间
     */
    private String qrcodeKey;
    private long qrcodeExpire;


    private String spbillCreateIp;

    /**
     * 交易方式
     */
    private String tradeType;
    /**
     * 统一下单地址
     */
    private String placeOrderUrl;
    /**
     * 商户秘钥
     */
    private String SecretKey;
}
