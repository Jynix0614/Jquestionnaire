package com.xdxct.wxapi.answer_question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdxct.wxapi.answer_paper.entity.AnswerPaper;
import com.xdxct.wxapi.answer_paper.service.AnswerPaperService;
import com.xdxct.wxapi.answer_paper_choice.entity.AnswerPaperChoice;
import com.xdxct.wxapi.answer_paper_choice.service.AnswerPaperChoiceService;
import com.xdxct.wxapi.answer_question.entity.AnswerQuestion;
import com.xdxct.wxapi.answer_question.entity.CommitParm;
import com.xdxct.wxapi.answer_question.entity.PaperParm;
import com.xdxct.wxapi.answer_question.mapper.AnswerQuestionMapper;
import com.xdxct.wxapi.answer_question.service.AnswerQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/1619:30
 */
@Service
public class AnswerQuestionServiceImpl extends ServiceImpl<AnswerQuestionMapper, AnswerQuestion> implements AnswerQuestionService {

    @Autowired
    private AnswerPaperChoiceService answerPaperChoiceService;
    @Autowired
    private AnswerPaperService answerPaperService;


    @Override
    @Transactional
    public void saveCommit(CommitParm parm) {
        //保存answer_question表
        AnswerQuestion answerQuestion = new AnswerQuestion();
        answerQuestion.setOpenid(parm.getOpenid());
        answerQuestion.setQuestionId(parm.getQuestionId());
        answerQuestion.setCreateTime(new Date());
        this.baseMapper.insert(answerQuestion);
        //保存answer_paper表
        //前端传来的试题
        List<PaperParm> paperList = parm.getPaperList();
        List<AnswerPaper> list = new ArrayList<>();
        for(int i=0;i<paperList.size();i++){
            AnswerPaper answerPaper = new AnswerPaper();
            BeanUtils.copyProperties(paperList.get(i),answerPaper);
            answerPaper.setOpenid(parm.getOpenid());
            list.add(answerPaper);
            //保存answer_paper_choice表
            //单选和多选需要单独保存选项
            if(paperList.get(i).getPaperType().equals("1")){
                AnswerPaperChoice choice = new AnswerPaperChoice();
                choice.setOpenid(parm.getOpenid());
                choice.setPaperId(paperList.get(i).getPaperId());
                choice.setChoiceId(Long.valueOf(paperList.get(i).getPaperValue()));
                answerPaperChoiceService.save(choice);
            }
            if(paperList.get(i).getPaperType().equals("2")){
                String regx = ",";
                String[] strings = paperList.get(i).getPaperValue().split(regx);
                List<AnswerPaperChoice> choiceList = new ArrayList<>();
                for(int j=0;j<strings.length;j++){
                    AnswerPaperChoice choice = new AnswerPaperChoice();
                    choice.setOpenid(parm.getOpenid());
                    choice.setPaperId(paperList.get(i).getPaperId());
                    choice.setChoiceId(Long.valueOf(strings[j]));
                    choiceList.add(choice);
                }
                answerPaperChoiceService.saveBatch(choiceList);
            }
        }
        answerPaperService.saveBatch(list);
    }
}
