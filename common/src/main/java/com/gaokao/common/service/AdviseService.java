package com.gaokao.common.service;

import com.gaokao.common.meta.po.GuessRank;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author MaeYon-Z
 *  date 2021-08-09
 */
public interface AdviseService {

    //根据分数获得用户排名
    int getUserRank(Integer score);


    //根据分数获得用户报考各学校专业的成功率
    Page<AdviseVO> list(Integer score, Integer page, Integer size);
}
