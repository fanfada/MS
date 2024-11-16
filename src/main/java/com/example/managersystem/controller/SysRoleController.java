package com.example.managersystem.controller;
import com.example.managersystem.domain.SysRole;
import com.example.managersystem.service.SysRoleService;
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

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @GetMapping
    public ResponseEntity<List> queryAll() {
        return ResponseEntity.ok(this.sysRoleService.queryAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SysRole> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.sysRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRole 实体
     * @return 新增结果
     */
    @PostMapping
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
    public ResponseEntity<Boolean> deleteById(@RequestParam(value = "roleId") String roleId) {
        return ResponseEntity.ok(this.sysRoleService.deleteById(roleId));
    }

}
