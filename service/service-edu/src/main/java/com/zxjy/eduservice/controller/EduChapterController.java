package com.zxjy.eduservice.controller;


import com.zxjy.commonutils.R;
import com.zxjy.eduservice.entity.EduChapter;
import com.zxjy.eduservice.entity.chapter.ChapterVo;
import com.zxjy.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(description = "课程章节管理")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "根据课程ID查询")
    //课程大纲列表,根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    //根据章节id查询
    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    //删除的方法
    @ApiOperation(value = "删除章节")
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    public R addChapter(
            @ApiParam(name = "eduChapter", value = "章节")
            @RequestBody EduChapter eduChapter) {

        chapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(
            @ApiParam(name = "eduChapter", value = "章节")
            @RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }
}

