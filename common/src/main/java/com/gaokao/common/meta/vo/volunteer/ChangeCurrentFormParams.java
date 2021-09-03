package com.gaokao.common.meta.vo.volunteer;

import lombok.Data;

/**
 * @author attack204
 * date:  2021/8/23
 * email: 757394026@qq.com
 */
@Data
public class ChangeCurrentFormParams {

    /**
     * 之前的志愿表id
     */
    private Long preFormId;

    /**
     * 新的当前志愿表id
     */
    private Long newFormId;

}