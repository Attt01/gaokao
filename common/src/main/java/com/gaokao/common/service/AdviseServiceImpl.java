package com.gaokao.common.service;

import com.gaokao.common.dao.AdviseDao;
import com.gaokao.common.dao.ScoreRankDao;
import com.gaokao.common.meta.po.GuessRank;
import com.gaokao.common.meta.po.ScoreRank;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int getUserRank(Integer score){
        int t = scoreRankDao.getTopScore();
        int l = scoreRankDao.getLowestScore();
        if(score > t)
            score = t;
        else if(score < l)
            score = l;
        ScoreRank scoreRank = scoreRankDao.findAllByScore(score);
        return scoreRank.getTotalNums();

    }

    @Override
    public Page<AdviseVO> list(Integer score, Integer page, Integer size){
        List<GuessRank> guessRankList = adviseDao.findGuessRankS();
        //接下来构造VO对象
        List<AdviseVO> adviseVOS = new ArrayList<>(guessRankList.size());
        int rank = getUserRank(score);
        guessRankList.forEach(guessRank -> {
            AdviseVO adviseVO = new AdviseVO();
            String universityName = adviseDao.findUniversityNameById(guessRank.getUniversityId());
            String universityCode = adviseDao.findUniversityCodeById(guessRank.getUniversityId());
            String majorName = adviseDao.findMajorNameByID(guessRank.getUniversityId(), guessRank.getMajorCode());
            String majorCode = guessRank.getMajorCode();
            int guess = guessRank.getGuessRank();   //guess为预估录取名次
            //接下来根据专业预估录取名次guess与用户名次rank来计算录取几率rate
            int dif = Math.abs(rank - guess);
            int rate = 0;
            if(rank > guess){//用户排名大于预估最低录取名次
                if(dif < 1000)
                    rate = 70 - dif/100;
                else
                    rate = 60 - (dif-1000)/50;
                if (rate < 10)
                    rate = 10;
            }
            else if(rank <= guess){//用户排名小于预估最低录取名次
                if(dif < 1000)
                    rate = 70 + dif/100;
                else
                    rate = 80 + (dif-1000)/50;
                if(rate > 95)
                    rate = 95;
            }
            adviseVO.setUniversityName(universityName);
            adviseVO.setUniversityCode(universityCode);
            adviseVO.setMajorName(majorName);
            adviseVO.setMajorCode(majorCode);
            adviseVO.setGuessRank(guess);
            adviseVO.setRate(rate);
            adviseVOS.add(adviseVO);
        });
        return new PageImpl<>(adviseVOS, PageRequest.of(page - 1, size), adviseVOS.size());
    }
}
