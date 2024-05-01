package com.gaokao.common.dao;

import com.gaokao.common.meta.po.UserMember;
import com.gaokao.common.meta.po.UserStar;
import com.gaokao.common.meta.po.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/18
 * email: 757394026@qq.com
 */
public interface VolunteerDao extends PagingAndSortingRepository<Volunteer, Long> {
    Page<Volunteer> findByNameContaining(String keyword, Pageable pageable);

}
