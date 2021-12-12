package com.zxjy.educms.controller;

import com.zxjy.commonutils.R;
import com.zxjy.educms.entity.CrmBanner;
import com.zxjy.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/26 14:43
 */
@RestController
@RequestMapping("/educms/bannerfront")
@MapperScan("com.zxjy.educms.mapper")
@Api(description = "网站首页Banner列表")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "查所有的Banner")
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
