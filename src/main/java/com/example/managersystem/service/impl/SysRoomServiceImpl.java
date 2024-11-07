package com.example.managersystem.service.impl;

import com.example.managersystem.domain.SysRoom;
import com.example.managersystem.mapper.SysRoomMapper;
import com.example.managersystem.service.SysRoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 房屋信息表(SysRoom)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 12:36:52
 */
@Service("sysRoomService")
public class SysRoomServiceImpl implements SysRoomService {
    @Resource
    private SysRoomMapper sysRoomMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<SysRoom> queryAll() {
        return this.sysRoomMapper.queryAll();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRoom queryById(Integer id) {
        return this.sysRoomMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoom insert(SysRoom sysRoom) {
        this.sysRoomMapper.insert(sysRoom);
        return sysRoom;
    }

    /**
     * 修改数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoom update(SysRoom sysRoom) {
        this.sysRoomMapper.update(sysRoom);
        return this.queryById(sysRoom.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysRoomMapper.deleteById(id) > 0;
    }
}
