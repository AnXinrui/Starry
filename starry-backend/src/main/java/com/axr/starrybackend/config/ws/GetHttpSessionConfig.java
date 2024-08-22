package com.axr.starrybackend.config.ws;


import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

/**
 * 获取HttpSession，这样的话，ChatEndpoint类就能操作HttpSession
 */
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 获取HttpSession对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 这里将httpSession对象保存起来，存到ServerEndpointConfig里面，然后在ChatEndpoint类的onOpen方法就能通过EndpointConfig类对象去获取这里存入的数据
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
