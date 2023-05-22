package cn.withwang.cs.interceptor;

import cn.withwang.cs.constant.UserStatusConstant;
import cn.withwang.cs.entity.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    /**
     * 绑定用户信息
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert stompHeaderAccessor != null;
        if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())) {
            User user = new User();
            user.setId(Integer.parseInt(Objects.requireNonNull(stompHeaderAccessor.getFirstNativeHeader("id"))));
            user.setUsername(stompHeaderAccessor.getFirstNativeHeader("username"));
            user.setAvatar(stompHeaderAccessor.getFirstNativeHeader("avatar"));
            user.setStatus(UserStatusConstant.ONLINE);

            stompHeaderAccessor.setUser((Principal) user);
        }

        return message;
    }
}