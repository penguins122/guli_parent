package com.yang.eduservice.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: yscong
 * @create: 2022/7/11 22:20
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    //根据课程id、用户id查询是已购买,远程调用
    @GetMapping("/orderservice/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
