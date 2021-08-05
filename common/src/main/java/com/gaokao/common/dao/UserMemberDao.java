package com.gaokao.common.dao;


import com.gaokao.common.meta.po.UserMember;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberDao extends PagingAndSortingRepository<UserMember, Long> {


    UserMember findUserMemberById(Long userId);

    void deleteById(Long userId);
}
