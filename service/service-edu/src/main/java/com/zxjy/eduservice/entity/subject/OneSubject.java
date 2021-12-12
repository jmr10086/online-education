package com.zxjy.eduservice.entity.subject;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jcheng
 * @since 2021/11/20 14:17
 */
@Data
public class OneSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
