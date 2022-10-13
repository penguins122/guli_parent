package com.yang.eduservice.service;

import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yscong
 * @since 2022-06-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishById(String id);

    void getCoursePage(Page<EduCourse> page, CourseQueryVo courseQueryVo);

    void delCourseInfo(String id);

    Map<String, Object> getCourseApiPageVo(Page<EduCourse> page, CourseApiQueryVo courseApiQueryVo);

    CourseWebVo getCourseWebVo(String id);
}
