package cn.withwang.cs.mapper;

import cn.withwang.cs.entity.Message;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM tb_message WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "fromUserId", column = "from_user_id"),
            @Result(property = "toUserId", column = "to_user_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "status", column = "status"),
            @Result(property = "createdAt", column = "created_at"),
    })
    Message findById(int id);

    @Insert("INSERT INTO tb_message(content, from_user_id, to_user_id, type, status, created_at, updated_at) VALUES(#{content}, #{fromUserId}, #{toUserId}, #{type}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Message message);

    @Update("UPDATE tb_message SET content = #{content}, from_user_id = #{fromUserId}, to_user_id = #{toUserId}, type = #{type}, status = #{status}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Message message);

    @Delete("DELETE FROM tb_message WHERE id = #{id}")
    void delete(int id);


}

