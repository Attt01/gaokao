package com.gaokao.common.meta.vo.volunteer;

import lombok.Data;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
@Data
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

}
