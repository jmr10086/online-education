package com.zxjy.staservice.service;

import com.zxjy.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-07
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    // 统计某天注册人数
    void registerCount(String day);

    // 图表显示
    Map<String, Object> getShowData(String type, String begin, String end);
}
