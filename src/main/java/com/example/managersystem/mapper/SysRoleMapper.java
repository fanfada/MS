package com.example.managersystem.mapper;

import com.example.managersystem.domain.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author fanfada
 * @since 2024-11-15 16:34:34
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysRole> queryAll(String userId);

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRole queryById(@Param("roleId") String roleId, @Param("userId") String userId);

    /**
     * 通过userid查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysRole queryByUserId(String userId);

    /**
     * 统计总行数
     *
     * @param sysRole 查询条件
     * @return 总行数
     */
    long count(SysRole sysRole);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int insert(SysRole sysRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysRole> entities);

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int update(SysRole sysRole);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteById(String roleId);

}
