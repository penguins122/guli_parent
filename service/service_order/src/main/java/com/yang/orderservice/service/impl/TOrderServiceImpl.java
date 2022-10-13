package com.yang.orderservice.service.impl;

import com.yang.servicebase.handle.GuliException;
import com.yang.commonutils.vo.CourseWebVoForOrder;
import com.yang.commonutils.vo.UcenterMemberForOrder;
import com.yang.orderservice.client.EduClient;
import com.yang.orderservice.client.UcenterClient;
import com.yang.orderservice.entity.TOrder;
import com.yang.orderservice.mapper.TOrderMapper;
import com.yang.orderservice.service.TOrderService;
import com.yang.orderservice.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-22
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id、用户id创建订单
    @Override
    public String createOrder(String courseId, String memberId) {
        //1 跨模块获取课程信息
        CourseWebVoForOrder courseInfoForOrder = eduClient.getCourseInfoForOrder(courseId);
        //1.1进行校验
        if(courseInfoForOrder==null){
            new GuliException(20001,"获取课程信息失败");
        }
        //2跨模块获取用户信息
        UcenterMemberForOrder ucenterForOrder = ucenterClient.getUcenterForOrder(memberId);
        //2.2进行校验
        if(ucenterForOrder==null){
            new GuliException(20001,"获取用户信息失败");
         }
        //3生成订单编号
        String orderNo = OrderNoUtil.getOrderNo();
        //4封装数据，存入数据库
        TOrder order = new TOrder();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoForOrder.getTitle());
        order.setCourseCover(courseInfoForOrder.getCover());
        order.setTeacherName(courseInfoForOrder.getTeacherName());
        order.setTotalFee(courseInfoForOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterForOrder.getMobile());
        order.setNickname(ucenterForOrder.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//1：微信
        baseMapper.insert(order);

        return orderNo;
    }
}
