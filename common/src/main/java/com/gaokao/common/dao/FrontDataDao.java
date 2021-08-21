package com.gaokao.common.dao;

import com.gaokao.common.meta.po.ThirdData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author MaeYon-Z
 * date 2021-08-20
 */
@Repository
public interface FrontDataDao extends PagingAndSortingRepository<ThirdData, Integer> {

    @Query(value = "select id from tb_dict_two where upid = ?;", nativeQuery = true)
    List<Integer> getSecondIds(Integer firstId);

    @Query(value = "select value from tb_dict_two where id = ?;", nativeQuery = true)
    String getSecondNameById(Integer id);

    @Query(value = "select * from tb_dict_three where upid = ? ;", nativeQuery = true)
    List<ThirdData> getThirdData(Integer id);
}