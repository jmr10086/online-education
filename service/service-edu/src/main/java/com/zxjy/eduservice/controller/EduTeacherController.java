package com.zxjy.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.commonutils.R;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.eduservice.entity.EduTeacher;
import com.zxjy.eduservice.entity.vo.TeacherQuery;
import com.zxjy.eduservice.service.EduTeacherService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-18
 */
@RestController
@RequestMapping("/eduservice/teacher")
@Api(description = "讲师管理")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "所有讲师列表")
    public R list() {

        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    @ApiOperation(value = "ID逻辑删除讲师")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "分页讲师列表")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody(required = false) TeacherQuery teacherQuery) {

        if (current <= 0 || limit <= 0) {
            throw new zxjyException(ResultCode.ERROR,"参数不正确");
        }

        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        teacherService.pageQuery(pageTeacher,teacherQuery);
        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {

        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @PostMapping("/updateTeacher")
    @ApiOperation(value = "修改讲师")
    public R updateById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }
}

