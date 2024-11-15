package com.example.managersystem.service;
import com.example.managersystem.domain.SysRole;

import java.util.List;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author fanfada
 * @since 2024-11-15 16:34:34
 */
public interface SysRoleService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysRole> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRole queryById(String roleId);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    Boolean insert(SysRole sysRole);

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    Boolean update(SysRole sysRole);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(String roleId);

}
