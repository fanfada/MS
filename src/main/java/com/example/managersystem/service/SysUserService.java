package com.example.managersystem.service;

import com.example.managersystem.domain.SysUser;
import com.example.managersystem.vo.SysUserVo;

import java.util.List;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author fanfada
 * @since 2024-11-08 09:27:46
 */
public interface SysUserService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysUserVo> queryAll();


    /**
     * 通过ID查询单条数据
     *
     * @param phonenumber 手机号
     * @return 实例对象
     */
    SysUser queryByPhone(String phonenumber);

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(String userId);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    Boolean insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    Boolean update(SysUser sysUser);

    /**
     * 软删除
     *
     * @param userId
     * @return
     */
    boolean deleteByIdSoft(String userId);


    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(String userId);

}
