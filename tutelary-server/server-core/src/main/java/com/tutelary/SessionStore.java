package com.tutelary;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.tutelary.common.constants.Constants;
import com.tutelary.common.utils.ThrowableUtil;
import com.tutelary.remoting.netty.utils.ProtobufEncodeUtils;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionStore {

    private static final String PARAMETER_TOKEN = Constants.Authentication.TOKEN;

    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();

    private static final Multimap<String, Session> SESSION_MAP = HashMultimap.create();

    private String getTokenBySession(Session session) {
        final Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        if (requestParameterMap.containsKey(PARAMETER_TOKEN)) {
            final List<String> list = requestParameterMap.get(PARAMETER_TOKEN);
            if (CollectionUtil.isNotEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    public void addSession(Session session) {
        final String token = getTokenBySession(session);
        if (StrUtil.isEmpty(token)) {
            session.getAsyncRemote().sendText("未登录");
            ThrowableUtil.safeExec(session::close);
            return;
        }
        SESSIONS.add(session);
        SESSION_MAP.put(token, session);
    }

    public void removeSession(Session session) {
        SESSIONS.remove(session);
        final String token = getTokenBySession(session);
        if (StrUtil.isNotEmpty(token)) {
            SESSION_MAP.remove(token, session);
        }
    }

    private void sendMessage(Session session, Object message) {
        ThrowableUtil.safeExec(() ->
            session.getAsyncRemote().sendBinary(ByteBuffer.wrap(ProtobufEncodeUtils.encode(message))));
    }

    public void sendAllMessage(Object message) {
        SESSIONS.forEach(session -> sendMessage(session, message));
    }

    public boolean hasSession(String token) {
        return SESSION_MAP.containsKey(token);
    }

    public void sendMessage(Object message, String token) {
        if (SESSION_MAP.containsKey(token)) {
            SESSION_MAP.get(token).forEach(session -> sendMessage(session, message));
        }
    }

}
