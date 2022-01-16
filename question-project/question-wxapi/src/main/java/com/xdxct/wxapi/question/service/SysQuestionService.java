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
    IPage<SysQuestion> getList(IPage<SysQuestion> page);
}
