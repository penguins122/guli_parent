package com.yang.eduservice.controller;


import com.yang.eduservice.client.VodClient;
import com.yang.eduservice.entity.EduVideo;
import com.yang.commonutils.R;
import com.yang.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author yscong
 * @since 2022-06-27
 */

@Api(description="小节管理")
@RestController
@RequestMapping("/eduservice/eduvideo")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    @ApiOperation(value = "根据id删除小节")
    @DeleteMapping("delVideo/{id}")
    //TODO 删除小节同时删除阿里云视频
    public R delVideo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        String videoId = video.getVideoSourceId();
        if(videoId!=null){
            vodClient.delVideo(videoId);
        }
        videoService.removeById(id);
        return R.ok();
    }
    @ApiOperation(value = "根据id查询小节")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

}
