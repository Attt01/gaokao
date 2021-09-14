package com.gaokao.common.dao;

import com.gaokao.common.meta.po.GuessRank;
import com.gaokao.common.meta.po.UserForm;
import com.gaokao.common.meta.po.Volunteer;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-10
 */
@Repository
public interface AdviseDao extends PagingAndSortingRepository<Volunteer, Long> {

    @Query(value = "select * from tb_volunteer; ", nativeQuery = true)
    List<Volunteer> getAllVolunteer();
}
