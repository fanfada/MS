package com.example.managersystem.controller;

import com.example.managersystem.domain.SysOwner;
import com.example.managersystem.hanbler.ReturnMessage;
import com.example.managersystem.hanbler.ReturnState;
import com.example.managersystem.service.SysOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 房东信息表(SysOwner)表控制层
 *
 * @author makejava
 * @since 2024-11-07 12:36:54
 */
@Slf4j
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
    public ReturnMessage<List> queryAll() {
        return new ReturnMessage<>(ReturnState.OK, this.sysOwnerService.queryAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ReturnMessage<SysOwner> queryById(@PathVariable("id") Integer id) {
        return new ReturnMessage<>(ReturnState.OK, this.sysOwnerService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysOwner 实体
     * @return 新增结果
     */
    @PostMapping
    public ReturnMessage<SysOwner> add(SysOwner sysOwner) {
        return new ReturnMessage<>(ReturnState.OK, this.sysOwnerService.insert(sysOwner));
    }

    /**
     * 编辑数据
     *
     * @param sysOwner 实体
     * @return 编辑结果
     */
    @PutMapping
    public ReturnMessage<SysOwner> edit(SysOwner sysOwner) {
        return new ReturnMessage<>(ReturnState.OK, this.sysOwnerService.update(sysOwner));
    }

    /**
     * 删除数据
     *
     * @param id id
     * @return 删除是否成功
     */
    @GetMapping("/delete/{id}")
    public ReturnMessage<Boolean> deleteById(@PathVariable(value = "id") Integer id) {
        return new ReturnMessage<>(ReturnState.OK, this.sysOwnerService.deleteByIdSoft(id));
    }

}
