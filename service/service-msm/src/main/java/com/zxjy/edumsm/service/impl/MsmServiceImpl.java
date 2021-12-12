package com.zxjy.edumsm.service.impl;

import com.tencentcloudapi.sms.v20190711.models.PullSmsSendStatusRequest;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import com.zxjy.commonutils.R;
import com.zxjy.edumsm.service.MsmService;
import com.zxjy.edumsm.utils.SendSmsUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author jcheng
 * @since 2021/11/27 18:57
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String[] code, String[] phones) {
        if (StringUtils.isEmpty(phones)) {
            return false;
        } else{
            SendSmsUtil.sendSms(phones, code);
            return true;
        }
    }
}
