package com.example.managersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysRoleCity;
import com.example.managersystem.domain.SysUser;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.mapper.SysUserMapper;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.util.CommonUtil;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.example.managersystem.util.UuidUtil;
import com.example.managersystem.vo.SysRoleVo;
import com.example.managersystem.vo.SysUserVo;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-08 09:27:46
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleCityServiceImpl sysRoleCityService;

    @Resource
    private SysRoleServiceImpl sysRoleService;


    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysUserVo> queryAll() {
        List<SysUser> sysUserList = this.sysUserMapper.queryAll();
        log.info("所有用户信息：{}", JsonUtil.toString(sysUserList));
        return sysUserList.stream()
                .sorted(Comparator.comparing(SysUser::getCreateTime).reversed())
                .map(SysUser -> SysUserVo.builder()
                        .userId(SysUser.getUserId())
                        .userName(SysUser.getUserName())
                        .nickName(SysUser.getNickName())
                        .userType(SysUser.getUserType())
                        .remark(SysUser.getRemark())
                        .email(SysUser.getEmail())
                        .phonenumber(SysUser.getPhonenumber())
                        .sex(SysUser.getSex())
                        .remark(SysUser.getRemark())
                        .status(SysUser.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param phonenumber 手机号
     * @return 实例对象
     */
    public SysUser queryByPhone(String phonenumber) {
        SysUser sysUser = this.sysUserMapper.queryByPhone(phonenumber);
        if (null == sysUser) {
            throw new GlobalException(String.format("该用户%s不存在", phonenumber));
        }
        log.info("用户信息：{}", JsonUtil.toString(sysUser));
        return sysUser;
    }


    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(String userId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
//        SysUser sysUser = queryWrapper.getEntity();
        SysUser sysUser = this.sysUserMapper.queryById(userId);
        if (null == sysUser) {
            throw new GlobalException(String.format("该用户%s不存在", userId));
        }
        return sysUser;
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUserVo insert(SysUser sysUser) {
        SysUser sysUserRaw = this.sysUserMapper.queryByPhone(sysUser.getPhonenumber());
        if (null != sysUserRaw) {
            throw new GlobalException(String.format("该用户%s已存在", sysUser.getPhonenumber()));
        }
        sysUser.setUserId(UuidUtil.uuid());
        if (sysUser.getNickName() == null) {
            sysUser.setNickName(UuidUtil.generateRandomString().replace("user_", "nickname_"));
        }
        sysUser.setUserName(sysUser.getUserName() == null ? UuidUtil.generateRandomString() : sysUser.getUserName());
        sysUser.setUserType("01");
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setLoginDate(new Date());
        sysUser.setUpdateBy(sysUser.getUserId());
        sysUser.setStatus(0);
        sysUser.setDelFlag("0");
        sysUser.setSex("0");
        sysUser.setRemark("Hello");
        sysUser.setRoleId("admin");//注册用户先给一个admin角色，默认拥有所有权限，后期如果需要做权限控制，新加用户无任何城市查看权限
        if (StringUtil.isNullOrEmpty(sysUser.getPassword())) {
            throw new GlobalException("密码为空");
        }
        if (!CommonUtil.isValidEmail(sysUser.getEmail())) {
            throw new GlobalException("邮箱地址无效");
        }
        SysUser sysUserOld = this.sysUserMapper.queryByPhone(sysUser.getPhonenumber());
        if (null != sysUserOld) {
            throw new GlobalException(String.format("该用户%s已存在", sysUser.getPhonenumber()));
        }
        if (!CommonUtil.isValidPhoneNumber(sysUser.getPhonenumber())) {
            throw new GlobalException("手机号码无效");
        }
        log.info("待添加数据sysUser: {}", JsonUtil.toString(sysUser));
        this.sysUserMapper.insert(sysUser);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        return sysUserVo;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(SysUser sysUser) {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        try {
            sysUser.setUpdateTime(new Date());
            log.info("待修改数据sysUser: {}", JsonUtil.toString(sysUser));
            this.sysUserMapper.update(sysUser);
            SysRoleVo sysRole = this.sysRoleService.queryById(safeUserDto.getId(), "");
            if (!sysUser.getCities().isEmpty()) {
                this.sysRoleCityService.deleteById(sysRole.getRoleId());
                List<String> authorityCities = sysUser.getCities();
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
            }
            return true;
        } catch (Exception e) {
            log.info("编辑用户信息失败：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 通过主键删除数据:软删除
     *
     * @return 是否成功
     */
    public boolean deleteByIdSoft(String userId) {
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        SysUser sysUser = this.queryById(userId);
        if (null == sysUser) {
            log.info("userId = {} 不存在", userId);
            throw new GlobalException("该用户不存在");
        } else if (sysUser.getDelFlag().equals("1")) {
            throw new GlobalException("该用户已经删除");
        }
        try {
            sysUser.setUpdateTime(new Date());
            sysUser.setDelFlag("1");
            log.info("要删除的sysUser: {}", JsonUtil.toString(sysUser));
            this.sysUserMapper.update(sysUser);
            SysRoleVo sysRole = this.sysRoleService.queryById(safeUserDto.getId(), "");
            this.sysRoleCityService.deleteById(sysRole.getRoleId());
            return true;
        } catch (Exception e) {
            log.info("删除用户信息失败：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userId) {
        return this.sysUserMapper.deleteById(userId) > 0;
    }
}
