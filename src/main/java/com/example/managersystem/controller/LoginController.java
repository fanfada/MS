package com.example.managersystem.controller;

import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.common.ReturnMessage;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.domain.SysUser;
import com.example.managersystem.dto.LoginDto;
import com.example.managersystem.dto.SafeUser;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.service.impl.TokenServiceImpl;
import com.example.managersystem.util.ThreadLocalMapUtil;
import com.example.managersystem.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanfada@cmss.chinamobile.com
 * @date 2024/11/08 11:09
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录接口
     * 前期支持手机号登录
     * @param loginDto
     * @return
     */
    @PostMapping(value = "/auth")
    public ReturnMessage<LoginVo> login(@RequestBody LoginDto loginDto) {
        if (null == loginDto.getPhonenumber()) {
            throw new GlobalException("手机号为空");
        }
        if (null == loginDto.getPassword()) {
            throw new GlobalException("密码为空");
        }
        SysUser sysUser = this.sysUserService.queryByPhone(loginDto.getPhonenumber());
        if (!sysUser.getPassword().equals(loginDto.getPassword())) {
            throw new GlobalException("密码错误");
        }
        //密码正确，分配token并存入redis
        final String token = this.tokenService.createToken(sysUser.getUserId());
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(sysUser.getUserId());
        loginVo.setToken(token);
        loginVo.setMessage("登陆成功");
        return new ReturnMessage<>(ReturnState.OK, loginVo);
    }


    /**
     * 登出接口
     * 前期只支持手机号登出
     *
     * @param loginDto
     * @return
     */
    @PostMapping(value = "logout")
    public ReturnMessage<String> logout(@RequestBody LoginDto loginDto) {
        if (null == loginDto.getPhonenumber()) {
            throw new GlobalException("手机号为空");
        }
        SafeUser safeUser = (SafeUser) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        SysUser sysUser = this.sysUserService.queryByPhone(loginDto.getPhonenumber());
        if (!safeUser.getId().equals(sysUser.getUserId())) {
            throw new GlobalException("请退出自己的账号");
        }
        this.redisCache.remove(safeUser.getId());
        return new ReturnMessage<>(ReturnState.OK, "登出成功");
    }

}
