package com.example.managersystem.controller;

import com.example.managersystem.domain.SysRoom;
import com.example.managersystem.service.SysRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 房屋信息表(SysRoom)表控制层
 *
 * @author makejava
 * @since 2024-11-07 12:36:52
 */
@RestController
@RequestMapping("sysRoom")
public class SysRoomController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoomService sysRoomService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @GetMapping
    public ResponseEntity<List> queryAll() {
        return ResponseEntity.ok(this.sysRoomService.queryAll());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SysRoom> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.sysRoomService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoom 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<SysRoom> add(SysRoom sysRoom) {
        return ResponseEntity.ok(this.sysRoomService.insert(sysRoom));
    }

    /**
     * 编辑数据
     *
     * @param sysRoom 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<SysRoom> edit(SysRoom sysRoom) {
        return ResponseEntity.ok(this.sysRoomService.update(sysRoom));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.sysRoomService.deleteById(id));
    }

}
