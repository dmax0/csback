package cn.withwang.cs.controller;

import cn.withwang.cs.dto.ChatMessageDto;
import cn.withwang.cs.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@MessageMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @SendTo("/topic/messages")
    public ChatMessageDto send(ChatMessageDto message) throws Exception {
        chatService.handleChatMessage(message);
        return message;
    }
}
