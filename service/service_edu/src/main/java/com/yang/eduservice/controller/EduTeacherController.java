package com.yang.eduservice.controller;


import com.yang.eduservice.entity.EduTeacher;
import com.yang.eduservice.entity.vo.TeacherQuery;
import com.yang.commonutils.R;
import com.yang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yscong
 * @since 2022-06-22
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/eduteacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R getAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "删除讲师")
    @DeleteMapping("{id}")
    public R delTeacher(@PathVariable String id){
        boolean remove = eduTeacherService.removeById(id);
        if (remove) return R.ok();
        else return R.error();
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("getTeacherPage/{currer}/{limit}")
    public R getTeacherPage(@PathVariable("currer")Long currer,
                            @PathVariable("limit") Long limit){

        Page<EduTeacher> page = new Page<>(currer, limit);
        eduTeacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        //1.存入map
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("list",records);
//        map.put("total",total);
//        return R.ok().data(map);
        return R.ok().data("list",records).data("total",total);
    }

    @ApiOperation(value = "带条件分页查询讲师列表")
    @PostMapping("getTeacherPageVo/{current}/{limit}")
    public R getTeacherPageVo(@PathVariable("current")Long current,
                              @PathVariable("limit") Long limit,
                              @RequestBody TeacherQuery teacherQuery){
        //取出查询条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件，拼接sql
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }

        Page<EduTeacher> page = new Page<>(current,limit);
        eduTeacherService.page(page,queryWrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("list",records).data("total",total);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查找讲师")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if(update){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

