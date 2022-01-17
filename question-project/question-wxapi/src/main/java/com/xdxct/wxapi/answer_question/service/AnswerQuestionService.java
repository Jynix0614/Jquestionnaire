package com.xdxct.wxapi.answer_question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdxct.wxapi.answer_question.entity.AnswerQuestion;
import com.xdxct.wxapi.answer_question.entity.CommitParm;

/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/1619:29
 */
public interface AnswerQuestionService extends IService<AnswerQuestion> {
    void saveCommit(CommitParm commitParm);
}
