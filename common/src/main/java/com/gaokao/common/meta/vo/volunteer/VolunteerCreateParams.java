package com.gaokao.common.meta.vo.volunteer;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
public class VolunteerCreateParams {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 选考科目
     */
    private List<Long> subject;

    /**
     * 分数
     */
    private Long score;

    /**
     * 位次
     */
    private Long rank;

}
