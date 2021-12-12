package com.zxjy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.eduservice.entity.EduCourse;
import com.zxjy.eduservice.entity.EduCourseDescription;
import com.zxjy.eduservice.entity.EduTeacher;
import com.zxjy.eduservice.entity.frontvo.CourseFrontVo;
import com.zxjy.eduservice.entity.frontvo.CourseWebVo;
import com.zxjy.eduservice.entity.vo.CourseInfoVo;
import com.zxjy.eduservice.entity.vo.CoursePublishVo;
import com.zxjy.eduservice.entity.vo.CourseQuery;
import com.zxjy.eduservice.mapper.EduCourseMapper;
import com.zxjy.eduservice.service.EduChapterService;
import com.zxjy.eduservice.service.EduCourseDescriptionService;
import com.zxjy.eduservice.service.EduCourseService;
import com.zxjy.eduservice.service.EduVideoService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import org.omg.CORBA.IRObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;


    /**
     * 添加课程基本信息的方法
     *
     * @param courseInfoVo 课程信息对象
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new zxjyException(ResultCode.ERROR, "添加课程信息失败");
        }
        //获取添加之后的id
        String cid = eduCourse.getId();
        //给课程简介表添加详细信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查课程
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //查课程基本信息
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.updateById(eduCourse);
        if (insert == 0) {
            throw new zxjyException(ResultCode.ERROR, "修改课程失败");
        }
        //修改课程详情
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo courseInfoVo = baseMapper.getPublishCourseInfo(id);
        return courseInfoVo;
    }

    @Override
    public void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("gmt_modified");

        if (courseQuery == null) {
            baseMapper.selectPage(coursePage, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        baseMapper.selectPage(coursePage, queryWrapper);
    }

    @Override
    public void removeCourseById(String courseId) {

        //删除小节
        videoService.removeVideoById(courseId);
        //删除章节
        chapterService.removeChapterById(courseId);
        //删除描述
        courseDescriptionService.removeById(courseId);
        //删除课程
        int delete = baseMapper.deleteById(courseId);
        if (delete == 0) {
            throw new zxjyException(ResultCode.ERROR, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {// 关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { // 更新时间
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) { //价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage, wrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext(); // 下一页
        boolean hasPrevious = coursePage.hasPrevious();// 上一页

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
