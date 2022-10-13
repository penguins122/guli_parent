package com.yang.staservice.client;

import com.yang.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //统计注册人数远程调用
    @GetMapping("/ucenterservice/member/countRegister/{day}")
    public R countRegister(@PathVariable("day")String day);
}
