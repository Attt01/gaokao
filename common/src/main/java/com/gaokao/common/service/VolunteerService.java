package com.gaokao.common.service;

import com.gaokao.common.meta.vo.volunteer.VolunteerFormVO;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/8
 * email: 757394026@qq.com
 */
public interface VolunteerService {

    Long create(Long userId, List<Long> subject, Long score);

    Long setVolunteer(Long userId, Long formId, Long position, Long volunteerId);

    Long updateVolunteerFormName(Long userId, Long formId, String newName);

    List<VolunteerFormVO> list(Long userId);

}
