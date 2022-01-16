package com.xdxct.wxapi.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xdxct.wxapi.question.entity.SysQuestion;

/**
 * @author 姜瑜欣
 * @description: 问题 数据访问层
 * @date 2022/1/1417:34
 */
public interface SysQuestionMapper extends BaseMapper<SysQuestion> {
    //获取首页列表
    IPage<SysQuestion> getList(IPage<SysQuestion> page);
}
