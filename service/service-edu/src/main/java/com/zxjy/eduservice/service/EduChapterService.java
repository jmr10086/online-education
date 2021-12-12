package com.zxjy.eduservice.service;

import com.zxjy.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxjy.eduservice.entity.chapter.ChapterVo;
import com.zxjy.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
public interface EduChapterService extends IService<EduChapter> {

    // 课程大纲列表 根据课程id查
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节的方法
    boolean deleteChapter(String chapterId);

    void removeChapterById(String courseId);
}
