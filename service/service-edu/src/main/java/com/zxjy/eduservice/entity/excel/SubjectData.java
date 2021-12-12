package com.zxjy.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jcheng
 * @since 2021/11/20 11:41
 */
@Data
public class SubjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
