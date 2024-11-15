package com.example.managersystem.service.impl;
import com.example.managersystem.domain.SysUserRole;
import com.example.managersystem.mapper.SysUserRoleMapper;
import com.example.managersystem.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 用户和角色关联表(SysUserRole)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:36
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysUserRole> queryAll() {
        return this.sysUserRoleMapper.queryAll();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUserRole queryById(String userId) {
        return this.sysUserRoleMapper.queryById(userId);
    }

    /**
     * 新增数据
     *
     * @param sysUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysUserRole insert(SysUserRole sysUserRole) {
        this.sysUserRoleMapper.insert(sysUserRole);
        return sysUserRole;
    }

    /**
     * 修改数据
     *
     * @param sysUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysUserRole update(SysUserRole sysUserRole) {
        this.sysUserRoleMapper.update(sysUserRole);
        return this.queryById(sysUserRole.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userId) {
        return this.sysUserRoleMapper.deleteById(userId) > 0;
    }
}
