package com.zxjy.educenter.controller;


import com.zxjy.commonutils.JwtUtils;
import com.zxjy.commonutils.R;
import com.zxjy.educenter.entity.UcenterMember;
import com.zxjy.educenter.entity.vo.RegisterVo;
import com.zxjy.educenter.service.UcenterMemberService;
import com.zxjy.vo.UcenterMemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/educenter/member")
@Api(description = "用户管理")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member){
        // 返回token，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo register){
        memberService.register(register);
        return R.ok();
    }

    @ApiOperation("从token获取用户信息")
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id){
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberVo);
        return ucenterMemberVo;
    }

    @ApiOperation("某天注册人数")
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

