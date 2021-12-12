package com.zxjy.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxjy.eduservice.entity.EduCourse;
import com.zxjy.eduservice.entity.frontvo.CourseWebVo;
import com.zxjy.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
