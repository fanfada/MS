package com.example.managersystem.service.impl;

import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysRole;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.mapper.SysRoleMapper;
import com.example.managersystem.service.SysRoleService;
import com.example.managersystem.util.ThreadLocalMapUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:34
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysRole> queryAll() {
        return this.sysRoleMapper.queryAll();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRole queryById(String roleId) {
        return this.sysRoleMapper.queryById(roleId);
    }

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(SysRole sysRole) {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        sysRole.setCreateTime(new Date());
        sysRole.setCreateBy(safeUserDto.getId());
        sysRole.setUpdateBy(safeUserDto.getId());
        sysRole.setUpdateTime(new Date());
        return this.sysRoleMapper.insert(sysRole) > 0;
    }

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(SysRole sysRole) {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        sysRole.setUpdateBy(safeUserDto.getId());
        sysRole.setUpdateTime(new Date());
        return this.sysRoleMapper.update(sysRole) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String roleId) {
        return this.sysRoleMapper.deleteById(roleId) > 0;
    }
}
