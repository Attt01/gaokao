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
            int guess = guessRank.getGuessRank();
            int rate = guessRankList.size();
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
