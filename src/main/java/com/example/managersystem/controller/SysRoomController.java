package com.example.managersystem.controller;

import com.example.managersystem.common.PageResultBody;
import com.example.managersystem.domain.SysRoom;
import com.example.managersystem.common.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.service.SysRoomService;
import com.example.managersystem.vo.SysRoomVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * 房屋信息表(SysRoom)表控制层
 *
 * @author fanfada
 * @since 2024-11-07 12:36:52
 */
@Slf4j
@RestController
@RequestMapping("/sysRoom")
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
    public ReturnMessage<PageResultBody<SysRoomVo>> queryAll() {
        PageResultBody<SysRoomVo> pageResultBody = new PageResultBody<>();
        List<SysRoomVo> sysRoomList = this.sysRoomService.queryAll();
        pageResultBody.setContent(sysRoomList);
        pageResultBody.setTotal(sysRoomList.size());
        return new ReturnMessage<>(ReturnState.OK, pageResultBody);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ReturnMessage<SysRoom> queryById(@PathVariable("id") Integer id) {
        return new ReturnMessage<>(ReturnState.OK, this.sysRoomService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoom 实体
     * @return 新增结果
     */
    @PostMapping
    public ReturnMessage<Boolean> add(@RequestBody SysRoom sysRoom) {
        return new ReturnMessage<>(ReturnState.OK, this.sysRoomService.insert(sysRoom));
    }

    /**
     * 编辑数据：软删除
     *
     * @param sysRoom 实体
     * @return 编辑结果
     */
    @PutMapping
    public ReturnMessage<Boolean> edit(@RequestBody SysRoom sysRoom) {
        return new ReturnMessage<>(ReturnState.OK, this.sysRoomService.update(sysRoom));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete/{id}")
    public ReturnMessage<Boolean> deleteById(@PathVariable(value = "id") Integer id) {
        return new ReturnMessage<>(ReturnState.OK, this.sysRoomService.deleteByIdSoft(id));
    }

}
