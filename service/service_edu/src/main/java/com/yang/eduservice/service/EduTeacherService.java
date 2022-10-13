package com.yang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yscong
 * @since 2022-06-22
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //前台分页查询讲师列表
    Map<String, Object> getTeacherApiPage(Page<EduTeacher> page);
}
