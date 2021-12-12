package com.zxjy.eduservice.controller;

import com.zxjy.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author jcheng
 * @since 2021/11/18 17:59
 */
@RestController
@RequestMapping("/eduservice/user")
@Api(description = "临时登录方法")
public class EduLoginController {

    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
