package com.yang.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yscong
 * @create: 2022/6/25 23:23
 */
@Data
public class OneSubjectVo {

    @ApiModelProperty(value="课程费类别ID")
    private String id;

    @ApiModelProperty(value = "课程分类标题")
    private String title;

    private List<TwoSubjectVo> children = new ArrayList<>();

}
