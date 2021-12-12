package com.zxjy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxjy.eduservice.entity.EduSubject;
import com.zxjy.eduservice.entity.excel.SubjectData;
import com.zxjy.eduservice.entity.subject.OneSubject;
import com.zxjy.eduservice.entity.subject.TwoSubject;
import com.zxjy.eduservice.listener.SubjectExcelListener;
import com.zxjy.eduservice.mapper.EduSubjectMapper;
import com.zxjy.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     *
     * @param file 文件
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调方法读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询课程分类（树形）
     * @return 返回分类数据
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //查所有1级分类  pid=0
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);

        //查所有2级分类  pid!=0
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        List<OneSubject> oneFinalSubjectList = new ArrayList<>();

        //封装1级数据 遍历 重新封装
        Iterator<EduSubject> it = oneSubjectList.iterator();
        while (it.hasNext()) {
            EduSubject eduSubject = it.next();
            OneSubject oneSubject = new OneSubject();
            //把eduSubject对象复制到oneSubject
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //封装
            oneFinalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //1级分类遍历中 循环2级数据 再封装
            for (EduSubject twoSubjectElement : twoSubjectList) {
                EduSubject tSubject = twoSubjectElement;
                if (tSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject twoSubject =new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //封装
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return oneFinalSubjectList;
    }
}
