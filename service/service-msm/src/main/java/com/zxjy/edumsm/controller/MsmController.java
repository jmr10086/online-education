package com.zxjy.edumsm.controller;

import com.zxjy.commonutils.R;
import com.zxjy.edumsm.service.MsmService;
import com.zxjy.edumsm.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author jcheng
 * @since 2021/11/27 18:55
 */
@RestController
@RequestMapping("/edumsm/msm")
@Api(description = "短信服务")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    @ApiOperation(value = "发送短信")
    public R sendMsm(@PathVariable(value = "phone") String phone) {

        String[] phones = {"+86" + phone};
        String redisP = null;
        for (int i = 0; i < phones.length; i++) {
            redisP = phones[i];
        }

        //1 从redis获取验证码，如果获取到直接返回
        String rCode = redisTemplate.opsForValue().get(redisP);
        if (!StringUtils.isEmpty(rCode)) {
            return R.ok();
        }

        // 随机数code
        rCode = RandomUtil.getSixBitRandom();
        String[] code = {rCode};

        String redisCode = null;
        for (int i = 0; i < code.length; i++) {
            redisCode = code[i];
        }
        //调用service发送短信的方法
        boolean isSend = msmService.send(code, phones);
        if (isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(redisP, redisCode, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
