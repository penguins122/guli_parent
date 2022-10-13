package com.yang.eduservice.mapper;

import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.eduservice.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yscong
 * @since 2022-06-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishById(String id);

    CourseWebVo getCourseWebVo(String courseId);
}
