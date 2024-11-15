package com.example.managersystem.service;
import com.example.managersystem.domain.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表服务接口
 *
 * @author fanfada
 * @since 2024-11-15 16:34:36
 */
public interface SysUserRoleService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysUserRole> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUserRole queryById(String userId);

    /**
     * 新增数据
     *
     * @param sysUserRole 实例对象
     * @return 实例对象
     */
    SysUserRole insert(SysUserRole sysUserRole);

    /**
     * 修改数据
     *
     * @param sysUserRole 实例对象
     * @return 实例对象
     */
    SysUserRole update(SysUserRole sysUserRole);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(String userId);

}
