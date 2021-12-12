package com.zxjy.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jcheng
 * @since 2021/11/22 23:26
 */
@Data
@ApiModel(value = "课程查询对象",description = "课程查询封装")
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程查询,模糊查询")
    private String title;

    @ApiModelProperty(value = "发布状态,Normal已发布，Draft未发布")
    private String status;

}
