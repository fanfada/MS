package com.example.managersystem.service;

import com.example.managersystem.entity.SysRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 房屋信息表(SysRoom)表服务接口
 *
 * @author fanfada
 * @since 2024-11-06 16:18:29
 */
public interface SysRoomService {

    /**
     * 通过ID查询单条数据
     *
     * @param roomId 主键
     * @return 实例对象
     */
    SysRoom queryById(Integer roomId);

    /**
     * 分页查询
     *
     * @param sysRoom     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<SysRoom> queryByPage(SysRoom sysRoom, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    SysRoom insert(SysRoom sysRoom);

    /**
     * 修改数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    SysRoom update(SysRoom sysRoom);

    /**
     * 通过主键删除数据
     *
     * @param roomId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer roomId);

}
