package com.ariel.spring.websocket.config;

import com.ariel.spring.websocket.handler.ChatHandler;
import com.ariel.spring.websocket.interceptor.ChatHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.PathContainer;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.EnumSet;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    WebSocketConfigurer createWebSocketConfigurer(ChatHandler chatHandler, ChatHandshakeInterceptor chatInterceptor) {
        return new WebSocketConfigurer() {
            public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
                // 把URL与指定的WebSocketHandler关联，可关联多个:
                registry.addHandler(chatHandler, "/chat").addInterceptors(chatInterceptor);
            }
        };
    }

}
