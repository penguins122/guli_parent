package com.yang.wxlogin.controller;


import com.yang.servicebase.handle.GuliException;
import com.yang.wxlogin.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yscong
 * @since 2022-07-07
 */
@Api(description = "微信登录")
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class UcenterMemberController {

    @GetMapping("login")
    public String wxlogin(){
        //方式1   https://open.weixin.qq.com/connect/qrconnect?
        // appid=APPID&redirect_uri=REDIRECT_URI&
        // response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        //方式2
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguiguwxlogin");

        return "redirect:" + qrcodeUrl;

    }
}

