package cn.withwang.cs.dto;

public class ChatMessageDto {

    private Integer id;
    private String content;
    private Integer fromUserId;
    private Integer toUserId;
    private Integer type;

    public ChatMessageDto(String content, Integer fromUserId, Integer toUserId, Integer type) {
        this.content = content;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
