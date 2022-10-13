package com.yang.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yang.eduservice.entity.vo.ExcelSubjectData;
import com.yang.eduservice.entity.EduSubject;
import com.yang.eduservice.service.EduSubjectService;
import com.yang.servicebase.handle.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author: yscong
 * @create: 2022/6/25 19:37
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }


    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData == null){
            throw  new GuliException(20001,"导入课程分类失败");
        }
        EduSubject eduOneSubject = this.existOneSubject(eduSubjectService,excelSubjectData.getOneSubjectName());
        if (eduOneSubject == null){
            eduOneSubject = new EduSubject();
            eduOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            eduOneSubject.setParentId("0");
            eduSubjectService.save(eduOneSubject);
        }

        String pid = eduOneSubject.getId();
        EduSubject eduTwoSubject = this.existTwoSubject(eduSubjectService,excelSubjectData.getTwoSubjectName(),pid);
        if (eduTwoSubject == null){
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            eduTwoSubject.setParentId(pid);
            eduSubjectService.save(eduTwoSubject);
        }

    }

    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
