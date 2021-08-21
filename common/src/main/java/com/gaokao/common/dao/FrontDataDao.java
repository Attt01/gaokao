package com.gaokao.common.dao;

import com.gaokao.common.meta.po.GuessRank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-20
 */
@Repository
public interface FrontDataDao extends PagingAndSortingRepository<GuessRank, Long> {
    @Query(value = "select id from tb_dict_one where value = ?;", nativeQuery = true)
    Integer getFirstIdByValue(String value);

    @Query(value = "select id from tb_dict_two where value = ?;", nativeQuery = true)
    Integer getSecondIdByValue(String value);

    @Query(value = "select value from tb_dict_two where upid = ?;", nativeQuery = true)
    List<String> getSecondValuesByFirstID(Integer firstID);

    @Query(value = "select value from tb_dict_three where upid = ?;", nativeQuery = true)
    List<String> getThirdValuesBySecondID(Integer SecondID);

    @Query(value = "select `value` from tb_dict_two where upid = 2;", nativeQuery = true)
    List<String> getProvinces();

    @Query(value = "select `value` from tb_dict_three where upid in (select id from tb_dict_two where `value` = ?);", nativeQuery = true)
    List<String> getCities(String provinceName);
}
