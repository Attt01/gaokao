package com.gaokao.common.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gaokao.common.dao.VolunteerDao;
import com.gaokao.common.meta.po.VolunteerForm;
import com.gaokao.common.meta.vo.volunteer.VolunteerFormVO;
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
 * @author attack204
 * date:  2021/8/8
 * email: 757394026@qq.com
 */
@Slf4j
@Service
public class VolunteerServiceImpl implements VolunteerService{

    @Autowired
    private VolunteerDao volunteerDao;

    @Override
    public Long create(Long userId, List<Long> subject, Long score) {
        VolunteerForm volunteerForm = new VolunteerForm();
        volunteerForm.setUserId(userId);
        volunteerForm.setSubject(JSON.toJSONString(subject));
        volunteerForm.setScore(score);
        return volunteerDao.save(volunteerForm).getId();
    }


    @Override
    public Long setVolunteer(Long userId, Long formId, Long position, Long volunteerId) {

        VolunteerForm volunteerForm = volunteerDao.findById(formId).orElse(null);

        if(volunteerForm == null) {
            return -1L;
        }

        List<Long> volunteerListArray = JSON.parseArray(volunteerForm.getVolunteerList(), Long.class);
        volunteerListArray.set(position.intValue() - 1, volunteerId);
        String volunteerListString = JSON.toJSONString(volunteerListArray);

        volunteerForm.setVolunteerList(volunteerListString);

        return volunteerDao.save(volunteerForm).getId();
    }

    @Override
    public Long updateVolunteerFormName(Long userId, Long formId, String newName) {

        VolunteerForm volunteerForm = volunteerDao.findById(formId).orElse(null);

        if(volunteerForm == null) {
            return -1L;
        }

        volunteerForm.setName(newName);

        return volunteerDao.save(volunteerForm).getId();
    }

    @Override
    public List<VolunteerFormVO> list(Long userId) {
        List<VolunteerForm> list = volunteerDao.findAllByUserId(userId);

        List<VolunteerFormVO> listVO = new ArrayList<>(list.size());

        list.forEach((item) -> {
            VolunteerFormVO volunteerFormVO = new VolunteerFormVO();
            BeanUtils.copyProperties(item, volunteerFormVO);
            volunteerFormVO.setSubject(JSON.parseArray(item.getVolunteerList(), Long.class));
            listVO.add(volunteerFormVO);
        });

        return listVO;

    }
}
