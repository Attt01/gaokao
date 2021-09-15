package com.gaokao.common.service.admin;

import com.alibaba.fastjson.JSON;
import com.gaokao.common.dao.AdviseDao;
import com.gaokao.common.dao.UserStarDao;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.po.UserStar;
import com.gaokao.common.meta.po.Volunteer;
import com.gaokao.common.meta.vo.volunteer.VolunteerVO;
import com.gaokao.common.service.UserStarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaeYon-Z
 * @create 2021-09-14
 */
@Slf4j
@Service
public class UserStarServiceImpl implements UserStarService {

    @Autowired
    private UserStarDao userStarDao;

    @Autowired
    private AdviseDao adviseDao;

    @Override
    public Page<VolunteerVO> getUserStarList(Long userId, Integer page, Integer size){
        UserStar userStar = userStarDao.getUserStars(userId);
        List<VolunteerVO> volunteerVOList = new ArrayList<>();
        if(userStar == null){
            return new PageImpl<>(volunteerVOList, PageRequest.of(page - 1, size), volunteerVOList.size());
        }
        String userStars = userStar.getStars();
        List<Long> userStarList = JSON.parseArray(userStars, Long.class);
        userStarList.forEach(id -> {
            Volunteer volunteer = adviseDao.getVolunteer(id);
            VolunteerVO volunteerVO = new VolunteerVO();
            BeanUtils.copyProperties(volunteer, volunteerVO);
            if(volunteer.getVolunteerSection() == 1){
                volunteerVO.setVolunteerSection(true);
            }
            else{
                volunteerVO.setVolunteerSection(false);
            }
            volunteerVO.setSubjectRestrictionDetail(JSON.parseArray(volunteer.getSubjectRestrictionDetail(), Integer.class));
            volunteerVO.setMyStar(true);
            volunteerVOList.add(volunteerVO);
        });
        Integer fromIndex = (page - 1)*size;
        Integer toIndex = fromIndex + size;
        if(toIndex >= volunteerVOList.size()){
            toIndex = volunteerVOList.size();
        }
        List<VolunteerVO> volunteerVOS = volunteerVOList.subList(fromIndex, toIndex);
        return new PageImpl<>(volunteerVOS, PageRequest.of(page - 1, size), volunteerVOList.size());
    }

    @Override
    public Boolean star(Long userId, Long volunteerId){
        UserStar userStar = userStarDao.getUserStars(userId);
        if(userStar == null){
            UserStar userStar1 = new UserStar();
            userStar1.setUserId(userId);
            userStar1.setStars("[" + volunteerId + "]");
            userStarDao.save(userStar1);
            return true;
        }
        String stars = userStar.getStars();
        List<Long> userStarList = JSON.parseArray(stars, Long.class);
        if(userStarList.contains(volunteerId)){
            userStarList.remove(volunteerId);
            stars = JSON.toJSONString(userStarList);
            UserStar userStar1 = new UserStar();
            userStar1.setId(userStar.getId());
            userStar1.setUserId(userStar.getUserId());
            userStar1.setStars(stars);
            userStarDao.save(userStar1);
            return true;
        }else {
            userStarList.add(volunteerId);
            stars = JSON.toJSONString(userStarList);
            UserStar userStar1 = new UserStar();
            userStar1.setId(userStar.getId());
            userStar1.setUserId(userStar.getUserId());
            userStar1.setStars(stars);
            userStarDao.save(userStar1);
            return true;
        }
    }
}
