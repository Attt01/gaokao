package com.gaokao.common.service;

import com.gaokao.common.meta.vo.advise.AutoGenerateFormParams;
import com.gaokao.common.meta.vo.advise.FilterParams;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import com.gaokao.common.meta.vo.volunteer.UserFormDetailVO;
import com.gaokao.common.meta.vo.volunteer.VolunteerVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author MaeYon-Z
 *  date 2021-08-09
 */
public interface AdviseService {

    //根据分数获得用户排名
    Integer getUserRank(Integer score);


    //输出符合条件的全部推荐
    List<AdviseVO> listAll(FilterParams filterParams);

    //获得录取概率
    Integer getRate(Integer score, Integer guessRank);

    //根据筛选条件进行判断
    boolean filter(FilterParams filterParams, VolunteerVO volunteerVO);

    /*
    * 按照type分类输出，type取值为：
    *   0：全部
    *   1：可冲击
    *   2：较稳妥
    *   3：可保底
    * */
    Page<AdviseVO> list(FilterParams filterParams, Integer type, Integer page, Integer size);

    //生成志愿表
    UserFormDetailVO generateVoluntForm(Long userId, AutoGenerateFormParams autoGenerateFormParams);
}
