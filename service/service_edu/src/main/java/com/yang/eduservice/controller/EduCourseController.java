package com.yang.eduservice.controller;


import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.vo.CourseInfoForm;
import com.yang.eduservice.entity.vo.CoursePublishVo;
import com.yang.eduservice.entity.vo.CourseQueryVo;
import com.yang.eduservice.service.EduCourseService;
import com.yang.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yscong
 * @since 2022-06-26
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.addCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }



    @ApiOperation(value = "根据id课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id){
        CourseInfoForm courseInfoForm =  courseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoForm);
    }


    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        courseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询课程发布信息")
    @GetMapping("getCoursePublishById/{id}")
    public R getCoursePublishById(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishById(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation(value = "根据id修改发布状态")
    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse course = courseService.getById(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @ApiOperation(value = "查询所有课程信息")
    @GetMapping("getCourseInfo")
    // TODO 实现带条件带分页查询
    public R getCourseInfo(){
        List<EduCourse> courseList = courseService.list(null);
        return R.ok().data("list",courseList);
    }


    @ApiOperation(value = "分页课程列表")
    @PostMapping("getCoursePage/{current}/{limit}")
    public R getCoursePage(@PathVariable Long current, @PathVariable Long limit,@RequestBody CourseQueryVo courseQueryVo){
        System.out.println(current);
        System.out.println(limit);
        System.out.println(courseQueryVo.toString());
        Page<EduCourse> page = new Page<EduCourse>(current, limit);
        courseService.getCoursePage(page,courseQueryVo);
        List<EduCourse> list = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("list",list).data("total",total);
    }

    @ApiOperation(value = "根据id删除")
    @DeleteMapping("delCourseInfo/{id}")
    public R delCourseInfo(@PathVariable String id){

        courseService.delCourseInfo(id);
        return R.ok();
    }


}

