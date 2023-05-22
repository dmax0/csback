package cn.withwang.cs.service;

import cn.withwang.cs.dto.ChatMessageDto;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import cn.withwang.cs.mapper.MessageMapper;
import cn.withwang.cs.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

@Service
public class ChatService {

    @Autowired
    private MessageMapper messageMapper;

    public void handleChatMessage(ChatMessageDto chatMessage) {
        Message message = new Message();
        message.setContent(HtmlUtils.htmlEscape(chatMessage.getContent()));
        message.setFromUserId(chatMessage.getFromUserId());
        message.setToUserId(chatMessage.getToUserId());
        message.setType(chatMessage.getType());
        message.setStatus(1);  // 1 - 未读 2 - 已读
        message.setCreatedAt(new Date());
        message.setUpdatedAt(new Date());
        messageMapper.insert(message);
    }
}

