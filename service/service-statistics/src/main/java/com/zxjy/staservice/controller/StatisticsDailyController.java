package com.zxjy.staservice.controller;


import com.zxjy.commonutils.R;
import com.zxjy.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-07
 */
@RestController
@RequestMapping("/staservice/sta")
@Api(value = "统计图表")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "统计某天注册人数")
    @PostMapping("/registerCount/{day}")
    public R registerCount(
            @ApiParam(name="day", value = "日期",required = true)
            @PathVariable String day){

        statisticsDailyService.registerCount(day);
        return R.ok();
    }

    @ApiOperation(value = "图表显示")
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

