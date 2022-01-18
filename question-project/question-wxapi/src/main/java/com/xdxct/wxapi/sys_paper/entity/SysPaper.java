package com.xdxct.wxapi.sys_paper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xdxct.wxapi.sys_paper_choice.entity.SysPaperChoice;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("sys_paper")
public class SysPaper implements Serializable {
    //试题id
    @TableId(type = IdType.AUTO)
    private Long paperId;
    //问卷id
    private Long questionId;
    //标题
    private String paperTitle;
    //试题类型
    private String paperType;
    private Long paperOrder;

    //单选、多选题对应的选项,不属于 试题表，需要排除
    @TableField(exist =  false)
    private List<SysPaperChoice> paperChoice;
    @TableField(exist = false)
    private String paperValue;
}
