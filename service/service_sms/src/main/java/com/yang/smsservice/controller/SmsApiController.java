package com.yang.smsservice.controller;

import com.yang.commonutils.R;
import com.yang.commonutils.utils.JwtUtils;
import com.yang.commonutils.utils.RandomUtil;
import com.yang.smsservice.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: yscong
 * @create: 2022/7/7 11:48
 */
@Api(description="短信发送")
@RestController
@RequestMapping("/edumsm/sms")
@CrossOrigin
public class SmsApiController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @ApiOperation(value = "短信发送")
    @GetMapping("sendSmsPhone/{phone}")
    public R sendSmsPhone(@PathVariable String phone){
        //1 拿手机号到redis查询验证码
        String rPhone = redisTemplate.opsForValue().get(phone);

        //2验证验证码是否存在，直接返回ok
        if (rPhone!=null){
            return R.ok();
        }
        //3验证码不存在，生成验证码
        String code  = RandomUtil.getFourBitRandom();
        //4调用接口服务发送短信
        HashMap<String, String> map = new HashMap<>();
        map.put("code",code);
        boolean isSend = smsService.sendSmsPhone(phone,map);

        //5发送成功验证码存入redis，时效5分钟
        if (isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else
        {
            return R.error();
        }
    }


}
