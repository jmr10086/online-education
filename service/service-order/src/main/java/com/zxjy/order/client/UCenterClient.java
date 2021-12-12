package com.zxjy.order.client;

import com.zxjy.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jcheng
 * @since 2021/12/5 13:13
 */
@Component
@FeignClient("service-ucenter")
public interface UCenterClient {
    //根据用户id获取用户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    UcenterMemberVo getInfo(@PathVariable("id") String id);
}
