package com.gaokao.common.meta.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_dict_three")
public class ThirdData {
    @Id
    private Integer id;

    private Integer upid;

    private String value;
}
