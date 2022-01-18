package com.xdxct.wxapi.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdxct.wxapi.question.entity.SysQuestion;
import com.xdxct.wxapi.question.service.SysQuestionService;
import com.xdxct.wxapi.question.mapper.SysQuestionMapper;
import com.xdxct.wxapi.sys_paper.entity.SysPaper;
import com.xdxct.wxapi.sys_paper.service.SysPaperService;
import com.xdxct.wxapi.sys_paper_choice.entity.SysPaperChoice;
import com.xdxct.wxapi.sys_paper_choice.service.SysPaperChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/14 17:38
 */
@Service
public class SysQuestionServiceImpl extends ServiceImpl<SysQuestionMapper, SysQuestion> implements SysQuestionService {
    @Autowired
    private SysPaperService sysPaperService;
    @Autowired
    private SysPaperChoiceService sysPaperChoiceService;

    @Override
    public IPage<SysQuestion> getList(IPage<SysQuestion> page) {
        return this.baseMapper.getList(page);
    }

    @Override
    public IPage<SysQuestion> getMyQuestionList(IPage<SysQuestion> page, String openid) {
        return this.baseMapper.getMyQuestionList(page,openid);
    }

    @Override
    public SysQuestion getMyPaperList(Long questionId, String openid) {
        //根据问卷id查询问卷
        SysQuestion sysQuestion = this.baseMapper.selectById(questionId);
        //根据问卷id查询试题列表
        //构造查询条件
        QueryWrapper<SysPaper> query = new QueryWrapper<>();
        query.lambda().eq(SysPaper::getQuestionId,questionId);
        //问卷的所有试题列表
        List<SysPaper> paperList = sysPaperService.list(query);
        //查询用户做的试题列表 根据openid查询
        List<SysPaper> myPaperList = sysPaperService.getMyPaperList(questionId, openid);
        if(paperList.size()>0){
            for (int i=0;i<paperList.size();i++){
                for (int j=0;j<myPaperList.size();j++){
                    //两个id相等，说明做了试题
                    if(paperList.get(i).getPaperId().equals(myPaperList.get(j).getPaperId())){
                        //设置用户的答案
                        paperList.get(i).setPaperValue(myPaperList.get(j).getPaperValue());
                        //判断是否是单选和多选
                        if(paperList.get(i).getPaperType().equals("1") || paperList.get(i).getPaperType().equals("2")){
                            //查询对应的选项
                            QueryWrapper<SysPaperChoice> queryWrapper = new QueryWrapper<>();
                            queryWrapper.lambda().eq(SysPaperChoice::getPaperId,paperList.get(i).getPaperId());
                            //试题选项
                            List<SysPaperChoice> choiceList = sysPaperChoiceService.list(queryWrapper);
                            for (int k=0;k<choiceList.size();k++){
                                String regex = ",";
                                //原来的答案
                                String[] strings = myPaperList.get(j).getPaperValue().split(regex);
                                for (int h=0;h<strings.length;h++){

                                    if(choiceList.get(k).getChoiceId().equals(Long.valueOf(strings[h]))){
                                        choiceList.get(k).setChecked(true);
                                    }
                                }
                            }
                            paperList.get(i).setPaperChoice(choiceList);
                        }
                    }
                }
            }
        }
        sysQuestion.setListPaper(paperList);
        return sysQuestion;
    }
}
