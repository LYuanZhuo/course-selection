package com.lyuan.admin.basecourse.entity;

import lombok.Data;

import java.util.Objects;

/**
 * @Description: 前端显示VO
 * @Author: Zhuo Linbo
 * @Date: 2019/11/22 19:06
 */
@Data
public class BasecourseListVO extends Basecourse{

    private String teacherName;

    private String teacherHeadImage;

    private String teacherCollege;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Basecourse that = (Basecourse) o;
        return that.getId().equals(super.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
