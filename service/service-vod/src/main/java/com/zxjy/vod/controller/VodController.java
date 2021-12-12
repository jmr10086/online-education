package com.zxjy.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zxjy.commonutils.R;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import com.zxjy.vod.service.VodService;
import com.zxjy.vod.utils.ConstantVodUtils;
import com.zxjy.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/23 15:51
 */
@RestController
@RequestMapping("/eduvod/video")
@Api(description = "阿里云视频点播")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadAlyVideo")
    @ApiOperation(value = "上传视频")
    public R uploadAlyVideo(
            @ApiParam(name = "file", value = "视频文件")
                    MultipartFile file) {
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @ApiOperation(value = "删除阿里云视频")
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(
            @ApiParam(name = "id", value = "阿里云视频id", required = true)
            @PathVariable String id) {

        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new zxjyException(ResultCode.ERROR, "删除视频失败");
        }
    }

    @ApiOperation(value = "删除多个阿里云视频")
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreALYVideo(videoIdList);
        return R.ok();
    }

    @ApiOperation(value = "获取视频凭证")
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new zxjyException(ResultCode.ERROR,"获取凭证失败");
        }
    }
}

