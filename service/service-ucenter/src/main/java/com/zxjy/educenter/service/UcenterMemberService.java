package com.zxjy.educenter.service;

import com.zxjy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxjy.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo register);

    UcenterMember getOpenidMember(String openid);

    Integer countRegisterDay(String day);
}
