package com.yang.eduservice.client;

import com.yang.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/eduvod/vod/delVideo/{videoId}")
    public R delVideo(@PathVariable("videoId") String videoId);


    @DeleteMapping("/eduvod/vod/delVideoList")
    public R delVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
