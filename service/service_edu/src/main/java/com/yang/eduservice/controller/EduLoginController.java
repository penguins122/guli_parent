package com.yang.eduservice.controller;

import com.yang.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author: yscong
 * @create: 2022/6/24 17:30
 */
@Api(description = "模拟登录")
@RestController
@RequestMapping("/eduuser")
@CrossOrigin
public class EduLoginController {

    @ApiOperation(value = "登陆")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}


