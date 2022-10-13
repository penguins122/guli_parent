package com.yang.eduservice.entity.vo;

import lombok.Data;

/**
 * @author: yscong
 * @create: 2022/6/27 19:28
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
