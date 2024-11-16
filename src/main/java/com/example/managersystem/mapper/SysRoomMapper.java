package com.example.managersystem.mapper;

import com.example.managersystem.domain.SysRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 房屋信息表(SysRoom)表数据库访问层
 *
 * @author fanfada
 * @since 2024-11-07 12:36:52
 */
@Mapper
public interface SysRoomMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysRoom> queryAll(final String zipCode);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRoom queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param sysRoom 查询条件
     * @return 总行数
     */
    long count(SysRoom sysRoom);

    /**
     * 新增数据
     *
     * @param sysRoom 实例对象
     * @return 影响行数
     */
    int insert(SysRoom sysRoom);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRoom> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysRoom> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRoom> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysRoom> entities);

    /**
     * 修改数据
     *
     * @param sysRoom 实例对象
     * @return 影响行数
     */
    int update(SysRoom sysRoom);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
