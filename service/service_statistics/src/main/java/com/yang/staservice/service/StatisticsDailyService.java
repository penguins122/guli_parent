package com.yang.staservice.service;

import com.yang.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author yscong
 * @since 2022-07-12
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStaDaily(String day);

    Map<String, Object> getStaDaily(String type, String begin, String end);
}
