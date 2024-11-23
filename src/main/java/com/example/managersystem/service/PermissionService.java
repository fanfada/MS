package com.example.managersystem.service;

import com.example.managersystem.cache.RedisCache;
import com.example.managersystem.common.GlobalConstants;
import com.example.managersystem.dto.SafeUserDto;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.security.PermissionContextHolder;
import com.example.managersystem.service.impl.TokenServiceImpl;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.util.StringUtils;
import com.example.managersystem.util.ThreadLocalMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * fanfada首创 自定义权限实现，ss取自SpringSecurity首字母
 *
 * @author fanfada
 */
@Service("ss")
@Slf4j
public class PermissionService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenServiceImpl tokenService;


    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        PermissionContextHolder.setContext(permission);
        SafeUserDto safeUserDto = (SafeUserDto) ThreadLocalMapUtil.get(GlobalConstants.ThreadLocalConstants.SAFE_SMP_USER);
        String authStr = this.redisCache.getCacheObject(GlobalConstants.AUTHORITY + safeUserDto.getId());
        List<String> authorityCities = Arrays.stream(authStr.split(",")).collect(Collectors.toList());
        return hasPermissions(authorityCities, permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission) {
        return hasPermi(permission) != true;
    }


    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(List<String> permissions, String permission) {
        long startTime = System.currentTimeMillis();
        final String msg = "进行未授权的操作，请联系管理员授权";
        log.info("permissions:{}, permission: {}", JsonUtil.toString(permissions), permission);
//        if (!permissions.contains("admin") && !permissions.contains(permission)) {
//            this.tokenService.generateErrorMsg(msg, "", "", startTime);
//            throw new GlobalException(msg);
//        }
        if (permissions.contains("admin")) {
            return true;
        } else if (permissions.contains(permission)) {
            return true;
        } else {
            for (String string : permissions) {
                String[] auth = string.split(":");
                if (auth[0].equals(permission.split(":")[0]) && auth[1].equals("all")) {
                    return true;
                }
            }
        }
        this.tokenService.generateErrorMsg(msg, "", "", startTime);
        throw new GlobalException(msg);
    }
}
