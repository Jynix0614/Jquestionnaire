package com.xdxct.wxapi.answer_paper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("answer_paper")
public class AnswerPaper implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long answerPaperId;

    private Long paperId;

    private String openid;

    private String paperType;

    private String paperValue;
}
