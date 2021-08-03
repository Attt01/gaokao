package com.gaokao.common.service.admin;


import com.gaokao.common.dao.UserMemberDao;
import com.gaokao.common.enums.UserMemberStatus;
import com.gaokao.common.exceptions.BusinessException;
import com.gaokao.common.meta.po.UserMember;
import com.gaokao.common.meta.vo.user.MemberUpdateParams;
import com.gaokao.common.meta.vo.user.RegParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserMemberServiceImpl implements UserMemberService{
    @Autowired
    private UserMemberDao userMemberDao;
    @Override
    public Long reg(RegParams regParams) {
        return null;
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
