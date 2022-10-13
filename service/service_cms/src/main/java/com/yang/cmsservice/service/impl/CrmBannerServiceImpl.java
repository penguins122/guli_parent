package com.yang.cmsservice.service.impl;

import com.yang.cmsservice.entity.CrmBanner;
import com.yang.cmsservice.mapper.CrmBannerMapper;
import com.yang.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yscong
 * @since 2022-07-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {


    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAllBanner(){
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}
