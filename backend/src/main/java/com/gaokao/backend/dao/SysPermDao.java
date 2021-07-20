package com.gaokao.backend.dao;

import com.gaokao.backend.meta.po.SysPerm;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author attack204
 * date:  2021/7/19
 * email: 757394026@qq.com
 */
@Repository
public interface SysPermDao extends PagingAndSortingRepository<SysPerm, Long> {

    List<SysPerm> findAllByPid(Long pid);

    List<SysPerm> findAllById(Long id);

    SysPerm findByCode(String code);

}
