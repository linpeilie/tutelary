package cn.easii.tutelary;

import cn.easii.tutelary.utils.AuthHelper;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.easii.tutelary.message.ErrorMessage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionStore {

    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

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
        session.getAsyncRemote().sendObject(ErrorMessage.build("未登录"));
        closeSession(session);
    }

    private void closeSession(Session session) {
        ThrowableUtil.safeExec(session::close);
    }

    public void addSession(Session session) {
        final String token = getTokenBySession(session);
        if (StrUtil.isEmpty(token) || !AuthHelper.isLogin(token)) {
            closeWithUnauthorized(session);
            return;
        }
        // 添加用户对应的 session
        final Session oldSession = SESSION_MAP.put(token, session);
        if (oldSession != null) {
            oldSession.getAsyncRemote().sendObject(ErrorMessage.build("已在其他地方登录或打开了多个窗口"));
            closeSession(oldSession);
        }
    }

    public void removeSession(Session session) {
        final String token = getTokenBySession(session);
        if (StrUtil.isNotEmpty(token)) {
            SESSION_MAP.remove(token, session);
        }
    }

    private void sendMessage(Session session, Object message) {
        ThrowableUtil.safeExec(() ->
            session.getAsyncRemote().sendObject(message));
    }

    public boolean containsSessionByToken(String token) {
        return SESSION_MAP.containsKey(token);
    }

    public void sendMessage(String token, Object message) {
        if(SESSION_MAP.containsKey(token)) {
            sendMessage(SESSION_MAP.get(token), message);
        }
    }

}
