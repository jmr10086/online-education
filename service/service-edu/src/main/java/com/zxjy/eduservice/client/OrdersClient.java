package com.zxjy.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jcheng
 * @since 2021/12/6 22:22
 */
@Component
@FeignClient(value = "service-order",fallback = OrdersClientImpl.class)
public interface OrdersClient {

    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
