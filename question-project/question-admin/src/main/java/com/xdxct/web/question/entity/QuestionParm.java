package com.xdxct.web.question.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 姜瑜欣
 * @description: 问卷列表
 * @date 2022/1/1417:58
 */
@Data
public class QuestionParm implements Serializable {
    //当前页
    private Long currentPage;
    //页容量
    private Long pageSize;

    //问卷标题
    private String questionTitle;
}
