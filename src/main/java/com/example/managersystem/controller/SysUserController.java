package com.example.managersystem.controller;

import com.example.managersystem.annotation.Log;
import com.example.managersystem.dto.PageResultBody;
import com.example.managersystem.dto.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.domain.SysUser;
import com.example.managersystem.enums.BusinessType;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author fanfada
 * @since 2024-11-08 09:27:45
 */
@Slf4j
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
//    @LoginStatusVerify
    @GetMapping
    @PreAuthorize("@ss.hasPermi('admin')")
    @Log(title = "查询用户信息", businessType = BusinessType.QUERY)
    public ReturnMessage<PageResultBody<SysUserVo>> queryAll() {
        PageResultBody<SysUserVo> pageResultBody = new PageResultBody<>();
        List<SysUserVo> sysUserList = this.sysUserService.queryAll();
        pageResultBody.setContent(sysUserList);
        pageResultBody.setTotal(sysUserList.size());
        return new ReturnMessage<>(ReturnState.OK, pageResultBody);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @GetMapping("{userId}")
    @PreAuthorize("@ss.hasPermi('admin')")
    @Log(title = "查询用户信息", businessType = BusinessType.QUERY)
    public ReturnMessage<SysUserVo> queryById(@PathVariable("userId") String userId) {
        SysUser sysUser = this.sysUserService.queryById(userId);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        log.info("查询的用户信息sysUserVo：{}", JsonUtil.toString(sysUserVo));
        return new ReturnMessage<>(ReturnState.OK, sysUserVo);
    }

    /**
     * 编辑数据
     *
     * @param sysUser 实体
     * @return 编辑结果
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('admin')")
    @Log(title = "编辑用户信息", businessType = BusinessType.UPDATE)
    public ReturnMessage<Boolean> edit(@RequestBody SysUser sysUser) {
        return new ReturnMessage<>(ReturnState.OK, this.sysUserService.update(sysUser));
    }

    /**
     * 删除数据：软删除
     *
     * @param userId 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete/{userId}")
    @PreAuthorize("@ss.hasPermi('admin')")
    @Log(title = "删除用户信息", businessType = BusinessType.DELETE)
    public ReturnMessage<Boolean> deleteByIdSoft(@PathVariable(value = "userId") String userId) {
        return new ReturnMessage<>(ReturnState.OK, this.sysUserService.deleteByIdSoft(userId));
    }


    /**
     * 删除数据
     *
     * @param userId 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    @PreAuthorize("@ss.hasPermi('admin')")
    @Log(title = "彻底删除用户信息", businessType = BusinessType.DELETE)
    public ResponseEntity<Boolean> deleteById(String userId) {
        return ResponseEntity.ok(this.sysUserService.deleteById(userId));
    }

}
