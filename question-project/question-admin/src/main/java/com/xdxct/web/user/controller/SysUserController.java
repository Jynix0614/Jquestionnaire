package com.xdxct.web.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdxct.utils.ResultUtils;
import com.xdxct.utils.ResultVo;
import com.xdxct.web.user.entity.SysUser;
import com.xdxct.web.user.entity.UserParm;
import com.xdxct.web.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姜瑜欣
 * @description: 用户管理
 * @date 2022/1/13 14:51
 */
@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody SysUser sysUser){
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,sysUser.getUsername())
                .eq(SysUser::getPassword,sysUser.getPassword());
        SysUser one = sysUserService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名或密码错误！");
        }
        return ResultUtils.success("登陆成功！",one.getUserId());
    }
    /**
     * 获取用户信息
     */
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId){
        SysUser sysUser = sysUserService.getById(userId);
        return ResultUtils.success("查询成功！",sysUser.getUsername());
    }
    /**
     * 新增
     */
    @PostMapping
    public ResultVo add(@RequestBody SysUser sysUser){
        boolean b = sysUserService.save(sysUser);
        if (b){
            return ResultUtils.success("新增用户成功！");
        }
        return ResultUtils.error("新增用户失败！");
    }
    /**
     * 编辑
     */
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser){
        boolean b = sysUserService.updateById(sysUser);
        if (b){
            return ResultUtils.success("编辑用户成功！");
        }
        return ResultUtils.error("编辑用户失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId){
        boolean b = sysUserService.removeById(userId);
        if (b){
            return ResultUtils.success("删除用户成功！");
        }
        return ResultUtils.error("删除用户失败！");
    }
    /**
     * 查单个用户
     */
    @GetMapping("/get/{userId}")
    public ResultVo get(@PathVariable("userId") Long userId){
        SysUser sysUser = sysUserService.getById(userId);
        if (sysUser != null){
            return ResultUtils.success("查询成功！",sysUser);
        }
        return ResultUtils.error("查询失败！");
    }
    /**
     * 列表查询
     */
    @GetMapping("/list")
    public ResultVo getList(UserParm userParm){
        //构造分页对象
        IPage<SysUser> page = new Page<>();
        page.setCurrent(userParm.getCurrentPage());
        page.setSize(userParm.getPageSize());
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().like(SysUser::getUsername,userParm.getUsername());
        IPage<SysUser> list = sysUserService.page(page,query);
        return ResultUtils.success("查询成功！",list);
    }
}
