package com.yang.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.commonutils.R;
import com.yang.commonutils.utils.RandomUtil;
import com.yang.staservice.client.UcenterClient;
import com.yang.staservice.entity.StatisticsDaily;
import com.yang.staservice.mapper.StatisticsDailyMapper;
import com.yang.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yscong
 * @since 2022-07-12
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStaDaily(String day) {
        //删除当天统计数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);

        //统计当天数据
        R r = ucenterClient.countRegister(day);
        Integer registerNum = (Integer) r.getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100,200);
        Integer videoViewNum  = RandomUtils.nextInt(100,200);
        Integer courseNum  = RandomUtils.nextInt(100,200);
        //封装数据插入数据库
        StatisticsDaily daily = new StatisticsDaily();
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setRegisterNum(registerNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setDateCalculated(day);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getStaDaily(String type, String begin, String end) {
        //1查询数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        queryWrapper.select("date_calculated",type);
        List<StatisticsDaily> dailieList = baseMapper.selectList(queryWrapper);
//2 遍历查询结果
        Map<String, Object> staDailyMap = new HashMap<>();
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> dataList =  new ArrayList<>();
        for (int i = 0; i < dailieList.size(); i++) {
            StatisticsDaily daily = dailieList.get(i);
            //3 封装X轴数据
            dateCalculatedList.add(daily.getDateCalculated());
            //4 封装Y轴数据
            switch (type){
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        staDailyMap.put("dateCalculatedList",dateCalculatedList);
        staDailyMap.put("dataList",dataList);


        return staDailyMap;

    }
}
