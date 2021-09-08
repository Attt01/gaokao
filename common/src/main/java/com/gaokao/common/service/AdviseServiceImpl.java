package com.gaokao.common.service;

import com.alibaba.fastjson.JSON;
import com.gaokao.common.dao.*;
import com.gaokao.common.enums.Subject;
import com.gaokao.common.meta.po.*;
import com.gaokao.common.meta.vo.advise.AutoGenerateFormParams;
import com.gaokao.common.meta.vo.advise.FilterParams;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import com.gaokao.common.meta.vo.volunteer.UserFormDetailVO;
import com.gaokao.common.meta.vo.volunteer.VolunteerVO;
import io.swagger.models.auth.In;
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

    @Autowired
    private FilterDataDao filterDataDao;

    private static List<VolunteerVO> volunteerVOList;

    private static Map<String, List<FilterData>> filterDataS = new HashMap<>();

    private Map<String, Boolean> conditionsMap = new HashMap<>();

    @Override
    public Integer getUserRank(Integer score){
        Integer totalNums = scoreRankDao.findTotalNumsByScore(score);
        if(totalNums == null){
            if(score > 400){
                totalNums = 50;
            }
            else {
                totalNums = 548762;
            }
        }
        return totalNums;
    }

    @Override
    public Integer getRate(Integer rank, Integer guess){
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

    private Integer findCommon(List<Integer> m, List<Integer> n){
        Integer res = 0;
        for(int i = 0; i < m.size(); i++){
            for (int j = 0; j < n.size(); j++){
                if(m.get(i) == n.get(j)){
                    res ++;
                }
            }
        }
        return res;
    }

    @Override
    public boolean filter(FilterParams filterParams, VolunteerVO volunteerVO){


        //首先判断选课是否符合
        List<Integer> voluntSubject = volunteerVO.getSubjectRestrictionDetail();
        List<Integer> subject = filterParams.getSubject();
        Integer commom = findCommon(voluntSubject, subject);
        switch(volunteerVO.getSubjectRestrictionType()){
            case 0:
                break;
            case 1:
                if(commom == 1){
                    break;
                }
                else {
                    return false;
                }
            case 2:
                if(commom == 2){
                    break;
                }
                else {
                    return false;
                }
            case 3:
                if(commom == 3){
                    break;
                }
                else {
                    return false;
                }
            case 4:
                if(commom >= 1){
                    break;
                }
                else{
                    return false;
                }
            case 5:
                if(commom >= 1){
                    break;
                }
                else {
                    return false;
                }
            case 6:
                if(commom >= 2){
                    break;
                }
                else {
                    return false;
                }
        }

        /*
           接下来判断录取批次、地区、大学类型、专业类型是否符合条件
                必须同时满足才可以返回true，只要一个不满足就返回false。
        */



        Integer section;
        if(volunteerVO.getVolunteerSection()){
            section = 1;
        }
        else {
            section = 2;
        }
        if(!conditionsMap.containsKey(section + "")){
            return false;
        }

        String city = volunteerVO.getCity();
        if(!conditionsMap.containsKey(city)){
            return false;
        }


        return true;

    }

    private void start(FilterParams filterParams){
        if(volunteerVOList == null){
            List<Volunteer> volunteerList = adviseDao.getAllVolunteer();
            List<VolunteerVO> volunteerVOS = new ArrayList<>();
            volunteerList.forEach(volunteer -> {
                VolunteerVO volunteerVO = new VolunteerVO();
                BeanUtils.copyProperties(volunteer, volunteerVO);
                if(volunteer.getVolunteerSection() == 1){
                    volunteerVO.setVolunteerSection(true);
                }
                else{
                    volunteerVO.setVolunteerSection(false);
                }
                volunteerVO.setSubjectRestrictionDetail(JSON.parseArray(volunteer.getSubjectRestrictionDetail(), Integer.class));
                volunteerVOS.add(volunteerVO);
            });
            volunteerVOList = volunteerVOS;
        }

        if(filterDataS.isEmpty()) {
            for (int i = 1; i <= 4; i++) {
                List<FilterData> secondList = filterDataDao.findSonsByFatherId(i);
                if (i == 1) {
                    filterDataS.put("批次", secondList);
                    continue;
                }
                if(i == 2){
                    List<FilterData> thirdList = new ArrayList<>();
                    for (int j = 0; j < secondList.size(); j++) {
                        thirdList.addAll(filterDataDao.findSonsByFatherId(secondList.get(j).getId()));
                    }
                    filterDataS.put("地区", thirdList);
                    continue;
                }
                if(i == 3){
                    for(int j = 0; j < secondList.size(); j++){
                        String label = secondList.get(j).getLabel();
                        if(label.equals("大学特色")){
                            List<FilterData> teSeList = filterDataDao.findSonsByFatherId(secondList.get(j).getId());
                            filterDataS.put(label, teSeList);
                        }
                        if(label.equals("办学性质")){
                            List<FilterData> xingZhiList = filterDataDao.findSonsByFatherId(secondList.get(j).getId());
                            filterDataS.put(label, xingZhiList);
                        }
                        if(label.equals("大学类型")){
                            List<FilterData> typeList = filterDataDao.findSonsByFatherId(secondList.get(j).getId());
                            filterDataS.put(label, typeList);
                        }
                    }
                }
            }
        }

        if(filterParams.getBatch().size() == 0){
            conditionsMap.put("1",true);
            conditionsMap.put("2",true);
        }else {
            for(int i = 0; i < filterParams.getBatch().size(); i++){
                FilterData filterData = filterDataDao.getOneById(filterParams.getBatch().get(i));
                String label = filterData.getLabel();
                if(label.equals("普通一段")){
                    conditionsMap.put("1", true);
                }else{
                    conditionsMap.put("2", true);
                }
            }
        }

        if(filterParams.getRegion().size() == 0){
            List<FilterData> regionList = filterDataS.get("地区");
            for(int i = 0; i < regionList.size(); i++){
                String label = regionList.get(i).getLabel();
                Integer j = label.indexOf("市");
                if(j != -1) {
                    label = label.substring(0, j);
                    conditionsMap.put(label, true);
                }
                else{
                    conditionsMap.put(label,true);
                }
            }
        }else{
            for(int i = 1; i < filterParams.getRegion().size(); i++){
                FilterData filterData = filterDataDao.getOneById(filterParams.getRegion().get(i));
                String label = filterData.getLabel();
                Integer j = label.indexOf("市");
                if(j != -1) {
                    label = label.substring(0, j);
                    conditionsMap.put(label, true);
                }
                else{
                    conditionsMap.put(label,true);
                }
            }
        }

        if(filterParams.getSchoolTeSe().size() == 0){
            List<FilterData> teSeList = filterDataS.get("大学特色");
            for(int i = 0; i < teSeList.size(); i++){
                String label = teSeList.get(i).getLabel();
                conditionsMap.put(label, true);
            }
        }else{
            for(int i = 0; i < filterParams.getSchoolTeSe().size(); i++){
                FilterData filterData = filterDataDao.getOneById(filterParams.getRegion().get(i));
                String label = filterData.getLabel();
                conditionsMap.put(label, true);
            }
        }

        if(filterParams.getSchoolXingZhi().size() == 0){
            List<FilterData> xingZhiList = filterDataS.get("办学性质");
            for(int i = 0; i < xingZhiList.size(); i++){
                String label = xingZhiList.get(i).getLabel();
                conditionsMap.put(label, true);
            }
        }else{
            for(int i = 0; i < filterParams.getSchoolXingZhi().size(); i++){
                FilterData filterData = filterDataDao.getOneById(filterParams.getRegion().get(i));
                String label = filterData.getLabel();
                conditionsMap.put(label, true);
            }
        }

        if(filterParams.getSchoolType().size() == 0){
            List<FilterData> typeList = filterDataS.get("大学类型");
            for(int i = 0; i < typeList.size(); i++){
                String label = typeList.get(i).getLabel();
                conditionsMap.put(label, true);
            }
        }else{
            for(int i = 0; i < filterParams.getSchoolType().size(); i++){
                FilterData filterData = filterDataDao.getOneById(filterParams.getRegion().get(i));
                String label = filterData.getLabel();
                conditionsMap.put(label, true);
            }
        }

    }


    @Override
    public List<AdviseVO> listAll(FilterParams filterParams){
        List<AdviseVO> adviseVOList = new ArrayList<>();

        start(filterParams);

        System.out.println(conditionsMap);

        Integer rank = getUserRank(filterParams.getScore());
        volunteerVOList.forEach(volunteerVO -> {
            if(filter(filterParams, volunteerVO)){
                Integer rate = getRate(rank, volunteerVO.getPosition());
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
    public Page<AdviseVO> list(FilterParams filterParams){
        List<AdviseVO> adviseVOS = listAll(filterParams);
        if(filterParams.getType() == 0){
            return new PageImpl<>(adviseVOS, PageRequest.of(filterParams.getPage() - 1, filterParams.getLimit()), adviseVOS.size());
        }
        Map<String, List<AdviseVO>> map = new HashMap<>();
        List<AdviseVO> adviseVOList = new ArrayList<>();
        adviseVOS.stream().collect(Collectors.groupingBy(AdviseVO::getRateDesc,Collectors.toList()))
                .forEach(map::put);
        switch (filterParams.getType()){
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
        return new PageImpl<>(adviseVOList, PageRequest.of(filterParams.getPage() - 1, filterParams.getLimit()), adviseVOList.size());
    }

    @Override
    public UserFormDetailVO generateVoluntForm(AutoGenerateFormParams autoGenerateFormParams){
        //首先向tb_user_form中添加一条数据，表示生成一张表
        Date date = new Date();
        Long timestamp = date.getTime();
        UserForm userForm = new UserForm();
        userForm.setUserId(autoGenerateFormParams.getUserId());
        userForm.setName(autoGenerateFormParams.getScore() + "-新建志愿表");
        userForm.setScore(autoGenerateFormParams.getScore());
        List<Integer> subject1 = autoGenerateFormParams.getSubject();
        String s = "";
        for(int i = 0; i < 3; i++){
            s += subject1.get(i);
            if(i != 2){
                s += ";";
            }
        }
        userForm.setSubject(s);
        userForm.setGeneratedType(true);
        userForm.setCurrent(true);
        userForm.setGeneratedTime(timestamp);
        userFormDao.save(userForm);
        UserForm userForm1 = userFormDao.findForm(autoGenerateFormParams.getUserId(), timestamp);  //获取刚生成的表的信息

        //接下来向tb_form_volunteer表中插入新自动生成的表的志愿
        List<AdviseVO> adviseVOList = listAll(autoGenerateFormParams);
        Map<String, List<AdviseVO>> map = new HashMap<>();
        adviseVOList.stream().collect(Collectors.groupingBy(AdviseVO::getRateDesc,Collectors.toList()))
                .forEach(map::put);
        List<AdviseVO> chongList = map.get("可冲击");
        List<AdviseVO> wenList = map.get("较稳妥");
        List<AdviseVO> baoList = map.get("可保底");
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
        List<FormVolunteer> formVolunteerList = new ArrayList<>();
        for(int i = 0; i < 96; i++){
            FormVolunteer formVolunteer = new FormVolunteer();
            formVolunteer.setFormId(userForm1.getId());
            formVolunteer.setVolunteerSection(volunteerVOList.get(i).getVolunteerSection());
            formVolunteer.setVolunteerPosition(i + 1);
            formVolunteer.setVolunteerId(volunteerVOList.get(i).getId());
            formVolunteerList.add(formVolunteer);
            volunteerVOList.get(i).setVolunteerPosition(i + 1);
        }
        formVolunteerDao.saveAll(formVolunteerList);

        //接下来构造返回对象
        UserFormDetailVO userFormDetailVO = new UserFormDetailVO();
        BeanUtils.copyProperties(userForm, userFormDetailVO);
        userFormDetailVO.setVolunteerList(volunteerVOList);
        userFormDetailVO.setScore(autoGenerateFormParams.getScore());
        List<String> subject = new ArrayList<>();
        for(int i = 0; i < userForm.getSubject().length(); i += 2){
            subject.add(Subject.getDescByCode(Integer.parseInt(userForm.getSubject().substring(i, i + 1))));
        }
        userFormDetailVO.setSubject(subject);
        return userFormDetailVO;
    }
    

}
