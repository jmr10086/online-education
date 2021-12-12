package com.zxjy.order.service;

import com.zxjy.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-04
 */
public interface OrderService extends IService<Order> {
    String createOrders(String courseId, String jwtToken);
}
