package com.xdxct.wxapi.sys_paper_choice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("sys_paper_choice")
public class SysPaperChoice implements Serializable {
    //选项id
    @TableId(type = IdType.AUTO)
    private Long choiceId;
    //试题id
    private Long paperId;
    //选项的名称
    private String choiceText;
    //序号
    private Long choiceOrder;
    @TableField(exist = false)
    private Long questionId;
    @TableField(exist = false)
    private Long total;
}
