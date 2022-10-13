package com.yang.eduservice.service;

import com.yang.eduservice.entity.EduChapter;
import com.yang.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yscong
 * @since 2022-06-27
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);
}
