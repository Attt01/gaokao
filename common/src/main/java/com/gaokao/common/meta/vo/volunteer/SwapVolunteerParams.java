package com.gaokao.common.meta.vo.volunteer;

import lombok.Data;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
@Data
public class SwapVolunteerParams {

    private Long userId;

    private Long formId;

    /**
     * 需要交换的志愿的位置
     */
    private Long firstVolunteerPosition;

    /**
     * 需要交换的志愿的id
     */
    private Long firstVolunteerId;


    /**
     * 目标志愿的位置
     */
    private Long secondVolunteerPosition;

    /**
     * 目标志愿的id
     */
    private Long secondVolunteerId;

}
