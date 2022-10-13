package com.yang.ucenterservice.service.impl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.commonutils.utils.JwtUtils;
import com.yang.commonutils.utils.MD5;
import com.yang.servicebase.handle.GuliException;
import com.yang.ucenterservice.entity.UcenterMember;
import com.yang.ucenterservice.entity.vo.LoginVo;
import com.yang.ucenterservice.entity.vo.RegisterVo;
import com.yang.ucenterservice.mapper.UcenterMemberMapper;
import com.yang.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yscong
 * @since 2022-07-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        //1.参数验空
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册信息缺失");
        }

        //2 验证手机号是否重复
        QueryWrapper<UcenterMember> memberWrapper = new QueryWrapper<>();
        memberWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(memberWrapper);
        if(count>0){
            throw new GuliException(20001,"手机号重复");
        }
        //3.验证 验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            System.out.println(redisCode);
            throw new GuliException(20001,"验证码错误");
        }
        //4.md5 加密
        String md5Password  = MD5.encrypt(password);
        //5 补充信息后插入数据库

        UcenterMember member = new UcenterMember();
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        member.setIsDisabled(false);
        member.setMobile(mobile);
        member.setPassword(md5Password);
        member.setNickname(nickname);
        baseMapper.insert(member);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1.校验参数
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"手机号或密码错误");
        }
        //2.根据手机号获取用户
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember selectOne = baseMapper.selectOne(wrapper);
        if (selectOne==null){
            throw new GuliException(20001,"手机号或密码错误");
        }
        //3.md5加密后判断密码
        String md5Password  = MD5.encrypt(password);
        if (!md5Password.equals(selectOne.getPassword())){
            throw new GuliException(20001,"手机号或密码错误");
        }

        //4. 生产token字符串
        String token = JwtUtils.getJwtToken(selectOne.getId(), selectOne.getNickname());

        return token;
    }

    @Override
    public Integer countRegister(String day) {

        Integer count= baseMapper.countRegister(day);
        return count;
    }
}
