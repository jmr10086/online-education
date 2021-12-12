package com.zxjy.eduservice.controller;


import com.zxjy.commonutils.R;
import com.zxjy.eduservice.entity.subject.OneSubject;
import com.zxjy.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-19
 */

@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(@ApiParam(name = "file",value = "文件") MultipartFile file){

        // 获取上传的excel文件 MultipartFile
        //返回错误提示信息
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @ApiOperation(value = "课程分类列表")
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        //List装一级分类  一级里有二级
        List<OneSubject> subjectList = subjectService.getAllOneTwoSubject();
        return R.ok().data("items", subjectList);
    }
}

