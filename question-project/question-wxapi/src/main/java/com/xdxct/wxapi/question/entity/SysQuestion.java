package com.xdxct.wxapi.question.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 姜瑜欣
 * @description: 问题实体
 * @date 2022/1/1417:21
 */
@Data
@TableName("sys_question")
public class SysQuestion implements Serializable {
    //主键 自动递增
    @TableId(type = IdType.AUTO)
    private Long questionId;
    //问卷标题
    private String questionTitle;
    //问卷描述
    private String questionDesc;
    //图片路径
    private String questionImg;
    //问卷状态 0：关闭 1：调查中
    private String questionStatus;
    //序号
    private Long questionOrder;
    //参与人数
    @TableField(exist = false)
    private Long joinCount;
    //是否答卷
    @TableField(exist = false)
    private String status;
    //问卷对应的试题列表,不属于问卷表，需要排除
//    @TableField(exist = false)
//    private List<SysPaper> listPaper;
}
