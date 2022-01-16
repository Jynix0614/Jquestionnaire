package com.xdxct.wxapi.question.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdxct.wxapi.question.entity.SysQuestion;
import com.xdxct.wxapi.question.service.SysQuestionService;
import com.xdxct.wxapi.question.mapper.SysQuestionMapper;
import org.springframework.stereotype.Service;

/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/14 17:38
 */
@Service
public class SysQuestionServiceImpl extends ServiceImpl<SysQuestionMapper, SysQuestion> implements SysQuestionService {


    @Override
    public IPage<SysQuestion> getList(IPage<SysQuestion> page) {
        return this.baseMapper.getList(page);
    }
}
