package com.example.managersystem.service.impl;

import com.example.managersystem.entity.SysRoom;
import com.example.managersystem.dao.SysRoomDao;
import com.example.managersystem.service.SysRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * 房屋信息表(SysRoom)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-06 16:18:29
 */
@Service("sysRoomService")
public class SysRoomServiceImpl implements SysRoomService {

    @Autowired
    private SysRoomDao sysRoomDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roomId 主键
     * @return 实例对象
     */
    @Override
    public SysRoom queryById(Integer roomId) {
        return this.sysRoomDao.queryById(roomId);
    }

    /**
     * 分页查询
     *
     * @param sysRoom     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<SysRoom> queryByPage(SysRoom sysRoom, PageRequest pageRequest) {
        long total = this.sysRoomDao.count(sysRoom);
        return new PageImpl<>(this.sysRoomDao.queryAllByLimit(sysRoom, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoom insert(SysRoom sysRoom) {
        this.sysRoomDao.insert(sysRoom);
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
        this.sysRoomDao.update(sysRoom);
        return this.queryById(sysRoom.getRoomId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roomId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer roomId) {
        return this.sysRoomDao.deleteById(roomId) > 0;
    }
}
