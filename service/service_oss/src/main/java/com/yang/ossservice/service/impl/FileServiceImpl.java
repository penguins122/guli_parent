package com.yang.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yang.ossservice.service.FileService;
import com.yang.ossservice.utils.ConstantPropertiesUtil;
import com.yang.servicebase.handle.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author: yscong
 * @create: 2022/6/25 14:55
 */
@Service
public class FileServiceImpl implements FileService {

    //上传文件
    @Override
    public String uploadFileOSS(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        //获取文件名
        String filename = file.getOriginalFilename();
        //优化文件名
        filename = UUID.randomUUID().toString() + filename;
        //优化文件存储路径
        String path = new DateTime().toString("yyyy/MM/dd");
        filename = path + "/" +filename;
        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(200001,"上传失败");
        }
    }
}
