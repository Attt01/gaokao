package com.gaokao.common.service;

import com.gaokao.common.meta.vo.volunteer.VolunteerVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-09-14
 */
public interface UserStarService {

    Page<VolunteerVO> getUserStarList(Long userId, Integer page, Integer size);

    Boolean star(Long userId, Long volunteerId);

}
