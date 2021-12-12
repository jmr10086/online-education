package com.zxjy.servicebase.exceptionHandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jcheng
 * @since 2021/11/18 16:23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class zxjyException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;

    @Override
    public String toString() {
        return "zxjyException{" +
                "code=" + code +
                ", msg='" + this.getMsg() + '\'' +
                '}';
    }
}
