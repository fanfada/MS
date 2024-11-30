package com.example.managersystem.service.impl;

import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysRole;
import com.example.managersystem.domain.SysRoleCity;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.mapper.SysRoleCityMapper;
import com.example.managersystem.mapper.SysRoleMapper;
import com.example.managersystem.service.SysRoleService;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.example.managersystem.util.UuidUtil;
import com.example.managersystem.vo.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-15 16:34:34
 */
@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleCityMapper sysRoleCityMapper;

    @Resource
    private SysRoleCityServiceImpl sysRoleCityService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysRoleVo> queryAll(String roleId, String userId) {
        List<SysRole> sysRoleList = this.sysRoleMapper.queryAll(roleId, userId);
        for (SysRole sysRole : sysRoleList) {
            sysRole.setAuthorityCities(this.sysRoleCityService.queryByRoleId(sysRole.getRoleId()).stream()
                    .map(SysRoleCity::getZipcode)
                    .collect(Collectors.toList()));
        }
        return sysRoleList.stream()
                .map(obj -> SysRoleVo.builder()
                        .roleId(obj.getRoleId())
                        .roleName(obj.getRoleName())
                        .roleKey(obj.getRoleKey())
                        .dataScope(obj.getDataScope())
                        .authorityCities(obj.getAuthorityCities())
                        .remark(obj.getRemark())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRoleVo queryById(String roleId, String userId) {
        SysRole sysRole = this.sysRoleMapper.queryById(roleId, userId);
        SysRoleVo sysRoleVo = new SysRoleVo();
        BeanUtils.copyProperties(sysRole, sysRoleVo);
        return sysRoleVo;
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
        sysRole.setRoleId(UuidUtil.uuid());
        sysRole.setStatus(0);
        log.info("角色{}", JsonUtil.toString(sysRole));
        this.sysRoleMapper.insert(sysRole);
        List<String> authorityCities = sysRole.getAuthorityCities();
        log.info("授权的城市：{}", JsonUtil.toString(authorityCities));
        List<SysRoleCity> sysRoleCities = new ArrayList<>();
        for (String city : authorityCities) {
            SysRoleCity sysRoleCity = SysRoleCity.builder()
                    .roleId(sysRole.getRoleId())
                    .zipcode(city)
                    .build();
            sysRoleCities.add(sysRoleCity);
        }
        this.sysRoleCityService.insertBatch(sysRoleCities);
        return true;
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
        if (!sysRole.getCreateBy().equals(safeUserDto.getId())) {
            throw new GlobalException("请操作自己创建的角色");
        }
        sysRole.setUpdateBy(safeUserDto.getId());
        sysRole.setUpdateTime(new Date());
        this.sysRoleMapper.update(sysRole);
        List<String> authorityCities = sysRole.getAuthorityCities();
        log.info("授权的城市：{}", JsonUtil.toString(authorityCities));
        List<SysRoleCity> sysRoleCities = new ArrayList<>();
        for (String city : authorityCities) {
            SysRoleCity sysRoleCity = SysRoleCity.builder()
                    .roleId(sysRole.getRoleId())
                    .zipcode(city)
                    .build();
            sysRoleCities.add(sysRoleCity);
        }
        this.sysRoleCityService.deleteById(sysRole.getRoleId());
        this.sysRoleCityService.insertBatch(sysRoleCities);
        return true;
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String roleId) {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        if (!roleId.equals(safeUserDto.getId())) {
            throw new GlobalException("请操作自己创建的角色");
        }
        return this.sysRoleMapper.deleteById(roleId) > 0;
    }
}
