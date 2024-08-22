package com.axr.starrybackend.config.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebsocketConfig {

    @Bean
    // 注入 ServerEndpointExporter bean 对象，该对象会自动注册使用了 @ServerEndpoint 注解声明的 WebSocket endpoint
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
