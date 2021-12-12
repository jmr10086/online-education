package com.zxjy.eduservice.client;

import com.zxjy.commonutils.R;
import com.zxjy.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jcheng
 * @since 2021/12/4 8:58
 */
@Component
@FeignClient(name = "service-ucenter",fallback = CommentClientImpl.class)
public interface CommentClient {

    //获取用户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    UcenterMemberVo getInfo(@PathVariable String id);
}
