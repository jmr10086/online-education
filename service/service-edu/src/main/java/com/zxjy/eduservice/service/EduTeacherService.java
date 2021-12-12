package com.zxjy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxjy.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-18
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
