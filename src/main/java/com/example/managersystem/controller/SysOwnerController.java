package com.example.managersystem.controller;

import com.example.managersystem.domain.SysOwner;
import com.example.managersystem.service.SysOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 房东信息表(SysOwner)表控制层
 *
 * @author makejava
 * @since 2024-11-07 12:36:54
 */
@RestController
@RequestMapping("sysOwner")
public class SysOwnerController {
    /**
     * 服务对象
     */
    @Resource
    private SysOwnerService sysOwnerService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @GetMapping
    public ResponseEntity<List> queryAll() {
        return ResponseEntity.ok(this.sysOwnerService.queryAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SysOwner> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.sysOwnerService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysOwner 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<SysOwner> add(SysOwner sysOwner) {
        return ResponseEntity.ok(this.sysOwnerService.insert(sysOwner));
    }

    /**
     * 编辑数据
     *
     * @param sysOwner 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<SysOwner> edit(SysOwner sysOwner) {
        return ResponseEntity.ok(this.sysOwnerService.update(sysOwner));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.sysOwnerService.deleteById(id));
    }

}
