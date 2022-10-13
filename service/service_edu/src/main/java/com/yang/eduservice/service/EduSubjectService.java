package com.yang.eduservice.service;

import com.yang.eduservice.entity.EduSubject;
import com.yang.eduservice.entity.vo.OneSubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yscong
 * @since 2022-06-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubjectVo> getAllSubject();
}
