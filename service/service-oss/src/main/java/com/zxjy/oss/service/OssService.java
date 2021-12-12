package com.zxjy.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author jcheng
 * @since 2021/11/19 16:56
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
