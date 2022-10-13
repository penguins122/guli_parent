package com.yang.eduservice.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.commonutils.R;
import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.EduTeacher;
import com.yang.eduservice.entity.vo.TeacherQuery;
import com.yang.eduservice.service.EduCourseService;
import com.yang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yscong
 * @since 2022-06-22
 */
@Api(description = "前台讲师展示")
@RestController
@RequestMapping("/eduservice/teacherapi")
@CrossOrigin
public class TeacherApiController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "前台分页查询讲师列表")
    @GetMapping("getTeacherApiPage/{currer}/{limit}")
    public R getTeacherApiPage(@PathVariable("currer")Long currer,
                            @PathVariable("limit") Long limit){
        Page<EduTeacher> page = new Page<>(currer,limit);
        Map<String,Object> map = teacherService.getTeacherApiPage(page);

        return R.ok().data(map);
    }

    @ApiOperation(value = "前台查询讲师详情")
    @GetMapping("getTeacherCourseById/{id}")
    public R getTeacherCourseById(@PathVariable String id){
        //根据id查询讲师信息
        EduTeacher eduTeacher = teacherService.getById(id);
        //查询讲师相关课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }

}

