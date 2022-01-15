package com.xdxct.web.sys_paper_choice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xdxct.web.sys_paper_choice.entity.SysPaperChoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPaperChoiceMapper extends BaseMapper<SysPaperChoice> {
    List<SysPaperChoice> getListPaperChoice(@Param("questionId") Long questionId);
}
