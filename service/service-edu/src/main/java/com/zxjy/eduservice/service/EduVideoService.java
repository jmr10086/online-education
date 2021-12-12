package com.zxjy.eduservice.service;

import com.zxjy.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoById(String courseId);
}
