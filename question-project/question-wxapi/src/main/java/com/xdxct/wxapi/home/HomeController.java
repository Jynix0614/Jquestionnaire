package com.xdxct.wxapi.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.jndi.cosnaming.CNCtx;
import com.xdxct.utils.ResultUtils;
import com.xdxct.utils.ResultVo;
import com.xdxct.wxapi.answer_question.entity.AnswerQuestion;
import com.xdxct.wxapi.answer_question.entity.CommitParm;
import com.xdxct.wxapi.answer_question.service.AnswerQuestionService;
import com.xdxct.wxapi.question.entity.QuestionParm;
import com.xdxct.wxapi.question.entity.SysQuestion;
import com.xdxct.wxapi.question.service.SysQuestionService;
import com.xdxct.wxapi.sys_paper.entity.SysPaper;
import com.xdxct.wxapi.sys_paper.service.SysPaperService;
import com.xdxct.wxapi.sys_paper_choice.entity.SysPaperChoice;
import com.xdxct.wxapi.sys_paper_choice.service.SysPaperChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private SysPaperService sysPaperService;

    @Autowired
    private SysPaperChoiceService sysPaperChoiceService;

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

    /**
     * 根据问卷id查询试题列表
     *
     * 1、查询问卷
     * 2、根据问卷id查试题列表
     * 3、查询单选和多选对应的选项
     */
    @GetMapping("/getPaperList")
    public ResultVo getPaperList(String questionId){
        // 1、查询问卷
        SysQuestion question = sysQuestionService.getById(questionId);
        //2、根据问卷id查试题列表
        //构造查询条件
        QueryWrapper<SysPaper> query = new QueryWrapper<>();
        query.lambda().eq(SysPaper::getQuestionId,questionId);
        //返回试题列表
        List<SysPaper> paperList = sysPaperService.list(query);
        // 3、查询单选和多选对应的选项
        for (int i=0;i<paperList.size();i++){
            //如果是单选或者多选的时候，需要查询选项
            if(paperList.get(i).getPaperType().equals("1") || paperList.get(i).getPaperType().equals("2")){
                QueryWrapper<SysPaperChoice> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SysPaperChoice::getPaperId,paperList.get(i).getPaperId());
                List<SysPaperChoice> paperChoices = sysPaperChoiceService.list(queryWrapper);
                //设置单选和多选的选项
                paperList.get(i).setPaperChoice(paperChoices);
            }
        }
        //给问卷设置试题
        question.setListPaper(paperList);
        return ResultUtils.success("查询成功",question);
    }


    /**
     * 保存问卷
     */
    @PostMapping("/saveCommit")
    public ResultVo saveCommit(@RequestBody CommitParm parm){
        answerQuestionService.saveCommit(parm);
        return ResultUtils.success("提交成功！");
    }

    /**
     * 查询试题回显
     */
    @GetMapping("/getMyPaperListShow")
    public ResultVo getMyPaperListShow(String openid,Long questionId){
        SysQuestion list = sysQuestionService.getMyPaperList(questionId,openid);
        return ResultUtils.success("查询成功",list);
    }
    /**
     * 查询我的列表
     */
    @GetMapping("/getMyQuestionList")
    public ResultVo getMyQuestionList(QuestionParm questionParm){
        //构造分页对象
        IPage<SysQuestion> page = new Page<>();
        page.setCurrent(questionParm.getCurrentPage());
        page.setSize(questionParm.getPageSize());
        IPage<SysQuestion> list = sysQuestionService.getMyQuestionList(page,questionParm.getOpenid());
        return ResultUtils.success("我的列表查询成功",list);
    }
}
