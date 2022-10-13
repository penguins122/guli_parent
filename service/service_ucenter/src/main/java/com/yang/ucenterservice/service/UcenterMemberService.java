package com.yang.ucenterservice.service;

import com.yang.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.ucenterservice.entity.vo.LoginVo;
import com.yang.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yscong
 * @since 2022-07-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    Integer countRegister(String day);
}
