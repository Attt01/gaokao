package com.gaokao.common.meta.vo.volunteer;

import lombok.Data;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
@Data
public class DeleteVolunteerParams {

    private Long userId;

    private Long formId;

    /**
     * 第几志愿
     */
    private Long volunteerPosition;

}
