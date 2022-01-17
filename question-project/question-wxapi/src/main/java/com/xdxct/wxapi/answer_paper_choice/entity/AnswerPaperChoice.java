package com.xdxct.wxapi.answer_paper_choice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("answer_paper_choice")
public class AnswerPaperChoice implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long answerChoiceId;
    private Long paperId;
    private Long choiceId;
    private String openid;
}
