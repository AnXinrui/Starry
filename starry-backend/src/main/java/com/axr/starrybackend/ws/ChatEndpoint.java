package com.axr.starrybackend.ws;

import com.alibaba.fastjson2.JSON;
import com.axr.starrybackend.config.ws.GetHttpSessionConfig;
import com.axr.starrybackend.model.vo.User.UserVO;
import com.axr.starrybackend.ws.pojo.Message;
import com.axr.starrybackend.ws.pojo.MessageUtils;
import com.axr.starrybackend.ws.utils.ServerEncoder;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

import static com.axr.starrybackend.constant.UserConstant.USER_LOGIN_STATE;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class, encoders = { ServerEncoder.class })
@Component
@Slf4j
public class ChatEndpoint {
    private HttpSession httpSession;
    private static final Map<String, Session> matchingUsers = new ConcurrentHashMap<>();
    private static final Map<String, Session> matchedUsers = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws EncodeException, IOException {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        Object userObject = this.httpSession.getAttribute(USER_LOGIN_STATE);
        UserVO userVO = (UserVO) userObject;
        String userAccount = userVO.getUserAccount();
        matchingUsers.put(userAccount, session);
         log.info("User {} has joined the chat", userAccount);

        this.matchUser(userAccount, session);

        // session.getBasicRemote().sendObject(MessageUtils.success(1, "Tom", "Hello"));
    }

    @OnMessage
    public void onMessage(Session session, String msg) throws EncodeException, IOException {
        Message message = JSON.parseObject(msg, Message.class);
        System.out.println(msg);
        Session matchedSession = matchedUsers.get(message.getToName());
        if (matchedSession == null) {
            session.getBasicRemote().sendObject(MessageUtils.error("对方已下线！"));
        }
        matchedSession.getBasicRemote().sendObject(MessageUtils.success(1, true, message.getMessage()));
        session.getBasicRemote().sendObject(MessageUtils.success(1, false, message.getMessage()));
    }

    private void matchUser(String userAccount, Session session) {
        ScheduledFuture<?> timeoutTask = scheduler.schedule(() -> {
            // 如果超时且还未匹配到用户，则停止匹配并移除用户
            if (matchingUsers.containsKey(userAccount)) {
                matchingUsers.remove(userAccount);
                try {
                    session.getBasicRemote().sendObject(MessageUtils.error("目前没有找到匹配的在线用户！"));
                    session.close(); // 可选：关闭WebSocket连接
                } catch (IOException e) {
                    log.error("Error sending timeout message to user {}", userAccount, e);
                } catch (EncodeException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 3, TimeUnit.MINUTES);

        // 尝试匹配
        scheduler.execute(() -> {
            while (matchingUsers.containsKey(userAccount)) {
                // 获取其他用户
                Optional<Map.Entry<String, Session>> matchedUser = matchingUsers.entrySet().stream()
                        .filter(entry -> !entry.getKey().equals(userAccount))
                        .findAny();

                if (matchedUser.isPresent()) {
                    try {
                        // 成功匹配到用户
                        String matchedUserAccount = matchedUser.get().getKey();
                        Session matchedSession = matchedUser.get().getValue();

                        // 发送匹配成功的消息
                        session.getBasicRemote().sendObject(MessageUtils.success(0, false, matchedUserAccount));
                        matchedSession.getBasicRemote().sendObject(MessageUtils.success(0, false, userAccount));

                        // 从匹配队列中移除这两个用户
                        matchedUsers.put(userAccount, session);
                        matchedUsers.put(matchedUserAccount, matchedSession);
                        matchingUsers.remove(userAccount);
                        matchingUsers.remove(matchedUserAccount);


                        // 取消超时任务
                        timeoutTask.cancel(false);

                        // log.info("User {} matched with {}", userAccount, matchedUserAccount);
                        break;
                    } catch (IOException | EncodeException e) {
                        log.error("Error sending match message to user {}", userAccount, e);
                    }
                }

                // 如果没有找到匹配用户，稍等片刻再继续尝试
                try {
                    Thread.sleep(5000); // 等待5秒钟再尝试匹配
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
