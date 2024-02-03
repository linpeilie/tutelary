package cn.easii.tutelary;

import cn.easii.tutelary.common.thread.NamedThreadFactory;
import cn.easii.tutelary.remoting.netty.codec.ProtobufCodec;
import cn.easii.tutelary.utils.AuthHelper;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SessionStore {

    private static final ProtobufCodec codec = new ProtobufCodec();

    private static final Map<String, CopyOnWriteArraySet<WebSocketSession>> SESSION_MAP = new HashMap<>();

    private static final ScheduledExecutorService scheduledExecutorService;

    static {
        scheduledExecutorService =
            new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("session-check"));
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ThrowableUtil.safeExec(() -> {
                SESSION_MAP.forEach((userId, sessions) -> {
                    sessions.forEach(session -> {
                        if (!session.isOpen()) {
                            sessions.remove(session);
                        }
                    });
                });
            });
        }, 1, 5, TimeUnit.MINUTES);
    }

    private String getUserIdBySession(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        String tokenName = AuthHelper.getTokenName();
        if (attributes.containsKey(tokenName)) {
            Object value = attributes.get(tokenName);
            String[] values = (String[]) value;
            if (ArrayUtil.isNotEmpty(values)) {
                return AuthHelper.getUserIdByToken(values[0]);
            }
        }
        return null;
    }

    private void closeSession(WebSocketSession session) {
        ThrowableUtil.safeExec(session::close);
    }

    public void addSession(WebSocketSession session) {
        String userId = getUserIdBySession(session);
        // If the current session does not have a unique identity, the connection is broken.
        if (StrUtil.isEmpty(userId)) {
            closeSession(session);
            return;
        }

        // 添加用户对应的 session
        synchronized (userId.intern()) {
            CopyOnWriteArraySet<WebSocketSession> sessions =
                SESSION_MAP.getOrDefault(userId, new CopyOnWriteArraySet<>());
            sessions.add(session);
            SESSION_MAP.put(userId, sessions);
        }
    }

    public void removeSession(WebSocketSession session) {
        final String userId = getUserIdBySession(session);
        if (StrUtil.isNotEmpty(userId)) {
            synchronized (userId.intern()) {
                CopyOnWriteArraySet<WebSocketSession> sessions = SESSION_MAP.get(userId);
                if (CollectionUtil.isNotEmpty(sessions)) {
                    sessions.remove(session);
                }
            }
        }
    }

    private void sendMessage(WebSocketSession session, Object message) {
        ThrowableUtil.safeExec(() -> {
            if (session.isOpen()) {
                session.sendMessage(new BinaryMessage(codec.encode(message)));
            }
        });
    }

    public boolean containsSessionByToken(String userId) {
        return SESSION_MAP.containsKey(userId);
    }

    public void sendMessage(String userId, Object message) {
        Collection<WebSocketSession> sessions = SESSION_MAP.get(userId);
        if (CollectionUtil.isNotEmpty(sessions)) {
            sessions.forEach(session -> sendMessage(session, message));
        }
    }

}
