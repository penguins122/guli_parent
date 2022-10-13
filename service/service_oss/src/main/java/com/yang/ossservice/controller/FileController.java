package com.yang.ossservice.controller;

import com.yang.commonutils.R;
import com.yang.ossservice.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: yscong
 * @create: 2022/6/25 14:54
 */
@Api(description = "文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file){
        //获取文件调用接口上传并获得图片地址
        String url = fileService.uploadFileOSS(file);
        return R.ok().data("url",url);
    }


}
