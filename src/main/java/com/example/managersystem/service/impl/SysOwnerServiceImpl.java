package com.example.managersystem.service.impl;

import com.example.managersystem.domain.SysOwner;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.mapper.SysOwnerMapper;
import com.example.managersystem.service.SysOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 房东信息表(SysOwner)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 12:36:54
 */
@Slf4j
@Service("sysOwnerService")
public class SysOwnerServiceImpl implements SysOwnerService {
    @Resource
    private SysOwnerMapper sysOwnerMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysOwner> queryAll() {
        return this.sysOwnerMapper.queryAll();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysOwner queryById(Integer id) {
        return this.sysOwnerMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    @Override
    public SysOwner insert(SysOwner sysOwner) {
        try {
            sysOwner.setCreateTime(new Date());
            sysOwner.setUpdateTime(new Date());
            this.sysOwnerMapper.insert(sysOwner);
        } catch (Exception e) {
            log.info("添加房东信息失败：{}", e.getMessage());
        }
        return sysOwner;
    }

    /**
     * 修改数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    @Override
    public SysOwner update(SysOwner sysOwner) {
        sysOwner.setUpdateTime(new Date());
        this.sysOwnerMapper.update(sysOwner);
        return this.queryById(sysOwner.getId());
    }

    /**
     * 通过主键删除数据:软删除
     *
     * @return 是否成功
     */
    public boolean deleteByIdSoft(Integer id) {
        SysOwner sysOwner = this.queryById(id);
        if (null == sysOwner) {
            log.info("id = {} 不存在", id);
            throw new GlobalException("该实例不存在");
        } else if (sysOwner.getStatus().equals(1)) {
            throw new GlobalException("该实例已经删除");
        }
        sysOwner.setUpdateTime(new Date());
        sysOwner.setStatus(1);
        this.sysOwnerMapper.update(sysOwner);
        return true;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysOwnerMapper.deleteById(id) > 0;
    }
}
