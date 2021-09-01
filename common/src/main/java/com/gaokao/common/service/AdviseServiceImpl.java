package com.gaokao.common.service;

import com.gaokao.common.dao.AdviseDao;
import com.gaokao.common.dao.FormVolunteerDao;
import com.gaokao.common.dao.ScoreRankDao;
import com.gaokao.common.dao.UserFormDao;
import com.gaokao.common.enums.Subject;
import com.gaokao.common.meta.po.FormVolunteer;
import com.gaokao.common.meta.po.ScoreRank;
import com.gaokao.common.meta.po.UserForm;
import com.gaokao.common.meta.po.Volunteer;
import com.gaokao.common.meta.vo.advise.AutoGenerateFormParams;
import com.gaokao.common.meta.vo.advise.FilterParams;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import com.gaokao.common.meta.vo.volunteer.UserFormDetailVO;
import com.gaokao.common.meta.vo.volunteer.VolunteerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MaeYon-Z
 *  date 2021-08-09
 */
@Slf4j
@Service
public class AdviseServiceImpl implements AdviseService{

    @Autowired
    private ScoreRankDao scoreRankDao;

    @Autowired
    private AdviseDao adviseDao;

    @Autowired
    private UserFormDao userFormDao;

    @Autowired
    private FormVolunteerDao formVolunteerDao;

    @Override
    public Integer getUserRank(Integer score){
        Integer t = scoreRankDao.getTopScore();
        Integer l = scoreRankDao.getLowestScore();
        if(score > t)
            score = t;
        else if(score < l)
            score = l;
        ScoreRank scoreRank = scoreRankDao.findAllByScore(score);
        return scoreRank.getTotalNums();
    }

    @Override
    public Integer getRate(Integer score, Integer guess){
        Integer rank = getUserRank(score);
        Integer dif = Math.abs(rank - guess);
        Integer rate = 0;
        if(rank > guess){//用户排名大于预估最低录取名次
            if(dif < 1000)
                rate = 60 - dif/100;
            else
                rate = 50 - (dif-1000)/50;
            if (rate < 10)
                rate = 10;
        }
        else if(rank <= guess){//用户排名小于预估最低录取名次
            if(dif < 1000)
                rate = 60 + dif/100;
            else
                rate = 70 + (dif-1000)/50;
            if(rate > 95)
                rate = 95;
        }
        return rate;
    }

    @Override
    public boolean filter(FilterParams filterParams, VolunteerVO volunteerVO){
        return true;
    }

