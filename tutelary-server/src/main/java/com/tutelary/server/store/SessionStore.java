package com.tutelary.server.store;

import com.tutelary.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    private SessionStore() {}

    public static void addSession(Session session) {
        SESSION_MAP.putIfAbsent(session.getSessionId(), session);
    }

    public static Session getSession(String sessionId) {
        return SESSION_MAP.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        SESSION_MAP.remove(sessionId);
    }

}
