package com.gaokao.common.meta.vo.volunteer;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * @author attack204
 * date:  2021/8/8
 * email: 757394026@qq.com
 */
@Data
public class VolunteerFormVO {

    private Long id;

    private Long userId;

    private String name;

    private Long score;

    private List<Long> subject;

    private String volunteerList;

    private Long generatedType;

    private Long currentIsOrNot;

    private Long generatedTime;

}
