package com.xdxct.wxapi.answer_question.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 姜瑜欣
 * @description: 已作答试卷对象
 * @date 2022/1/1715:41
 */
@Data
public class PaperParm implements Serializable {
    private Long paperId;

    private String paperType;

    private String paperValue;

    private Long questionId;
}
