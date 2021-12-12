package com.zxjy.order.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author jcheng
 * @since 2021/12/5 16:44
 */
public class OrderNoUtils {
    /**
     * 获取订单号
     * @return
     */
    public static String getOrderNo() {
        String newDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
}
