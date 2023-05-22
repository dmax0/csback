package cn.withwang.cs.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Conversation {
    private int id;
    private int fromUserId;
    private int toUserId;
    private int unreadCount;
    private int lastMessageId;
    private Date createdAt;
    private Date updatedAt;

   
}