    @Override
    public List<AdviseVO> listAll(FilterParams filterParams){
        List<AdviseVO> adviseVOList = new ArrayList<>();
        List<Volunteer> volunteerList = adviseDao.getAllVolunteer();
        volunteerList.forEach(volunteer -> {
            VolunteerVO volunteerVO = new VolunteerVO();
            BeanUtils.copyProperties(volunteer, volunteerVO);
            List<Integer> res = new ArrayList<>();
            for(int i = 0; i < volunteer.getSubjectRestrictionDetail().length(); i += 2){
                res.add(Integer.parseInt(volunteer.getSubjectRestrictionDetail().substring(i, i + 1)));
            }
            volunteerVO.setSubjectRestrictionDetail(res);
            if(filter(filterParams, volunteerVO)){
                Integer rate = getRate(filterParams.getScore(), volunteer.getPosition());
                String rateDesc = "";
                if(rate <= 50)
                    rateDesc = "难录取";
                else if(rate > 50 && rate <= 60)
                    rateDesc = "可冲击";
                else if(rate > 60 && rate <= 80)
                    rateDesc = "较稳妥";
                else if(rate > 80 && rate < 95)
                    rateDesc = "可保底";
                else if(rate == 95)
                    rateDesc = "浪费分";
                AdviseVO adviseVO = new AdviseVO();
                adviseVO.setRate(rate);
                adviseVO.setRateDesc(rateDesc);
                adviseVO.setVolunteerVO(volunteerVO);
                adviseVOList.add(adviseVO);
            }
        });
        return adviseVOList.stream().sorted(Comparator.comparing(AdviseVO::getRate))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AdviseVO> list(FilterParams filterParams, Integer type, Integer page, Integer size){
        List<AdviseVO> adviseVOS = listAll(filterParams);
        if(type == 0)
            return new PageImpl<>(adviseVOS, PageRequest.of(page - 1, size), adviseVOS.size());
        Map<String, List<AdviseVO>> map = new HashMap<>();
        List<AdviseVO> adviseVOList = new ArrayList<>();
        adviseVOS.stream().collect(Collectors.groupingBy(AdviseVO::getRateDesc,Collectors.toList()))
                .forEach(map::put);
        switch (type){
            case 1:
                adviseVOList = map.get("可冲击");
                break;
            case 2:
                adviseVOList = map.get("较稳妥");
                break;
            case 3:
                adviseVOList = map.get("可保底");
                break;
            default:
                adviseVOList = adviseVOS;
            }
            adviseVOList.sort(Comparator.comparing(AdviseVO::getRate).reversed());
            return new PageImpl<>(adviseVOList, PageRequest.of(page - 1, size), adviseVOList.size());
    }

    @Override
    public UserFormDetailVO generateVoluntForm(Long userId, AutoGenerateFormParams autoGenerateFormParams){
        //首先向tb_user_form中添加一条数据，表示生成一张表
        Date date = new Date();
        Long timestamp = date.getTime();
        UserForm userForm = new UserForm();
        userForm.setUserId(userId);
        userForm.setName(autoGenerateFormParams.getScore() + "-新建志愿表");
        userForm.setScore(autoGenerateFormParams.getScore().longValue());
        userForm.setSubject(autoGenerateFormParams.getSubject());
        userForm.setGeneratedType(true);
        userForm.setCurrent(true);
        userForm.setGeneratedTime(timestamp);
        userFormDao.save(userForm);
        UserForm userForm1 = userFormDao.findForm(userId, timestamp);  //获取刚生成的表的信息

        //接下来向tb_form_volunteer表中插入新自动生成的表的志愿
        FilterParams filterParams = new FilterParams();
        filterParams.setScore(autoGenerateFormParams.getScore());
        filterParams.setSubject(autoGenerateFormParams.getSubject());
        filterParams.setOther(autoGenerateFormParams.getOther());
        List<AdviseVO> chongList = list(filterParams, 1, 1, 10).getContent();
        List<AdviseVO> wenList = list(filterParams, 2, 1, 10).getContent();
        List<AdviseVO> baoList = list(filterParams, 3, 1, 10).getContent();
        List<VolunteerVO> volunteerVOList = new ArrayList<>();
        if(chongList.size() > autoGenerateFormParams.getChongRate() && baoList.size() > autoGenerateFormParams.getBaoRate()
            && wenList.size() > autoGenerateFormParams.getWenRate()){
            for(int i = 0; i < autoGenerateFormParams.getChongRate(); i++){
                volunteerVOList.add(chongList.get(i).getVolunteerVO());
            }
            for(int i = 0; i < autoGenerateFormParams.getWenRate(); i++){
                volunteerVOList.add(wenList.get(i).getVolunteerVO());
            }
            for(int i = 0; i < autoGenerateFormParams.getBaoRate(); i++){
                volunteerVOList.add(baoList.get(i).getVolunteerVO());
            }
        }
        for(int i = 0; i < 96; i++){
            FormVolunteer formVolunteer = new FormVolunteer();
            formVolunteer.setFormId(userForm1.getId());
            formVolunteer.setVolunteerSection(volunteerVOList.get(i).getVolunteerSection());
            formVolunteer.setVolunteerPosition(i + 1);
            formVolunteer.setVolunteerId(volunteerVOList.get(i).getId());
            formVolunteerDao.save(formVolunteer);
            volunteerVOList.get(i).setVolunteerPosition(i + 1);
        }

        //接下来构造返回对象
        UserFormDetailVO userFormDetailVO = new UserFormDetailVO();
        BeanUtils.copyProperties(userForm, userFormDetailVO);
        userFormDetailVO.setVolunteerList(volunteerVOList);
        List<String> subject = new ArrayList<>();
        for(int i = 0; i < userForm.getSubject().length(); i += 2){
            subject.add(Subject.getDescByCode(Integer.parseInt(userForm.getSubject().substring(i, i + 1))));
        }
        userFormDetailVO.setSubject(subject);
        return userFormDetailVO;
    }

}
