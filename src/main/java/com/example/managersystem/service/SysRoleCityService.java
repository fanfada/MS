package com.example.managersystem.service;
import com.example.managersystem.domain.SysRoleCity;

import java.util.List;

/**
 * 角色和城市关联表(SysRoleCity)表服务接口
 *
 * @author fanfada
 * @since 2024-11-15 16:34:35
 */
public interface SysRoleCityService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysRoleCity> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param roleId
     * @return 实例对象
     */
    SysRoleCity queryByRoleId(final String roleId);

    /**
     * 新增数据
     *
     * @param sysRoleCity 实例对象
     * @return 实例对象
     */
    Boolean insert(SysRoleCity sysRoleCity);

    /**
     * 修改数据
     *
     * @param sysRoleCity 实例对象
     * @return 实例对象
     */
    Boolean update(SysRoleCity sysRoleCity);

    /**
     * 通过主键删除数据
     *
     * @param roleId
     * @return 是否成功
     */
    boolean deleteById(final String roleId);

}
