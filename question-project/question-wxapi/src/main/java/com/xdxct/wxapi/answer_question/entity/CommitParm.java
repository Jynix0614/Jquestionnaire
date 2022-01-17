package com.xdxct.wxapi.answer_question.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 姜瑜欣
 * @description: 点击提交表单对象,存入answer_paper表
 * @date 2022/1/1715:40
 */
@Data
public class CommitParm implements Serializable {

    private String openid;

    private Long questionId;

    private List<PaperParm> paperList = new ArrayList<>();



}
