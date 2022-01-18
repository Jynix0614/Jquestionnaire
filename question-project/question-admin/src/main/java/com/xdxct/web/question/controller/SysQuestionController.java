package com.xdxct.web.question.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdxct.utils.ResultUtils;
import com.xdxct.utils.ResultVo;
import com.xdxct.web.question.entity.QuestionParm;
import com.xdxct.web.question.entity.SysQuestion;
import com.xdxct.web.question.service.SysQuestionService;
import com.xdxct.web.sys_paper.entity.SysPaper;
import com.xdxct.web.sys_paper.service.SysPaperService;
import com.xdxct.web.sys_paper_choice.entity.SysPaperChoice;
import com.xdxct.web.sys_paper_choice.service.SysPaperChoiceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 姜瑜欣
 * @description: 问题控制器
 * @date 2022/1/1417:41
 */

@RestController
@RequestMapping("/api/question")
public class SysQuestionController {
    @Autowired
    private SysQuestionService sysQuestionService;

    @Autowired
    private SysPaperChoiceService sysPaperChoiceService;

    @Autowired
    private SysPaperService sysPaperService;

    /**
     * 新增问卷
     */
    @PostMapping
    public ResultVo add(@RequestBody SysQuestion sysQuestion){
        boolean b = sysQuestionService.save(sysQuestion);
        if(b){
            return ResultUtils.success("新增问卷成功！");
        }
        return ResultUtils.error("新增问卷失败！");
    }

    /**
     * 编辑问卷
     */
    @PutMapping
    public ResultVo edit(@RequestBody SysQuestion sysQuestion){
        boolean b = sysQuestionService.updateById(sysQuestion);
        if (b){
            return ResultUtils.success("编辑问卷成功！");
        }
        return ResultUtils.error("编辑问卷失败！");
    }

    /**
     * 删除问卷
     */
    @DeleteMapping("/{questionId}")
    public ResultVo delete(@PathVariable("questionId") Long questionId){
        boolean b = sysQuestionService.removeById(questionId);
        if (b){
            return ResultUtils.success("删除问卷成功！");
        }
        return ResultUtils.error("删除问卷失败！");
    }

    /**
     * 问卷列表
     */
    @GetMapping("/list")
    public ResultVo getList(QuestionParm questionParm){
        IPage<SysQuestion> page = new Page<>();
        page.setCurrent(questionParm.getCurrentPage());
        page.setSize(questionParm.getPageSize());
        //构造查询对象
        QueryWrapper<SysQuestion> query = new QueryWrapper<>();
        //模糊查询
        if (StringUtils.isNotEmpty(questionParm.getQuestionTitle())){
            query.lambda().like(SysQuestion::getQuestionTitle,questionParm.getQuestionTitle());
        }
        //排序
        query.lambda().orderByAsc(SysQuestion::getQuestionOrder);

        IPage<SysQuestion> list = sysQuestionService.page(page, query);
        return ResultUtils.success("查询成功！",list);
    }

    /**
     * 问卷统计查询
     */
    @GetMapping("/getTotalList")
    public ResultVo getTotalList(Long questionId){
        //1、查询问卷详情
        SysQuestion sysQuestion = sysQuestionService.getById(questionId);
        //2、查询问卷的所有试题
        QueryWrapper<SysPaper> query = new QueryWrapper<>();
        query.lambda().eq(SysPaper::getQuestionId,questionId)
                .orderByAsc(SysPaper::getPaperOrder);
        List<SysPaper> paperList = sysPaperService.list(query);
        //3查询试题对应的选项统计
        List<SysPaperChoice> choiceList = sysPaperChoiceService.getListPaperChoice(questionId);
        for (int i=0;i<paperList.size();i++){
            List<SysPaperChoice> paperChoice = new ArrayList<>();
            Long paperId = paperList.get(i).getPaperId();
            choiceList.stream().filter(item -> item.getPaperId().equals(paperId)).forEach(item->{
                SysPaperChoice choice = new SysPaperChoice();
                BeanUtils.copyProperties(item,choice);
                paperChoice.add(choice);
            });
            //设置单选、多选的选项
            paperList.get(i).setPaperChoice(paperChoice);
        }
        sysQuestion.setListPaper(paperList);
        return ResultUtils.success("查询成功",sysQuestion);
    }
}
