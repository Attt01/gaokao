package com.gaokao.common.meta.vo.volunteer;

import lombok.Data;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
@Data
public class DownVolunteerParams {

    private Long userId;

    private Long formId;

    /**
     * 需要上移的志愿
     */
    private Long volunteerPosition;

    /**
     * 需要下移的志愿的id
     */
    private Long volunteerId;

}
