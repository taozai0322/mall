package com.ym.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.ym.mall.mapper.CmsSubjectMapper;
import com.ym.mall.model.CmsSubject;
import com.ym.mall.model.CmsSubjectExample;
import com.ym.mall.service.CmsSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品专题管理Service的实现类
 * @author matao
 * @create 2019-08-21 10:37
 */
@Service
@Slf4j
public class CmsSubjectServiceImpl implements CmsSubjectService {

    @Autowired
    private CmsSubjectMapper cmsSubjectMapper;

    /**
     * 获取所有的商品专题
     * @return
     */
    @Override
    public List<CmsSubject> getAllCmsSubject() {
        return cmsSubjectMapper.selectByExample(new CmsSubjectExample());
    }

    /**
     * 分页或者根据关键字查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<CmsSubject> getCmsSubjectByPageOrKeyword(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        CmsSubjectExample subjectExample = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = subjectExample.createCriteria();
        if(!StringUtils.isEmpty(keyword)){
            criteria.andTitleLike(keyword);
        }
        List<CmsSubject> subjectList = cmsSubjectMapper.selectByExample(subjectExample);
        return subjectList;
    }
}
