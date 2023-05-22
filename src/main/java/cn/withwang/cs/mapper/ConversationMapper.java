package cn.withwang.cs.mapper;
import cn.withwang.cs.entity.Conversation;
import org.apache.ibatis.annotations.*;

public interface ConversationMapper {
    @Select("SELECT * FROM tb_conversation WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fromUserId", column = "from_user_id"),
            @Result(property = "toUserId", column = "to_user_id"),
            @Result(property = "unreadCount", column = "unread_count"),
            @Result(property = "lastMessageId", column = "last_message_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Conversation findById(int id);

    @Insert("INSERT INTO tb_conversation(from_user_id, to_user_id, unread_count, last_message_id, created_at, updated_at) VALUES(#{fromUserId}, #{toUserId}, #{unreadCount}, #{lastMessageId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Conversation conversation);

    @Update("UPDATE tb_conversation SET from_user_id = #{fromUserId}, to_user_id = #{toUserId}, unread_count = #{unreadCount}, last_message_id = #{lastMessageId}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Conversation conversation);

    @Delete("DELETE FROM tb_conversation WHERE id = #{id}")
    void delete(int id);


}
