package com.zxjy.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/21 14:08
 */
@Data
@ApiModel(value = "章节信息")
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
