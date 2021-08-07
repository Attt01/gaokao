package com.gaokao.common.meta.vo.volunteer;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
public class SwapVolunteerParams {

    private Long userId;

    private Long formId;

    /**
     * 需要交换的志愿
     */
    private Long firstVolunteerPosition;

    /**
     * 目标志愿
     */
    private Long secondVolunteerPosition;

}
