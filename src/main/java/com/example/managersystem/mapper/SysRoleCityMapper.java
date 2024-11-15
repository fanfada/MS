package com.example.managersystem.mapper;
import com.example.managersystem.domain.SysRoleCity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色和城市关联表(SysRoleCity)表数据库访问层
 *
 * @author fanfada
 * @since 2024-11-15 16:34:35
 */
@Mapper
public interface SysRoleCityMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<SysRoleCity> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param roleId
     * @return 实例对象
     */
    SysRoleCity queryByRoleId(final String roleId);

    /**
     * 统计总行数
     *
     * @param sysRoleCity 查询条件
     * @return 总行数
     */
    long count(SysRoleCity sysRoleCity);

    /**
     * 新增数据
     *
     * @param sysRoleCity 实例对象
     * @return 影响行数
     */
    int insert(SysRoleCity sysRoleCity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRoleCity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysRoleCity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRoleCity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysRoleCity> entities);

    /**
     * 修改数据
     *
     * @param sysRoleCity 实例对象
     * @return 影响行数
     */
    int update(SysRoleCity sysRoleCity);

    /**
     * 通过主键删除数据
     *
     * @param roleId
     * @return 影响行数
     */
    int deleteById(final String roleId);

}
