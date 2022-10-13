package com.yang.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: yscong
 * @create: 2022/6/25 23:25
 */
@Data
public class TwoSubjectVo {
    @ApiModelProperty(value="课程费类别ID")
    private String id;

    @ApiModelProperty(value = "课程分类标题")
    private String title;
}
