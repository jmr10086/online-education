package com.zxjy.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxjy.commonutils.JwtUtils;
import com.zxjy.commonutils.MD5;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.educenter.entity.UcenterMember;
import com.zxjy.educenter.entity.vo.RegisterVo;
import com.zxjy.educenter.mapper.UcenterMemberMapper;
import com.zxjy.educenter.service.UcenterMemberService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        String finalMobile = "+86" + mobile;

        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new zxjyException(ResultCode.ERROR, "登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> memberWrapper = new QueryWrapper<>();
        memberWrapper.eq("mobile", finalMobile);
        UcenterMember mobileMember = baseMapper.selectOne(memberWrapper);
        //判断查询对象是否为空
        if (null == mobileMember) {
            throw new zxjyException(ResultCode.ERROR, "用户不存在");
        }
        //判断密码 把输入的密码进行加密，再和数据库密码进行比较
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new zxjyException(ResultCode.ERROR, "用户密码不正确");
        }
        //判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new zxjyException(ResultCode.ERROR, "用户已被禁用");
        }
        //登录成功 生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo register) {

        //获取注册的数据
        String firstMobile = register.getMobile();
        String nickname = register.getNickname();
        String password = register.getPassword();
        String code = register.getCode();
        String finalMobile = "+86" + firstMobile;

        //非空判断
        if (StringUtils.isEmpty(firstMobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
        throw new zxjyException(ResultCode.ERROR,"注册失败");
        }

        //判断验证码 获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(finalMobile);
        if (!code.equals(redisCode)) {
            throw new zxjyException(ResultCode.ERROR,"验证码不正确");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",finalMobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0) {
            throw new zxjyException(ResultCode.ERROR,"用户已存在");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(finalMobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://jchengpz.oss-cn-shanghai.aliyuncs.com/2021/11/26/1442297885942.jpg");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenidMember(String openid) {
        QueryWrapper<UcenterMember> wrapper= new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {

        return  baseMapper.countRegisterDay(day);
    }
}
