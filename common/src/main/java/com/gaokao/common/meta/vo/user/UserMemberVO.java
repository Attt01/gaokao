package com.gaokao.common.meta.vo.user;

import com.gaokao.common.meta.po.UserMember;
import lombok.Data;

import java.util.List;

/**
 * @author wyc
 * 2021/8/15
 */
@Data
public class UserMemberVO {

    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * vip
     */
    private boolean is_vip;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 排名
     */
    private Long province_rank;
    /**
     * 选课
     */
    private List<Long> subject;
    /**
     * 手机
     */
    private String phone;






}
