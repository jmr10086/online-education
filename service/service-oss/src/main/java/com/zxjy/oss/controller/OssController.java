package com.zxjy.oss.controller;

import com.zxjy.commonutils.R;
import com.zxjy.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jcheng
 * @since 2021/11/19 16:54
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@Api(description = "OSS存储")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像
     *
     * @param file 头像文件
     * @return 返回上传到Oss路径
     */
    @PostMapping
    @ApiOperation(value = "文件上传")
    public R uploadOssFile(@ApiParam(name = "file", value = "文件")
                                   MultipartFile file) {

        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
