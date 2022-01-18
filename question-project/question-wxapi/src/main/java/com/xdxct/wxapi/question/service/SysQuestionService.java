package com.xdxct.wxapi.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdxct.wxapi.question.entity.SysQuestion;


/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/1417:37
 */
public interface SysQuestionService extends IService<SysQuestion> {
    //获取首页列表
    IPage<SysQuestion> getList(IPage<SysQuestion> page);

    //我的问卷列表
    IPage<SysQuestion> getMyQuestionList(IPage<SysQuestion> page, String openid);
    //查询问卷回显列表
    SysQuestion getMyPaperList(Long questionId,String openid);
}
