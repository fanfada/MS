package com.example.managersystem.service;

import com.example.managersystem.domain.SysRoom;
import com.example.managersystem.vo.SysRoomVo;

import java.util.List;

/**
 * 房屋信息表(SysRoom)表服务接口
 *
 * @author fanfada
 * @since 2024-11-07 12:36:52
 */
public interface SysRoomService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysRoomVo> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRoom queryById(Integer id);

    /**
     * 新增数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    Boolean insert(SysRoom sysRoom);

    /**
     * 修改数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    Boolean update(SysRoom sysRoom);


    /**
     * 软删除
     *
     * @param id
     * @return
     */
    boolean deleteByIdSoft(Integer id);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
