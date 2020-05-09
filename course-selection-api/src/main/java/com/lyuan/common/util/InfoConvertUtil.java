package com.lyuan.common.util;

import com.lyuan.admin.course.entity.CourseListVO;
import com.lyuan.applet.course.entity.Course;
/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/21 17:09
 */
public class InfoConvertUtil {

    private static String[] DAYS = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

    private static String[] CLASS_INDEXS = {"1,2节","3,4节","5,6节","7,8节","9,10节","11,12节"};

    private static String[] SELECT_STATUS = {"选课中","成功","失败"};

    public static String selectStatusConvert(int status){
        if (status<0||status>2){
            return "";
        }
        return SELECT_STATUS[status];
    }

    public static String dayConvert(int day){
        if (day<1||day>7){
            return "";
        }
        return DAYS[day-1];
    }

    public static String classIndexConvert(int index){
        if (index<1||index>6){
            return "";
        }
        return CLASS_INDEXS[index-1];
    }

    public static String getCourseTimeDetail(Course course){
        String courseDetail = course.getStartWeek()+"-"+course.getEndWeek()+"周 "
                +InfoConvertUtil.dayConvert(course.getWeekDay())
                +InfoConvertUtil.classIndexConvert(course.getClassIndex());
        return courseDetail;
    }

    public static String getCourseTimeDetail(com.lyuan.admin.course.entity.Course course){
        String courseDetail = course.getStartWeek()+"-"+course.getEndWeek()+"周 "
                +InfoConvertUtil.dayConvert(course.getWeekDay())
                +InfoConvertUtil.classIndexConvert(course.getClassIndex());
        return courseDetail;
    }

    public static String getCourseDetail(CourseListVO courseListVO){
        StringBuilder sb = new StringBuilder();
        sb.append(courseListVO.getType()).append(" | ")
                .append(courseListVO.getStartWeek()).append("-").append(courseListVO.getEndWeek())
                .append("周 ").append(InfoConvertUtil.dayConvert(courseListVO.getWeekDay()))
                .append(InfoConvertUtil.classIndexConvert(courseListVO.getClassIndex()))
                .append(" | ").append(courseListVO.getClassroomName());
        return sb.toString();
    }
}
