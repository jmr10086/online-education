package com.zxjy.order.controller;


import com.zxjy.commonutils.R;
import com.zxjy.order.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/eduorder/paylog")
@Api(value = "支付功能")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation(value = "微信二维码支付")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {

        Map map = payLogService.createNative(orderNo);
        System.out.println("****返回二维码map集合:" + map);
        return R.ok().data(map);
    }

    @ApiOperation(value = "订单支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {

        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合:" + map);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {//支付成功
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

