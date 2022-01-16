package com.xdxct.wxapi.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdxct.utils.ResultUtils;
import com.xdxct.utils.ResultVo;
import com.xdxct.wxapi.answer_question.entity.AnswerQuestion;
import com.xdxct.wxapi.answer_question.service.AnswerQuestionService;
import com.xdxct.wxapi.question.entity.QuestionParm;
import com.xdxct.wxapi.question.entity.SysQuestion;
import com.xdxct.wxapi.question.service.SysQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姜瑜欣
 * @description: 首页控制器
 * @date 2022/1/163:44
 */
@RestController
@RequestMapping("/wxapi/home")
public class HomeController {
    @Autowired
    private SysQuestionService sysQuestionService;

    @Autowired
    private AnswerQuestionService answerQuestionService;

    /**
     * 查询首页列表
     */
    @GetMapping("/getList")
    public ResultVo getList(QuestionParm questionParm){
        //构造分页对象
        IPage<SysQuestion> page = new Page<>();
        page.setCurrent(questionParm.getCurrentPage());
        page.setSize(questionParm.getPageSize());
        IPage<SysQuestion> list = sysQuestionService.getList(page);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 问卷详情查询
     */
    @GetMapping("/getDetails")
    public ResultVo getDetails(String questionId,String openid){
        //根据问卷Id查询
        SysQuestion question = sysQuestionService.getById(questionId);
        //根据问卷Id和openid查询
        QueryWrapper<AnswerQuestion> query = new QueryWrapper<>();
        query.lambda().eq(AnswerQuestion::getQuestionId,questionId).eq(AnswerQuestion::getOpenid,openid);
        AnswerQuestion answerQuestion = answerQuestionService.getOne(query);
        if(answerQuestion != null){
            question.setStatus("1");//已答卷
        }else {
            question.setStatus("0");//未答卷
        }
        return ResultUtils.success("查询成功！",question);
    }
}
