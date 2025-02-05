package com.example.managersystem.service;

import com.example.managersystem.domain.SysOwner;
import com.example.managersystem.vo.SysOwnerVo;

import java.util.List;

/**
 * 房东信息表(SysOwner)表服务接口
 *
 * @author fanfada
 * @since 2024-11-07 12:36:54
 */
public interface SysOwnerService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysOwnerVo> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysOwner queryById(Integer id);

    /**
     * 新增数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    Boolean insert(SysOwner sysOwner);

    /**
     * 修改数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    Boolean update(SysOwner sysOwner);


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
