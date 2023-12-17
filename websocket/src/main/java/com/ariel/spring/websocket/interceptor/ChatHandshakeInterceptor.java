package com.ariel.spring.websocket.interceptor;

import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;

public class ChatHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    public ChatHandshakeInterceptor() {
        // 指定从HttpSession复制属性到WebSocketSession:
//        super(List.of(UserController.KEY_USER));
    }
}
