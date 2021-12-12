package com.zxjy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxjy.eduservice.entity.frontvo.CourseFrontVo;
import com.zxjy.eduservice.entity.frontvo.CourseWebVo;
import com.zxjy.eduservice.entity.vo.CourseInfoVo;
import com.zxjy.eduservice.entity.vo.CoursePublishVo;
import com.zxjy.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
public interface EduCourseService extends IService<EduCourse> {

    //新增课程
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程ID查基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 课程id查课程信息
    CoursePublishVo publishCourseInfo(String id);

    //分页查询课程
    void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery);

    // 删除课程
    void removeCourseById(String courseId);

    // 条件查询带分页查询课程前台
    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    // 课程id查询详情方法
    CourseWebVo getBaseCourseInfo(String courseId);
}
