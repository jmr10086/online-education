package com.zxjy.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.commonutils.R;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.eduservice.entity.EduCourse;
import com.zxjy.eduservice.entity.vo.CourseInfoVo;
import com.zxjy.eduservice.entity.vo.CoursePublishVo;
import com.zxjy.eduservice.entity.vo.CourseQuery;
import com.zxjy.eduservice.service.EduCourseService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
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
@RequestMapping("/eduservice/course")
@Api(description = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        //返回添加之后的id，为后面做大纲用
        return R.ok().data("courseId", id);
    }

    @ApiOperation(value = "课程ID查基本信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "课程基本信息")
            @RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "课程id查课程信息")
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("courseInfo", coursePublishVo);
    }

    @ApiOperation(value = "发布课程")
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        eduCourseService.updateById(course);
        return R.ok();
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping("/findAll/{current}/{limit}")
    public R pageListCourseInfo(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQuery courseQuery) {

        if (current <= 0 || limit <= 0) {
            throw new zxjyException(ResultCode.ERROR, "参数不正确");
        }

        Page<EduCourse> coursePage = new Page<>(current, limit);
        eduCourseService.pageQuery(coursePage, courseQuery);
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "课程删除")
    @DeleteMapping("/{courseId}")
    public R removeCourse(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        eduCourseService.removeCourseById(courseId);
        return R.ok();
    }
}

