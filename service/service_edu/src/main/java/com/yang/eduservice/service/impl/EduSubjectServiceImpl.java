package com.yang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yang.eduservice.entity.vo.ExcelSubjectData;
import com.yang.eduservice.entity.vo.OneSubjectVo;
import com.yang.eduservice.entity.vo.TwoSubjectVo;
import com.yang.eduservice.entity.EduSubject;
import com.yang.eduservice.listener.SubjectExcelListener;
import com.yang.eduservice.mapper.EduSubjectMapper;
import com.yang.eduservice.service.EduSubjectService;
import com.yang.servicebase.handle.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yscong
 * @since 2022-06-25
 */
@Service
@Transactional
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream fileInputStream = file.getInputStream();
            EasyExcel.read(fileInputStream, ExcelSubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"导入课程分类失败");
        }
    }

    @Override
    public List<OneSubjectVo> getAllSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        //查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        oneWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);
        //封装一级分类

        ArrayList<OneSubjectVo> allSubjectVo = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject oneSubject = oneSubjectList.get(i);
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            BeanUtils.copyProperties(oneSubject,oneSubjectVo);
            allSubjectVo.add(oneSubjectVo);
            //封装二级分类
            ArrayList<TwoSubjectVo> twoSubjectVos = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject twoSubject = twoSubjectList.get(m);
                if (twoSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject,twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                    oneSubjectVo.setChildren(twoSubjectVos);
                }

            }
        }
        return allSubjectVo;
    }
}
