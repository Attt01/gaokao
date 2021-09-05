package com.gaokao.common.dao;

import com.gaokao.common.meta.po.ScoreRank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MaeYon-Z
 *  date 2021-08-09
 */
@Repository
public interface ScoreRankDao extends PagingAndSortingRepository<ScoreRank, Long> {
    //获得一分一段表中的记录
    @Query(value = "select total_nums from tb_score_rank where score = ? ;", nativeQuery = true)
    Integer findTotalNumsByScore(Integer score);

    //获得一分一段表的最高分
    @Query(value = "select score from tb_score_rank LIMIT 1;", nativeQuery = true)
    int getTopScore();

    //获得一分一段表的最低分
    @Query(value = "select score from tb_score_rank order by id desc LIMIT 1;", nativeQuery = true)
    int getLowestScore();

}
