package com.zxjy.edumsm.service;

import java.util.Map;

/**
 * @author jcheng
 * @since 2021/11/27 18:56
 */
public interface MsmService {
    boolean send(String[] code, String[] phones);
}
