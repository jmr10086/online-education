package com.zxjy.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jcheng
 * @since 2021/11/18 14:21
 */
@ApiModel(value = "教师查询对象",description = "讲师查询封装")
@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师查询,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔等级，1高级讲师，2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2020-01-01 08:08:08")
    private String begin;//String 类型前端传来的数据无需转型

    @ApiModelProperty(value = "查询结束时间",example = "2020-01-01 08:08:08")
    private String end;
}
