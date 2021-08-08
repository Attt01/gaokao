package com.gaokao.common.dao;

import com.gaokao.common.meta.po.VolunteerForm;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/8
 * email: 757394026@qq.com
 */
@Repository
public interface VolunteerDao extends PagingAndSortingRepository<VolunteerForm, Long> {

    List<VolunteerForm> findAllByUserId(Long userId);

}
