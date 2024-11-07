package com.example.managersystem.controller;

import com.example.managersystem.entity.SysRoom;
import com.example.managersystem.service.SysRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 房屋信息表(SysRoom)表控制层
 *
 * @author fanfada
 * @since 2024-11-06 16:18:28
 */
@Slf4j
@RestController
@RequestMapping("/sysRoom")
public class SysRoomController {
    /**
     * 服务对象
     */
    @Autowired
    private SysRoomService sysRoomService;

    /**
     * 分页查询
     *
     * @param sysRoom     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<SysRoom>> queryByPage(SysRoom sysRoom, PageRequest pageRequest) {
        return ResponseEntity.ok(this.sysRoomService.queryByPage(sysRoom, pageRequest));
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

