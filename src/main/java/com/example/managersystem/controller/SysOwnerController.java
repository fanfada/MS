package com.example.managersystem.controller;

import com.example.managersystem.entity.SysOwner;
import com.example.managersystem.service.SysOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 房东信息表(SysOwner)表控制层
 *
 * @author fanfada
 * @since 2024-11-06 16:18:14
 */
@Slf4j
@RestController
@RequestMapping("sysOwner")
public class SysOwnerController {
    /**
     * 服务对象
     */
    @Autowired
    private SysOwnerService sysOwnerService;

    /**
     * 分页查询
     *
     * @param sysOwner    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<SysOwner>> queryByPage(SysOwner sysOwner, PageRequest pageRequest) {
        return ResponseEntity.ok(this.sysOwnerService.queryByPage(sysOwner, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SysOwner> queryById(@PathVariable("id") Integer id) {
        log.info("/sysOwner/id");
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

