package com.zxjy.educms.service;

import com.zxjy.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-26
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //查所有banner
    List<CrmBanner> selectAllBanner();
}
