package com.zxjy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxjy.commonutils.R;
import com.zxjy.eduservice.entity.EduCourse;
import com.zxjy.eduservice.entity.EduTeacher;
import com.zxjy.eduservice.service.EduCourseService;
import com.zxjy.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/26 16:36
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@Api(description = "首页讲师、课程数据")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    // 查询前8条热门课程，查询前4条名师
    @GetMapping("/index")
    public R index(){

        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseQueryWrapper);

        QueryWrapper<EduTeacher> teacherQueryWrapper =new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherQueryWrapper);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
