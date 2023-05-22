package cn.withwang.cs.mapper;

import cn.withwang.cs.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface RoleMapper {
    /**
     * @description 插入新的role信息
     * @param role  要插入Role的信息
     * @return int 影响的行数
     */
    @Insert("INSERT INTO tb_role (name_en, name_zh, level, remark) VALUES (#{nameEn}, #{nameZh}, #{level}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Role role);

    /**
     * @description 根据角色ID和权限ID列表插入新的角色权限记录
     * @param roleId 角色ID
     * @param permissions 权限ID列表
     * @return int 插入操作影响的行数
     */
    @Insert("<script>"
            + "INSERT INTO tb_role_permission (role_id, permission_id) VALUES "
            + "<foreach item='item' index='index' collection='list' separator=','>"
            + "(#{roleId}, #{item})"
            + "</foreach>"
            + "</script>")
    int insertRolePermissions(@Param("roleId") int roleId, @Param("list") List<Integer> permissions);


    @Update("UPDATE tb_role SET name_en = #{nameEn}, name_zh = #{nameZh}, level = #{level}, remark = #{remark} WHERE id = #{id}")
    void update(Role role);

    @Select("SELECT * FROM tb_role WHERE id = #{id}")
    Role select(Integer id);


    /**
     * @description 根据用户ID查询用户的角色列表
     * @param userId 用户ID
     * @return ArrayList<Role> 角色列表
     */
    @Select("SELECT * FROM tb_role WHERE id IN (SELECT role_id FROM tb_user_role WHERE user_id = #{userId})")
    @Results({
            @Result(property = "nameEn", column = "name_en"),
            @Result(property = "nameZh", column = "name_zh"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    ArrayList<Role> selectRolesByUserId(int userId);

    /**
     * @description 查询所有角色
     * @return  ArrayList<Role> 角色列表
     */
    @Select("SELECT * FROM tb_role")
    @Results({
            @Result(property = "nameEn", column = "name_en"),
            @Result(property = "nameZh", column = "name_zh"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    ArrayList<Role> selectAllRoles();

    /**
     * @description 根据角色名称查询角色信息
     * @param name_en 角色名称
     * @return ArrayList<Integer> 权限ID列表
     */
    @Select("SELECT * FROM tb_role WHERE name_en = #{name_en}")
    @Results({
            @Result(property = "nameEn", column = "name_en"),
            @Result(property = "nameZh", column = "name_zh"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Role selectByName(String name_en);

}
