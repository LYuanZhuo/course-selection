package com.lyuan.applet.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer basecourseId;

    private Integer count;

    private Integer selectCount;

    private Integer classroomId;

    private Integer startWeek;

    private Integer endWeek;

    private Integer year;

    /**
     *  秋 /春
     */
    private String quarter;

    private Integer classIndex;

    private Integer weekDay;

    private String detail;

    private Integer itemId;

}
