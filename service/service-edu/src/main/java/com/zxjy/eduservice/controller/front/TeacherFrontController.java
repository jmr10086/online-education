package com.zxjy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.commonutils.R;
import com.zxjy.eduservice.entity.EduCourse;
import com.zxjy.eduservice.entity.EduTeacher;
import com.zxjy.eduservice.service.EduCourseService;
import com.zxjy.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jcheng
 * @since 2021/12/2 14:59
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@Api(description = "前台讲师数据")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询讲师方法
     * @param page 当前页
     * @param limit 条数
     * @return 讲师结果集
     */
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Long page, @PathVariable Long limit){



        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);
        //返回分页中的所有数据
        return R.ok().data(map);
    }

    /**
     * 查询讲师详情信息
     * @param teacherId 讲师id
     * @return 返回讲师对象信息
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public  R getTeacherFrontInfo(@PathVariable String teacherId){
        // 1.根据讲师id查询讲师基本信息
        EduTeacher teacherInfo = teacherService.getById(teacherId);
        // 2.根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper =new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",teacherInfo).data("courseList",courseList);
    }
}
