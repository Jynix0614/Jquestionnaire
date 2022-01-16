package com.xdxct.wxapi.answer_question.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/1619:24
 */
@Data
@TableName("answer_question")
public class AnswerQuestion implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long answerId;

    private Long questionId;

    private String openid;

    private Date createTime;
}
