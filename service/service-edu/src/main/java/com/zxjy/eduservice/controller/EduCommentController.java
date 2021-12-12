package com.zxjy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxjy.commonutils.JwtUtils;
import com.zxjy.commonutils.R;
import com.zxjy.commonutils.ResultCode;
import com.zxjy.eduservice.client.CommentClient;
import com.zxjy.eduservice.entity.EduComment;
import com.zxjy.eduservice.service.EduCommentService;
import com.zxjy.servicebase.exceptionHandler.zxjyException;
import com.zxjy.vo.UcenterMemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/eduservice/comment")
@Api(description = "评论")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private CommentClient commentClient;

    @ApiOperation(value = "分页评论")
    @GetMapping("/{page}/{limit}")
    public R pagination(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页条数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseId", value = "课程id", required = false)
                      String courseId) {


        Page<EduComment> commentPage = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();

        //判断课程id是否为空
        if (!StringUtils.isEmpty(courseId)){
            wrapper.eq("course_id",courseId);
        }

        //按最新排序
        wrapper.orderByDesc("gmt_create");

        //数据会被封装到commentPage中
        commentService.page(commentPage,wrapper);

        List<EduComment> commentList = commentPage.getRecords();
        long current = commentPage.getCurrent();//当前页
        long size = commentPage.getSize();//一页记录数
        long total = commentPage.getTotal();//总记录数
        long pages = commentPage.getPages();//总页数
        boolean hasPrevious = commentPage.hasPrevious();//是否有上页
        boolean hasNext = commentPage.hasNext();//是否有下页

        HashMap<String, Object> map = new HashMap<>();
        map.put("current",current);
        map.put("size",size);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        map.put("list",commentList);

        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("/auth/saveComment")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if (StringUtils.isEmpty(memberId)) {
            throw new zxjyException(ResultCode.ERROR, "请登录");
        }

        comment.setMemberId(memberId);

        UcenterMemberVo ucenterMemberVo = commentClient.getInfo(memberId);

        comment.setNickname(ucenterMemberVo.getNickname());
        comment.setAvatar(ucenterMemberVo.getAvatar());
        commentService.save(comment);

        return R.ok();
    }
}
