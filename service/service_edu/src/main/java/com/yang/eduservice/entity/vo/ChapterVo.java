package com.yang.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yscong
 * @create: 2022/6/27 10:12
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    private List<VideoVo> children =new ArrayList<>();

}
