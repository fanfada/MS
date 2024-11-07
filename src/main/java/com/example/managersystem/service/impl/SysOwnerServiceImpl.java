package com.example.managersystem.service.impl;

import com.example.managersystem.domain.SysOwner;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.mapper.SysOwnerMapper;
import com.example.managersystem.service.SysOwnerService;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.vo.SysOwnerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 房东信息表(SysOwner)表服务实现类
 *
 * @author fanfada
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
    public List<SysOwnerVo> queryAll() {
        List<SysOwner> sysOwnerList = this.sysOwnerMapper.queryAll();
        log.info("所有房东信息：{}", JsonUtil.toString(sysOwnerList));
        return sysOwnerList.stream()
                .map(SysOwner -> SysOwnerVo.builder()
                        .id(SysOwner.getId())
                        .phone(SysOwner.getPhone())
                        .ownerType(SysOwner.getOwnerType())
                        .color(SysOwner.getColor())
                        .remark(SysOwner.getRemark())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysOwner queryById(Integer id) {
        SysOwner sysOwner = this.sysOwnerMapper.queryById(id);
        log.info("查询出的房东信息：{}", JsonUtil.toString(sysOwner));
        return sysOwner;
    }

    /**
     * 新增数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(SysOwner sysOwner) {
        try {
            sysOwner.setCreateTime(new Date());
            sysOwner.setUpdateTime(new Date());
            sysOwner.setStatus(0);
            log.info("待添加数据sysOwner: {}", JsonUtil.toString(sysOwner));
            this.sysOwnerMapper.insert(sysOwner);
            return true;
        } catch (Exception e) {
            log.info("添加房东信息失败：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 修改数据
     *
     * @param sysOwner 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(SysOwner sysOwner) {
        try {
            sysOwner.setUpdateTime(new Date());
            log.info("待修改数据sysOwner: {}", JsonUtil.toString(sysOwner));
            this.sysOwnerMapper.update(sysOwner);
            return true;
        } catch (Exception e) {
            log.info("编辑房东信息失败：{}", e.getMessage());
        }
        return false;
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
        try {
            log.info("要删除的sysOwner: {}", JsonUtil.toString(sysOwner));
            sysOwner.setUpdateTime(new Date());
            sysOwner.setStatus(1);
            this.sysOwnerMapper.update(sysOwner);
            return true;
        } catch (Exception e) {
            log.info("删除失败");
            return false;
        }
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
