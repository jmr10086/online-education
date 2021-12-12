package com.zxjy.servicebase.exceptionHandler;

import com.zxjy.commonutils.R;
import com.zxjy.exceptionutil.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jcheng
 * @since 2021/11/18 15:55
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){

        log.error(ExceptionUtil.getMessage(e));
        // e.printStackTrace();
        return R.error().message("全局异常错误");
    }

    @ExceptionHandler(zxjyException.class)
    @ResponseBody
    public R error(zxjyException e){

        log.error(ExceptionUtil.getMessage(e));
        // e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
