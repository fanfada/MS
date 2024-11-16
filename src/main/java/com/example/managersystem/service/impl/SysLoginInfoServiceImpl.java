package com.example.managersystem.service.impl;

import com.example.managersystem.domain.SysLoginInfo;
import com.example.managersystem.mapper.SysLoginInfoMapper;
import com.example.managersystem.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author fanfada
 */
@Service
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper logininforMapper;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLoginInfo(SysLoginInfo logininfor) {
        logininforMapper.insertLoginInfo(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo logininfor) {
        return logininforMapper.selectLoginInfoList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        return logininforMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginInfo() {
        logininforMapper.cleanLoginInfo();
    }
}
