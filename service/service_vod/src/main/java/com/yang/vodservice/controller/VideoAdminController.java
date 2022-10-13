package com.yang.vodservice.controller;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.ram.model.v20150501.DeleteAccessKeyRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yang.commonutils.R;
import com.yang.servicebase.handle.GuliException;
import com.yang.vodservice.utils.AliyunVodSDKUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: yscong
 * @create: 2022/7/5 9:29
 */
@Api(description = "视频管理")
@RestController
@RequestMapping("/eduvod/vod")
@CrossOrigin
public class VideoAdminController {

    @ApiOperation(value = "上传视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    "LTAI5t7kM3MXoUfpyeC3XREo",
                    "c9sFuJcGUUciMdI9JfD4uBHipyJ1tJ",
                    title,filename,inputStream
            );
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            String videoId = response.getVideoId();
            return R.ok().data("videoId",videoId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"上传视频失败");
        }
    }

    @ApiOperation(value = "删除视频")
    @DeleteMapping("delVideo/{videoId}")
    public R delVideo(@PathVariable("videoId") String videoId){
        //初始化客户端对象
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5t7kM3MXoUfpyeC3XREo",
                    "c9sFuJcGUUciMdI9JfD4uBHipyJ1tJ");
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delVideoList")
    public R delVideo(@RequestParam("videoIdList") List<String> videoIdList){
        System.out.println("vodDel");
        //初始化客户端对象
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5t7kM3MXoUfpyeC3XREo",
                    "c9sFuJcGUUciMdI9JfD4uBHipyJ1tJ");
            //创建请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向请求中设置参数
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"批量删除视频失败");
        }
    }

    @ApiOperation(value = "根据视频id获取视频播放凭证")
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid){
        try {
            //（1）创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5t7kM3MXoUfpyeC3XREo",
                    "c9sFuJcGUUciMdI9JfD4uBHipyJ1tJ");
            //（2）创建request、response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //（3）向request设置视频id
            request.setVideoId(vid);
            //播放凭证有过期时间，默认值：100秒 。取值范围：100~3000。
            //request.setAuthInfoTimeout(200L);
            //（4）调用初始化方法实现功能
            response = client.getAcsResponse(request);

            //（5）调用方法返回response对象，获取内容
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001,"获取视频凭证失败");
        }
    }


}
