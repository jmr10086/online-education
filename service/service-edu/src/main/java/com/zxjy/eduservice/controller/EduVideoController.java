package com.zxjy.eduservice.controller;


import com.zxjy.commonutils.R;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.eduservice.client.VodClient;
import com.zxjy.eduservice.entity.EduVideo;
import com.zxjy.eduservice.service.EduVideoService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/eduservice/video")
@Api(description = "课程视频")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @ApiOperation(value = "添加小节")
    @PostMapping("/addVideo")
    public R addVideo(
            @ApiParam(name = "eduVideo", value = "小节")
            @RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // TODO 删除小节的同时删除小节视频
    @ApiOperation(value = "删除小节")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "小节id", required = true)
            @PathVariable String id) {

        //删小节视频
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (!result.getSuccess()) {
                throw new zxjyException(ResultCode.ERROR,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

    // TODO 修改小节
}

