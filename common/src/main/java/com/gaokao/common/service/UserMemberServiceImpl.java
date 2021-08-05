package com.gaokao.common.service;


import com.gaokao.common.dao.UserMemberDao;
import com.gaokao.common.enums.UserMemberStatus;
import com.gaokao.common.exceptions.BusinessException;
import com.gaokao.common.meta.po.UserMember;
import com.gaokao.common.meta.vo.user.MemberUpdateParams;
import com.gaokao.common.meta.vo.user.RegParams;
import com.gaokao.common.meta.vo.user.UserMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserMemberServiceImpl implements UserMemberService{
    @Autowired
    private UserMemberDao userMemberDao;
    @Override
    public Long reg(RegParams regParams) {
        return null;
    }

    @Override
    public Page<UserMemberVO> list(String keyword, Integer page, Integer size) {
        List<UserMember> userMembers = userMemberDao.findByUsernameContaining(keyword, PageRequest.of(page - 1, size));
        List<UserMemberVO> userMemberVOS = new ArrayList<>(userMembers.size());
        userMembers.forEach(userMember -> {
            UserMemberVO userMemberVO = new UserMemberVO();
            BeanUtils.copyProperties(userMember, userMemberVO);
            userMemberVOS.add(userMemberVO);
        });
        return new PageImpl<>(userMemberVOS, PageRequest.of(page - 1, size), userMemberVOS.size());
    }

    @Override
    public Long update(Long id, Long userId, MemberUpdateParams params) {
        UserMember userMember = userMemberDao.findById(id).orElse(null);
        if (userMember == null) {
            throw new BusinessException("记录不存在");
        }
        BeanUtils.copyProperties(params, userMember);
        return userMemberDao.save(userMember).getId();
    }

    @Override
    public int changePwd(long id, String originPwd, String newPwd) {
        return 0;
    }

    @Override
    public Long lock(Long userId) {
        Optional<UserMember> optionalUser = userMemberDao.findById(userId);
        UserMember userMember = optionalUser.orElse(null);
        if (userMember == null) {
            return -1L;
        } else {
            userMember.setStatus(UserMemberStatus.LOCKED.getCode());
            userMemberDao.save(userMember);
            return userId;
        }
    }

    @Override
    public Long unlock(Long userId) {
        return null;
    }
}
