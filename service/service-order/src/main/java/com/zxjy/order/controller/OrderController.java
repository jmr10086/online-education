package com.zxjy.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.x.protobuf.Mysqlx;
import com.sun.org.apache.regexp.internal.RE;
import com.zxjy.commonutils.JwtUtils;
import com.zxjy.commonutils.R;
import com.zxjy.order.entity.Order;
import com.zxjy.order.service.OrderService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/eduorder/order")
@Api(description = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "生成订单")
    @PostMapping("/createOder/{courseId}")
    public R createOder(
            @ApiParam(name = "courseId",value = "课程id",required = true)
            @PathVariable String courseId,
            HttpServletRequest request){

        String jwtToken = JwtUtils.getMemberIdByJwtToken(request);

        String orderNo =  orderService.createOrders(courseId,jwtToken);
        return R.ok().data("orderId",orderNo);
    }

    @ApiOperation(value = "根据订单id查订单信息")
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    @ApiOperation(value = "查询购买状态")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){

        QueryWrapper<Order> wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);

        int count = orderService.count(wrapper);
        if (count>0) { //已经支付
            return true;
        }else {
            return false;
        }
    }
}

