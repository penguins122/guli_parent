package com.yang.eduservice.service.impl;

import com.yang.eduservice.client.VodClient;
import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.EduVideo;
import com.yang.eduservice.entity.vo.*;
import com.yang.eduservice.mapper.EduCourseMapper;
import com.yang.eduservice.service.EduCourseService;
import com.yang.eduservice.entity.EduChapter;
import com.yang.eduservice.entity.EduCourseDescription;
import com.yang.eduservice.service.EduChapterService;
import com.yang.eduservice.service.EduCourseDescriptionService;
import com.yang.eduservice.service.EduVideoService;
import com.yang.servicebase.handle.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yscong
 * @since 2022-06-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodClient vodClient;
    //添加课程信息
    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        //1添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert==0){
            throw  new GuliException(20001,"创建课程失败");
        }
        //2获取课程id
        String courseId = eduCourse.getId();
        //3添加课程描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseId);
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionService.save(courseDescription);

        return courseId;
    }

    //根据id课程信息
    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        //1根据id查询课程信息
        EduCourse eduCourse = baseMapper.selectById(id);
        System.out.println(eduCourse.toString());
        //2封装课程信息
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        //3根据id查询课程描述信息
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        System.out.println(courseDescription.toString());
        //4封装课程描述
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //复制EduCourse信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        //更新信息
        Integer update = baseMapper.updateById(eduCourse);

        //3判断是否成功
        if(update==0){
            throw  new GuliException(20001,"修改课程失败");
        }
        //复制EduCourseDescr 信息
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,description);
        //更新信息
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo =
                baseMapper.getCoursePublishById(id);
        return coursePublishVo;
    }

    @Override
    public void getCoursePage(Page<EduCourse> page, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQueryVo == null){
            baseMapper.selectPage(page, queryWrapper);
            return;
        }

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void delCourseInfo(String id) {
        //删除小节、删除章节、删除课程描述、删除课程
        //1  删除视频
        // 1.1 查询相关小节
        QueryWrapper<EduVideo> wrapperVideo1 = new QueryWrapper<>();
        wrapperVideo1.eq("course_id",id);
        List<EduVideo> list = eduVideoService.list(wrapperVideo1);
        // 1.2 遍历获取视频id
        ArrayList<String> videoIdList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            EduVideo eduVideo = list.get(i);
            System.out.println(eduVideo.getVideoSourceId());
            videoIdList.add(eduVideo.getVideoSourceId());
        }
        // 1.3 判断，调接口
        if (videoIdList.size()>0){
            vodClient.delVideo(videoIdList);
        }
        //2删除小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        eduVideoService.remove(wrapperVideo);
// 3删除章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",id);
        eduChapterService.remove(wrapperChapter);
// 4删除课程描述
        courseDescriptionService.removeById(id);
// 5删除课程
        int delete = baseMapper.deleteById(id);
        if(delete==0){
            throw  new GuliException(20001,"删除课程失败");
        }

    }

    @Override
    public Map<String, Object> getCourseApiPageVo(Page<EduCourse> pageParam, CourseApiQueryVo courseApiQueryVo) {
        //取出查询条件
        //验空，不空拼接到查询条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseApiQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseApiQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseApiQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseApiQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseApiQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseApiQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseApiQueryVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        //分页查询
        baseMapper.selectPage(pageParam, queryWrapper);
        //封装数据
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }

    //前台根据课程id查询课程信息
    @Override
    public CourseWebVo getCourseWebVo(String courseId) {
        CourseWebVo courseWebVo = baseMapper.getCourseWebVo(courseId);
            return courseWebVo;
    }
}
