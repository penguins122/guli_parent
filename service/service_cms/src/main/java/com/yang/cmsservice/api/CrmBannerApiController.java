package com.yang.cmsservice.api;

import com.yang.cmsservice.entity.CrmBanner;
import com.yang.cmsservice.service.CrmBannerService;
import com.yang.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yscong
 * @create: 2022/7/5 23:27
 */
@Api(description = "前台banner展示")
@RestController
@CrossOrigin
@RequestMapping("/cmsservice/banner")
public class CrmBannerApiController {

    @Autowired
    private CrmBannerService bannerService;


    @ApiOperation(value = "查询所有banner信息")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList  = bannerService.getAllBanner();
        return R.ok().data("bannerList",bannerList );
    }

}
