package com.yang.ucenterservice.controller;


import com.yang.commonutils.R;
import com.yang.commonutils.utils.JwtUtils;
import com.yang.commonutils.vo.UcenterMemberForOrder;
import com.yang.ucenterservice.entity.UcenterMember;
import com.yang.ucenterservice.entity.vo.LoginVo;
import com.yang.ucenterservice.entity.vo.RegisterVo;
import com.yang.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yscong
 * @since 2022-07-07
 */
@Api(description = "登录注册")
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "根据token字符串获取用户信息")
    @GetMapping("getUcenterByToken")
    public R getUcenterByToken(HttpServletRequest request){
        System.out.println(request);
        String idByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(idByJwtToken);
        UcenterMember ucenterMember  = memberService.getById(idByJwtToken);
        return R.ok().data("ucenterMember",ucenterMember );
    }



    @ApiOperation(value = "根据memberId获取用户信息跨模块")
    @GetMapping("getUcenterForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterForOrder(
            @PathVariable("memberId") String memberId){
        UcenterMember ucenterMember = memberService.getById(memberId);
        UcenterMemberForOrder ucenterMemberForOrder = new UcenterMemberForOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberForOrder);
        return ucenterMemberForOrder;
    }

    @ApiOperation(value = "统计注册人数远程调用")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day")String day){
        Integer count = memberService.countRegister(day);
        return R.ok().data("countRegister",count);
    }


}

