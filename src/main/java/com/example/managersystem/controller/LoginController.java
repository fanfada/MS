package com.example.managersystem.controller;

import com.example.managersystem.annotation.Log;
import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.domain.SysRole;
import com.example.managersystem.domain.SysRoleCity;
import com.example.managersystem.dto.*;
import com.example.managersystem.common.ReturnState;
import com.example.managersystem.domain.SysUser;
import com.example.managersystem.enums.BusinessType;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.manager.AsyncFactory;
import com.example.managersystem.manager.AsyncManager;
import com.example.managersystem.security.AuthenticationContextHolder;
import com.example.managersystem.service.SysUserService;
import com.example.managersystem.service.impl.SysRoleCityServiceImpl;
import com.example.managersystem.service.impl.SysRoleServiceImpl;
import com.example.managersystem.service.impl.TokenServiceImpl;
import com.example.managersystem.util.*;
import com.example.managersystem.vo.LoginVo;
import com.example.managersystem.vo.SysUserVo;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.helpers.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.context.Theme;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author fanfada
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

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Resource
    private SysRoleCityServiceImpl sysRoleCityService;
    @Resource
    private SysRoleServiceImpl sysRoleService;

    @Resource
    private AuthenticationManager authenticationManager;


    @Value("${server.captchaEnabled}")
    private boolean captchaEnabled;

    @Value("${server.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    @Log(title = "生成验证码", businessType = BusinessType.OTHER)
    public AjaxResult getCaptchaCode(HttpServletResponse response) {
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("captchaEnabled", this.captchaEnabled);
        log.info("验证码是否开启：{}", this.captchaEnabled);
        if (!this.captchaEnabled) {
            return ajaxResult;
        }
        // 保存验证码信息
        String uuid = UuidUtil.uuid();
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid;
        String capStr, code = null;
        BufferedImage image = null;
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = this.captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = this.captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = this.captchaProducer.createText();
            image = this.captchaProducer.createImage(capStr);
        }
        this.redisCache.setCacheObject(verifyKey, code, 2, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
        ajaxResult.put("uuid", uuid);
        ajaxResult.put("img", Base64.encode(os.toByteArray()));
        return ajaxResult;
    }

    /**
     * 用户注册
     *
     * @param sysUser 实体
     * @return 新增结果
     */
    @PostMapping(value = "/registry")
    @Log(title = "用户注册", businessType = BusinessType.INSERT)
    public ReturnMessage<SysUserVo> add(@RequestBody SysUser sysUser) {
        return new ReturnMessage<>(ReturnState.OK, this.sysUserService.insert(sysUser));
    }

    /**
     * 登录接口
     * 前期支持手机号登录
     *
     * @param loginDto
     * @return
     */
    @PostMapping(value = "/login")
    public ReturnMessage<LoginVo> login(@RequestBody LoginDto loginDto) {
//        this.validateCaptcha(loginDto.getCode(), loginDto.getUuid());
        SysUser sysUser = this.sysUserService.queryByPhone(loginDto.getPhonenumber());
        if (sysUser == null) {
            throw new GlobalException(String.format("不存在该用户%s", loginDto.getPhonenumber()));
        }
        SysRole sysRole = this.sysRoleService.queryById(sysUser.getRoleId());
        // 登录前置校验
        loginPreCheck(loginDto.getPhonenumber(), loginDto.getPassword(), sysUser, sysRole);
        // 用户验证
        Authentication authentication = null;
        final String token;
        final String auth = GlobalConstants.AUTHORITY + sysUser.getUserId();
        log.info("auth: {} ", auth);
        String sysRoleCities = "";
        try {
            if (!sysRole.getRoleId().equals("admin")) {
                 sysRoleCities = this.sysRoleCityService.queryByRoleId(sysRole.getRoleId()).stream().map(SysRoleCity::getZipcode).collect(Collectors.joining(","));
                this.redisCache.setEx(auth, sysRoleCities, 1800L);
            } else {
                this.redisCache.setEx(auth, "admin", 1800L);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPhonenumber(), loginDto.getPassword());
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
            //密码正确，分配token并存入redis
        } catch (GlobalException e) {
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(sysUser.getUserId(), GlobalConstants.LOGIN_FAIL, e.getMessage()));
            throw new GlobalException(e.getMessage());
        } catch (Exception e) {
            this.redisCache.deleteObject(auth);
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(sysUser.getUserId(), GlobalConstants.LOGIN_FAIL, e.getMessage()));
                throw new GlobalException(e.getMessage());
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(sysUser.getUserId(), GlobalConstants.LOGIN_FAIL, e.getMessage()));
                throw new GlobalException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        token = this.tokenService.createToken(loginUser);
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(sysUser.getUserId());
        loginVo.setToken(token);
        loginVo.setAuthorities(Arrays.asList(sysRoleCities.split(",")));
        loginVo.setMessage("登录成功");
        AsyncManager.me().execute(AsyncFactory.recordLoginInfo(loginUser.getUserId(), GlobalConstants.LOGIN_SUCCESS, "登录成功"));
        return new ReturnMessage<>(ReturnState.OK, loginVo);
    }

    /**
     * 登录前置校验
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password, SysUser sysUser, SysRole sysRole) {
        log.info("开始进行前置校验");
        //重复登录校验
        if (this.redisCache.exists(sysUser.getUserId())) {
            String token = this.redisCache.getCacheObject(sysUser.getUserId());
            throw new GlobalException(String.format("请勿重复登录token: %s, userid: %s", token, sysUser.getUserId()));
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, GlobalConstants.LOGIN_FAIL, "用户名或密码为空"));
            throw new GlobalException("用户名或密码为空");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < GlobalConstants.PASSWORD_MIN_LENGTH || password.length() > GlobalConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, GlobalConstants.LOGIN_FAIL, "密码长度错误"));
            throw new GlobalException("密码长度错误");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < GlobalConstants.USERNAME_MIN_LENGTH || username.length() > GlobalConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, GlobalConstants.LOGIN_FAIL, "用户名长度错误"));
            throw new GlobalException("用户名长度错误");
        }
        if (sysRole == null) {
            throw new GlobalException(String.format("用户%s未分配角色", sysUser.getPhonenumber()));
        }
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) {
        if (this.captchaEnabled) {
            String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = this.redisCache.getCacheObject(verifyKey);
            if (captcha == null) {
                throw new GlobalException("请输入验证码");
            }
            this.redisCache.deleteObject(verifyKey);
            if (!code.equalsIgnoreCase(captcha)) {
                throw new GlobalException("验证码错误");
            }
        }
    }


//    /**
//     * 登出接口
//     * 前期只支持手机号登出
//     *
//     * @param loginDto
//     * @return
//     */
//    @PostMapping(value = "logout")
//    @Log(title = "用户登出", businessType = BusinessType.OTHER)
//    public ReturnMessage<String> logout(@RequestBody LoginDto loginDto) {
//        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
//        SysUser sysUser = this.sysUserService.queryByPhone(loginDto.getPhonenumber());
//        try {
//            log.info("开始登出phonenumber: {}", loginDto.getPhonenumber());
//            if (null == loginDto.getPhonenumber()) {
//                throw new GlobalException("手机号为空");
//            }
//            if (!safeUserDto.getId().equals(sysUser.getUserId())) {
//                return new ReturnMessage<>(ReturnState.OK, "请勿登出他人账号");
//            }
//            if (!this.redisCache.exists(safeUserDto.getId())) {
//                return new ReturnMessage<>(ReturnState.OK, "您已登出，请勿重复操作");
//            }
//            this.redisCache.remove(safeUserDto.getId());
//            this.redisCache.remove(GlobalConstants.AUTHORITY);
//            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(sysUser.getUserName(), GlobalConstants.LOGOUT_SUCCESS, "登出成功"));
//            return new ReturnMessage<>(ReturnState.OK, "登出成功");
//        } catch (Exception e) {
//            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(sysUser.getUserName(), GlobalConstants.LOGOUT_FAIL, e.getMessage()));
//            throw new GlobalException(e.getMessage());
//        }
//    }
}
