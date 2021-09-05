package com.gaokao.common.meta.vo.order;

import lombok.Data;

/**
 * @author wyc-0705
 * date: 2021/8/23
 * desc: 预下单参数
 */
@Data
public class PreOrderParam {
    private Integer totalPrice;

    private long userId;
}
