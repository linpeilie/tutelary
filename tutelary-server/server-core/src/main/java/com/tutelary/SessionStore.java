package com.tutelary;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.tutelary.common.utils.ThrowableUtil;
import com.tutelary.utils.AuthHelper;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionStore {

    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();

    private static final Multimap<String, Session> SESSION_MAP = HashMultimap.create();

    private String getTokenBySession(Session session) {
        final Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        final String tokenName = AuthHelper.getTokenName();
        if (requestParameterMap.containsKey(tokenName)) {
            final List<String> list = requestParameterMap.get(tokenName);
            if (CollectionUtil.isNotEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    private void closeWithUnauthorized(Session session) {
        session.getAsyncRemote().sendText("未登录");
        ThrowableUtil.safeExec(session::close);
    }

    public void addSession(Session session) {
        final String token = getTokenBySession(session);
        if (StrUtil.isEmpty(token) || !AuthHelper.isLogin(token)) {
            closeWithUnauthorized(session);
            return;
        }
        SESSIONS.add(session);
        // 添加用户对应的 session
        synchronized (token.intern()) {
            SESSION_MAP.put(token, session);
        }
    }

    public void removeSession(Session session) {
        SESSIONS.remove(session);
        final String token = getTokenBySession(session);
        if (StrUtil.isNotEmpty(token)) {
            synchronized (token.intern()) {
                SESSION_MAP.remove(token, session);
            }
        }
    }

    public void removeSession(String userId, String token) {
        final Collection<Session> sessions = SESSION_MAP.get(userId);
        if (CollectionUtil.isEmpty(sessions)) {
            return;
        }
        sessions.forEach(session -> {
            final String tokenValue = getTokenBySession(session);
            if (ObjectUtil.equals(tokenValue, token)) {
                removeSession(session);
            }
        });
    }

    private void sendMessage(Session session, Object message) {
        ThrowableUtil.safeExec(() ->
            session.getAsyncRemote().sendObject(message));
    }

    public void sendAllMessage(Object message) {
        SESSIONS.forEach(session -> sendMessage(session, message));
    }

    public void sendMessage(String userId, Object message) {
        final List<String> tokens = AuthHelper.getTokensByUserId(userId);
        if (CollectionUtil.isEmpty(tokens)) {
            return;
        }
        tokens.forEach(token -> {
            final Collection<Session> sessions = SESSION_MAP.get(token);
            if (CollectionUtil.isEmpty(sessions)) {
                return;
            }
            sessions.forEach(session -> sendMessage(session, message));
        });
    }

}
