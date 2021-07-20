package com.gaokao.backend.dao;

import com.gaokao.backend.meta.po.SysUser;
import com.gaokao.backend.meta.po.SysUserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author attack204
 * date:  2021/7/19
 * email: 757394026@qq.com
 */
@Repository
public interface SysUserRoleDao extends PagingAndSortingRepository<SysUserRole, Long> {
    @Transactional
    Long deleteAllByUserId(Long userId);

    List<SysUserRole> findAllByUserId(Long userId);
}
