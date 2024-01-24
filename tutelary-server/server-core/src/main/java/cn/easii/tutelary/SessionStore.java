package cn.easii.tutelary;

import cn.easii.tutelary.remoting.netty.codec.ProtobufCodec;
import cn.easii.tutelary.utils.AuthHelper;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.easii.tutelary.message.ErrorMessage;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SessionStore {

    private static final ProtobufCodec codec = new ProtobufCodec();

    private static final Multimap<String, WebSocketSession> SESSION_MAP = HashMultimap.create();

    private String getTokenBySession(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        String tokenName = AuthHelper.getTokenName();
        if (attributes.containsKey(tokenName)) {
            Object value = attributes.get(tokenName);
            String[] values = (String[]) value;
            if (ArrayUtil.isNotEmpty(values)) {
                return values[0];
            }
        }
        return null;
    }

    private void closeSession(WebSocketSession session) {
        ThrowableUtil.safeExec(session::close);
    }

    public void addSession(WebSocketSession session) {
        String token = getTokenBySession(session);
        // If the current session does not have a unique identity, the connection is broken.
        if (StrUtil.isEmpty(token)) {
            closeSession(session);
            return;
        }

        // 添加用户对应的 session
        synchronized (token.intern()) {
            SESSION_MAP.put(token, session);
        }
    }

    public void removeSession(WebSocketSession session) {
        final String token = getTokenBySession(session);
        if (StrUtil.isNotEmpty(token)) {
            synchronized (token.intern()) {
                SESSION_MAP.remove(token, session);
            }
        }
    }

    private void sendMessage(WebSocketSession session, Object message) {
        ThrowableUtil.safeExec(() ->
            session.sendMessage(new BinaryMessage(codec.encode(message))));
    }

    public boolean containsSessionByToken(String token) {
        return SESSION_MAP.containsKey(token);
    }

    public void sendMessage(String token, Object message) {
        Collection<WebSocketSession> sessions = SESSION_MAP.get(token);
        if (CollectionUtil.isNotEmpty(sessions)) {
            sessions.forEach(session -> sendMessage(session, message));
        }
    }

}
