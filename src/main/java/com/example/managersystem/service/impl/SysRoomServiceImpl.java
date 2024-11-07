package com.example.managersystem.service.impl;

import com.example.managersystem.domain.SysRoom;
import com.example.managersystem.excepion.GlobalException;
import com.example.managersystem.mapper.SysRoomMapper;
import com.example.managersystem.service.SysRoomService;
import com.example.managersystem.util.JsonUtil;
import com.example.managersystem.vo.SysRoomVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 房屋信息表(SysRoom)表服务实现类
 *
 * @author fanfada
 * @since 2024-11-07 12:36:52
 */
@Slf4j
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
    public List<SysRoomVo> queryAll() {
        List<SysRoom> sysRoomList = this.sysRoomMapper.queryAll();
        log.info("所有房屋信息：{}", JsonUtil.toString(sysRoomList));
        return sysRoomList.stream()
                .map(SysRoom -> SysRoomVo.builder()
                        .id(SysRoom.getId())
                        .color(SysRoom.getColor())
                        .roomType(SysRoom.getRoomType())
                        .province(SysRoom.getProvince())
                        .city(SysRoom.getCity())
                        .address(SysRoom.getAddress())
                        .remark(SysRoom.getRemark())
                        .ownerId(SysRoom.getOwnerId())
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
    public SysRoom queryById(Integer id) {
        SysRoom sysRoom = this.sysRoomMapper.queryById(id);
        log.info("查询出的房屋信息：{}", JsonUtil.toString(sysRoom));
        return sysRoom;
    }

    /**
     * 新增数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(SysRoom sysRoom) {
        try {
            sysRoom.setCreateTime(new Date());
            sysRoom.setUpdateTime(new Date());
            sysRoom.setStatus(0);
            log.info("待添加数据sysRoom: {}", JsonUtil.toString(sysRoom));
            this.sysRoomMapper.insert(sysRoom);
            return true;
        } catch (Exception e) {
            log.info("添加房屋信息失败：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 修改数据
     *
     * @param sysRoom 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(SysRoom sysRoom) {
        try {
            sysRoom.setUpdateTime(new Date());
            log.info("待修改数据sysRoom: {}", JsonUtil.toString(sysRoom));
            this.sysRoomMapper.update(sysRoom);
            return true;
        } catch (Exception e) {
            log.info("编辑房屋信息失败：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 通过主键删除数据:软删除
     *
     * @return 是否成功
     */
    public boolean deleteByIdSoft(Integer id) {
        SysRoom sysRoom = this.queryById(id);
        if (null == sysRoom) {
            log.info("id = {} 不存在", id);
            throw new GlobalException("该实例不存在");
        } else if (sysRoom.getStatus().equals(1)) {
            throw new GlobalException("该实例已经删除");
        }
        try {
            sysRoom.setUpdateTime(new Date());
            sysRoom.setStatus(1);
            log.info("要删除的id: {}", id);
            this.sysRoomMapper.update(sysRoom);
        } catch (Exception e) {
            log.info("删除房屋信息失败：{}", e.getMessage());
        }
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
        return this.sysRoomMapper.deleteById(id) > 0;
    }
}
