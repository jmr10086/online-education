package com.zxjy.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.commonutils.R;
import com.zxjy.educms.entity.CrmBanner;
import com.zxjy.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-26
 */
@RestController
@RequestMapping("/educms/banneradmin")
@MapperScan("com.zxjy.educms.mapper")
@Api(description = "Banner后台对接")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "分页查询banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            long limit){

        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

