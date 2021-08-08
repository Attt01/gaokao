package com.gaokao.common.dao;


import com.gaokao.common.meta.po.UserMember;
import com.gaokao.common.meta.vo.user.UserMemberVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMemberDao extends PagingAndSortingRepository<UserMember, Long> {

    UserMember findUserMemberByUsername(String username);

    UserMember findUserMemberById(Long userId);

    List<UserMember> findByUsernameContaining(String keyword, Pageable pageable);

    void deleteById(Long userId);

    UserMember findUserMemberByPhone(String phone);
}
