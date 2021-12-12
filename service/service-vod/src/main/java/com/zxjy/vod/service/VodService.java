package com.zxjy.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/23 15:53
 */

public interface VodService {

    //上传视频到阿里云
    String uploadAlyVideo(MultipartFile file);

    // 删除多个阿里云视频
    void removeMoreALYVideo(List videoIdList);
}
