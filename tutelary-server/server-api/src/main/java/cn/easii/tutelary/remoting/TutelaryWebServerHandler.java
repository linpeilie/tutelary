package cn.easii.tutelary.remoting;

import cn.easii.tutelary.SessionStore;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class TutelaryWebServerHandler implements WebSocketHandler {

    private final SessionStore sessionStore;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.debug("WebSocket connection established, uri : {}, remoteAddress : {}",
            webSocketSession.getUri(), webSocketSession.getRemoteAddress());
        sessionStore.addSession(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
        throws Exception {
        // ignore
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.error("WebSocket connection occur error, uri : {}, remoteAddress : {}, error : {}",
            webSocketSession.getUri(), webSocketSession.getRemoteAddress(),
            ExceptionUtil.stacktraceToString(throwable));
        sessionStore.removeSession(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.debug("WebSocket connection closed, uri: {}, remoteAddress : {}, closeStatus : {}",
            webSocketSession.getUri(), webSocketSession.getRemoteAddress(), closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
