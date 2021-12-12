package com.zxjy.staservice.schedule;

import com.zxjy.staservice.service.StatisticsDailyService;
import com.zxjy.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jcheng
 * @since 2021/12/7 13:53
 */

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // 每五分钟执行一次
    // @Scheduled(cron = "0 0/5 * * * ? ")
    public void task1(){
        System.out.println("************task1执行");
    }

    // 每天早上7点，把数据添加
    @Scheduled(cron = "0 0 6 * * ? ")
    public void task2(){
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
