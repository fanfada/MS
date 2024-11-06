package com.example.managersystem.service.impl;

import com.example.managersystem.entity.SysOwner;
import com.example.managersystem.dao.SysOwnerDao;
import com.example.managersystem.service.SysOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * 房东信息表(SysOwner)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-06 16:18:25
 */
@Service("sysOwnerService")
public class SysOwnerServiceImpl implements SysOwnerService {

    @Autowired
    private SysOwnerDao sysOwnerDao;

    /**
     * 通过ID查询单条数据
     *
     * @param ownerId 主键
     * @return 实例对象
     */
    @Override
    public SysOwner queryById(Integer ownerId) {
        return this.sysOwnerDao.queryById(ownerId);
    }

    /**
     * 分页查询
     *
     * @param sysOwner    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<SysOwner> queryByPage(SysOwner sysOwner, PageRequest pageRequest) {
        long total = this.sysOwnerDao.count(sysOwner);
        return new PageImpl<>(this.sysOwnerDao.queryAllByLimit(sysOwner, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    @Override
    public SysOwner insert(SysOwner sysOwner) {
        this.sysOwnerDao.insert(sysOwner);
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
        this.sysOwnerDao.update(sysOwner);
        return this.queryById(sysOwner.getOwnerId());
    }

    /**
     * 通过主键删除数据
     *
     * @param ownerId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer ownerId) {
        return this.sysOwnerDao.deleteById(ownerId) > 0;
    }
}
