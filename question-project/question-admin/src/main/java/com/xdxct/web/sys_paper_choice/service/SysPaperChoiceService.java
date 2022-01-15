package com.xdxct.web.sys_paper_choice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdxct.web.sys_paper_choice.entity.SysPaperChoice;
import java.util.List;

public interface SysPaperChoiceService extends IService<SysPaperChoice> {
    List<SysPaperChoice> getListPaperChoice(Long questionId);
}
