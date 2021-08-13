package com.gaokao.common.dao;

import com.gaokao.common.meta.po.GuessRank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-10
 */
@Repository
public interface AdviseDao extends PagingAndSortingRepository<GuessRank, Long> {

    @Query(value = "select * from tb_guess_rank;", nativeQuery = true)
    List<GuessRank> findGuessRankS();

    @Query(value = "select name from tb_university where id = ? ;", nativeQuery = true)
    String findUniversityNameById(Long id);

    @Query(value = "select university_code from tb_university where id = ? ;", nativeQuery = true)
    String findUniversityCodeById(Long id);

    @Query(value = "select DISTINCT major_name from tb_recruit_plan where university_id = ?1 and major_code = ?2 ;", nativeQuery = true)
    String findMajorNameByID(Long id, String majorCode);
}
