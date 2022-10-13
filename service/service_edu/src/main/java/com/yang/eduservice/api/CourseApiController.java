package com.yang.eduservice.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.commonutils.R;
import com.yang.commonutils.utils.JwtUtils;
import com.yang.commonutils.vo.CourseWebVoForOrder;
import com.yang.eduservice.client.OrderClient;
import com.yang.eduservice.entity.EduCourse;
import com.yang.eduservice.entity.vo.ChapterVo;
import com.yang.eduservice.entity.vo.CourseApiQueryVo;
import com.yang.eduservice.entity.vo.CourseWebVo;
import com.yang.eduservice.service.EduChapterService;
import com.yang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yscong
 * @create: 2022/7/8 10:24
 */
@Api(description = "前台课程展示")
@RestController
@RequestMapping("/eduservice/courseapi")
@CrossOrigin
public class CourseApiController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private OrderClient orderClient;
    
    @Autowired
    private EduChapterService chapterService;
    @ApiOperation(value = "前台带条件分页查询课程信息")
    @PostMapping(value = "getCourseApiPageVo/{current}/{limit}")
    public R getCourseApiPageVo(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseApiQueryVo courseApiQueryVo){


       Page<EduCourse> page = new Page<>(current,limit);
       Map<String,Object> map = courseService.getCourseApiPageVo(page,courseApiQueryVo);
       return R.ok().data(map);
    }

    @ApiOperation(value = "前台根据课程id查询课程信息")
    @GetMapping(value = "getCourseWebInfo/{courseId}")
    public R getCourseWebInfo(@PathVariable String courseId, HttpServletRequest request){
        //1.查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        //2.查询课程大纲
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(courseId);
        //3.根据课程id，会员id查询是否已购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("memberId="+memberId);
        boolean isBuyCourse  = orderClient.isBuyCourse(courseId, memberId);

        return R.ok().data("courseWebVo",courseWebVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuyCourse",isBuyCourse);

    }
    @ApiOperation(value = "根据课程id查询课程信息跨模块")
    @GetMapping(value = "getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable String courseId) {
        //1.查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        CourseWebVoForOrder courseWebVoForOrder = new CourseWebVoForOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoForOrder);
        return courseWebVoForOrder;
    }

}
