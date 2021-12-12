package com.zxjy.eduservice.client;

import com.zxjy.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/24 22:48
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        System.out.println();
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
