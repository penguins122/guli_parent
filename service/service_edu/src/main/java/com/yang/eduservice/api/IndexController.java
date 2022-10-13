package com.yang.eduservice.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.commonutils.R;
import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.EduTeacher;
import com.yang.eduservice.service.EduCourseService;
import com.yang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yscong
 * @create: 2022/7/6 20:01
 */
@Api(description = "首页显示")
@CrossOrigin
@RestController
@RequestMapping(value = "/eduservice/index")
public class IndexController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "展示首页8条课程信息和4条讲师信息")
    @GetMapping("getCourseTeacherList")
    public R getCourseTeacherList(){
        QueryWrapper<EduCourse> courseWrapper  = new QueryWrapper<>();
        courseWrapper.orderByDesc("gmt_create");
        courseWrapper.last("LIMIT 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

        QueryWrapper<EduTeacher> teacherWrapper  = new QueryWrapper<>();
        teacherWrapper.orderByDesc("gmt_create");
        teacherWrapper.last("LIMIT 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}

