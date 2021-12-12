package com.zxjy.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.commonutils.JwtUtils;
import com.zxjy.commonutils.R;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.eduservice.client.OrdersClient;
import com.zxjy.eduservice.entity.EduCourse;
import com.zxjy.eduservice.entity.chapter.ChapterVo;
import com.zxjy.eduservice.entity.frontvo.CourseFrontVo;
import com.zxjy.eduservice.entity.frontvo.CourseWebVo;
import com.zxjy.eduservice.service.EduChapterService;
import com.zxjy.eduservice.service.EduCourseService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import com.zxjy.vo.CourseWebVoOrder;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author jcheng
 * @since 2021/12/2 23:48
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@Api(description = "前台课程数据")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    /**
     * 条件查询带分页查询课程前台
     * @param page 当前页
     * @param limit 每页数量
     * @param courseFrontVo 条件封装类
     * @return 返回查询结果集
     */
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCodeList(@PathVariable Long page, @PathVariable Long limit,
                              @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        if (page <= 0 || limit <= 0) {
            throw new zxjyException(ResultCode.ERROR, "参数不正确");
        }
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(coursePage, courseFrontVo);

        return R.ok().data(map);
    }

    // 课程详情查询方法
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        // 根据课程id,查询sql语句
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        // 根据课程id和用户id查询当前课程是否已经支付过了
        Boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        // 根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("chapterVideoList",chapterVideoList).data("courseWebVo",courseWebVo).data("isBuy",buyCourse);
    }

    // 根据课程id查询课程信息
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseWebInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
