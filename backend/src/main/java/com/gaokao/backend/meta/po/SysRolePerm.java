package com.gaokao.backend.meta.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author attack204
 * date:  2021/7/19
 * email: 757394026@qq.com
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_sys_role_perm")
public class SysRolePerm{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleId;

    private Long permId;
}
