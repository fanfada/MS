package com.example.managersystem.mapper;
import com.example.managersystem.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表数据库访问层
 *
 * @author fanfada
 * @since 2024-11-15 16:34:36
 */
@Mapper
public interface SysUserRoleMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysUserRole> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUserRole queryById(String userId);

    /**
     * 统计总行数
     *
     * @param sysUserRole 查询条件
     * @return 总行数
     */
    long count(SysUserRole sysUserRole);

    /**
     * 新增数据
     *
     * @param sysUserRole 实例对象
     * @return 影响行数
     */
    int insert(SysUserRole sysUserRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUserRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysUserRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUserRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysUserRole> entities);

    /**
     * 修改数据
     *
     * @param sysUserRole 实例对象
     * @return 影响行数
     */
    int update(SysUserRole sysUserRole);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(String userId);

}
