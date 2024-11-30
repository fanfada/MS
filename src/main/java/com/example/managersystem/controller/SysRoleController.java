package com.example.managersystem.controller;

import com.example.managersystem.annotation.Log;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysRole;
import com.example.managersystem.domain.SysUser;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.enums.BusinessType;
import com.example.managersystem.service.SysRoleService;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.example.managersystem.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 角色信息表(SysRole)表控制层
 *
 * @author fanfada
 * @since 2024-11-15 20:09:25
 */
@RestController
@RequestMapping("/api/sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @GetMapping
    @Log(title = "查询角色信息", businessType = BusinessType.QUERY)
    public ResponseEntity<List<SysRoleVo>> queryAll() {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        SysUser sysUser = this.sysUserService.queryById(safeUserDto.getId());
        return ResponseEntity.ok(this.sysRoleService.queryAll(sysUser.getRoleId(), safeUserDto.getId()));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @Log(title = "查询角色信息", businessType = BusinessType.QUERY)
    public ResponseEntity<SysRoleVo> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.sysRoleService.queryById(id, ""));
    }

    /**
     * 新增数据
     *
     * @param sysRole 实体
     * @return 新增结果
     */
    @PostMapping
    @Log(title = "新增角色信息", businessType = BusinessType.INSERT)
    public ResponseEntity<Boolean> add(@RequestBody SysRole sysRole) {
        return ResponseEntity.ok(this.sysRoleService.insert(sysRole));
    }

    /**
     * 编辑数据
     *
     * @param sysRole 实体
     * @return 编辑结果
     */
    @PutMapping
    @Log(title = "编辑角色信息", businessType = BusinessType.UPDATE)
    public ResponseEntity<Boolean> edit(@RequestBody SysRole sysRole) {
        return ResponseEntity.ok(this.sysRoleService.update(sysRole));
    }

    /**
     * 删除数据
     *
     * @param roleId 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    @Log(title = "彻底删除角色信息", businessType = BusinessType.DELETE)
    public ResponseEntity<Boolean> deleteById(@RequestParam(value = "roleId") String roleId) {
        return ResponseEntity.ok(this.sysRoleService.deleteById(roleId));
    }

}
