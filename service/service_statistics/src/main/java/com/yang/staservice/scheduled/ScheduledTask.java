package com.yang.staservice.scheduled;

import com.yang.staservice.service.StatisticsDailyService;
import com.yang.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;

    //每天凌晨1点跑前一天数据
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task2(){
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        dailyService.createStaDaily(day);
        System.out.println("生成数据成功"+day);
    }

}
