package cn.withwang.cs.mapper;

import cn.withwang.cs.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PermissionMapper {

    /**
     * @description 查询所有Permission的信息
     * @return List<Permission>
     */
    @Select("SELECT * FROM tb_permission")
    @Results({
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Permission> selectAll();

    @Select("SELECT * FROM tb_permission WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "path", column = "path"),

    })
    Permission findById(int id);



    @Insert("INSERT INTO tb_permission (parent_id, path, component, name, icon, type, sort, remark, created_at, updated_at) VALUES (#{parentId}, #{path}, #{component}, #{name}, #{icon}, #{type}, #{sort}, #{remark}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Permission permission);

    @Update("UPDATE tb_permission SET parent_id = #{parentId}, path = #{path}, component = #{component}, name = #{name}, icon = #{icon}, type = #{type}, sort = #{sort}, remark = #{remark}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Permission permission);

    @Select("SELECT * FROM tb_permission WHERE id IN (SELECT permission_id FROM tb_role_permission WHERE role_id = #{roleId})")
    List<Integer> selectByRoleId(Integer roleId);

    @Select("SELECT * FROM tb_permission WHERE id IN (SELECT permission_id FROM tb_role_permission WHERE role_id = #{id})")
    List<Permission> selectPermissionsByRoleId(int id);

    @Select("SELECT * FROM tb_permission WHERE parent_id = #{parentId}")
    List<Permission> selectByParentId(int parentId);

    @Select("SELECT * FROM tb_permission WHERE parent_id = 0")
    List<Permission> selectAllTopPermission();


}
