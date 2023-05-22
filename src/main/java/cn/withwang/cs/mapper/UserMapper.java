package cn.withwang.cs.mapper;

import cn.withwang.cs.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO tb_user (nickname, avatar, email, birthday, gender, status, username, password, createdAt, updatedAt) VALUES (#{nickname}, #{avatar}, #{email}, #{birthday}, #{gender}, #{status}, #{username}, #{password}, NOW(), NOW())")
    void insert(User user);

    @Insert({
            "<script>",
            "INSERT INTO tb_user_role (user_id, role_id) VALUES ",
            "<foreach collection='roleIds' item='roleId' index='index' separator=','>",
            "(#{userId}, #{roleId})",
            "</foreach>",
            "</script>"
    })
    void insertRoles(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);


    @Update("UPDATE tb_user SET nickname = #{nickname}, avatar = #{avatar}, email = #{email}, birthday = #{birthday}, gender = #{gender}, status = #{status}, username = #{username}, password = #{password}, updatedAt = NOW() WHERE id = #{id}")
    void update(User user);

    @Update("UPDATE tb_user SET status = 0, updatedAt = NOW() WHERE id = #{id}")
    void delete(Integer id);

    @Select("SELECT * FROM tb_user WHERE id = #{id}")
    @Results({
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = Timestamp.class)
    })
    User select(Integer id);

    @Select("SELECT * FROM tb_user ORDER BY id DESC LIMIT #{offset}, #{limit}")
    @Results({
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = Timestamp.class)
    })
    ArrayList<User> selectByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    @Select("SELECT * FROM tb_user WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "email", column = "email"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "status", column = "status"),
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class)
    })
    User selectByUsername(String username);



    @Select("SELECT * FROM tb_user WHERE id = #{id}")
    @Results({
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = Timestamp.class)
    })
    User selectById(int id);

    @Select("SELECT * FROM tb_user WHERE email = #{email}")
    @Results({
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = Timestamp.class)
    })
    User selectByEmail(String email);


    // 修改用户status
    @Update("UPDATE tb_user SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
