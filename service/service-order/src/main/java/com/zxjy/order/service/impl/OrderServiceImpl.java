package com.zxjy.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxjy.order.client.EduClient;
import com.zxjy.order.client.UCenterClient;
import com.zxjy.order.entity.Order;
import com.zxjy.order.mapper.OrderMapper;
import com.zxjy.order.service.OrderService;
import com.zxjy.order.utils.OrderNoUtils;
import com.zxjy.vo.CourseWebVoOrder;
import com.zxjy.vo.UcenterMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Qualifier("com.zxjy.order.client.UCenterClient")
    @Autowired
    private UCenterClient uCenterClient;

    // 生成订单方法
    @Override
    public String createOrders(String courseId, String jwtToken) {

        // 通过远程调用根据用户id获取用户信息
        UcenterMemberVo centerClientInfo = uCenterClient.getInfo(jwtToken);
        // 通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        // 创建Order对象,向Order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtils.getOrderNo()); //订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(jwtToken);
        order.setMobile(centerClientInfo.getMobile());
        order.setNickname(centerClientInfo.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1

        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
