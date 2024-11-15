package com.example.managersystem.service.impl;

import com.example.managersystem.domain.SysRoleCity;
import com.example.managersystem.mapper.SysRoleCityMapper;
import com.example.managersystem.service.SysRoleCityService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 角色和城市关联表(SysRoleCity)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:34
 */
@Service("sysRoleCityService")
public class SysRoleCityServiceImpl implements SysRoleCityService {
    @Resource
    private SysRoleCityMapper sysRoleCityMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysRoleCity> queryAll() {
        return this.sysRoleCityMapper.queryAll();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param roleId
     * @return 实例对象
     */
    @Override
    public SysRoleCity queryByRoleId(final String roleId) {
        return this.sysRoleCityMapper.queryByRoleId(roleId);
    }

    /**
     * 新增数据
     *
     * @param sysRoleCity 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(SysRoleCity sysRoleCity) {
        return this.sysRoleCityMapper.insert(sysRoleCity) > 0;
    }

    /**
     * 修改数据
     *
     * @param sysRoleCity 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(SysRoleCity sysRoleCity) {
        return this.sysRoleCityMapper.update(sysRoleCity) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId
     * @return 是否成功
     */
    @Override
    public boolean deleteById(final String roleId) {
        return this.sysRoleCityMapper.deleteById(roleId) > 0;
    }
}
