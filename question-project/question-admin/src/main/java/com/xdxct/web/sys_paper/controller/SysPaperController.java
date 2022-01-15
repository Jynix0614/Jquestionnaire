package com.xdxct.web.sys_paper.controller;

import com.xdxct.utils.ResultUtils;
import com.xdxct.utils.ResultVo;
import com.xdxct.web.sys_paper.entity.SysPaper;
import com.xdxct.web.sys_paper.service.SysPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试题模块控制器
 */
@RestController
@RequestMapping("/api/syspaper")
public class SysPaperController {
    @Autowired
    private SysPaperService sysPaperService;

    //保存试题
    @PostMapping("/savePaper")
    public ResultVo savePaper(@RequestBody List<SysPaper> paperList){
        sysPaperService.savePaper(paperList);
        return ResultUtils.success("设计试题成功!");
    }
    //编辑回显
    @GetMapping("/getPaperList")
    public ResultVo getPaperList(String questionId){
        List<SysPaper> list = sysPaperService.getList(questionId);
        return ResultUtils.success("查询成功",list);
    }
}
