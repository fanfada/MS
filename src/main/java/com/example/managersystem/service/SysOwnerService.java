package com.example.managersystem.service;

import com.example.managersystem.entity.SysOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 房东信息表(SysOwner)表服务接口
 *
 * @author fanfada
 * @since 2024-11-06 16:18:25
 */
public interface SysOwnerService {

    /**
     * 通过ID查询单条数据
     *
     * @param ownerId 主键
     * @return 实例对象
     */
    SysOwner queryById(Integer ownerId);

    /**
     * 分页查询
     *
     * @param sysOwner    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<SysOwner> queryByPage(SysOwner sysOwner, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    SysOwner insert(SysOwner sysOwner);

    /**
     * 修改数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    SysOwner update(SysOwner sysOwner);

    /**
     * 通过主键删除数据
     *
     * @param ownerId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer ownerId);

}
