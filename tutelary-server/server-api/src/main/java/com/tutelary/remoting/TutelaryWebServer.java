package com.tutelary.remoting;

import com.tutelary.SessionStore;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ServerEndpoint(value = "/ws", configurator = TutelaryWebServerConfig.class)
public class TutelaryWebServer {

    private static SessionStore sessionStore;

    @Autowired
    public void setSessionStore(final SessionStore sessionStore) {
        TutelaryWebServer.sessionStore = sessionStore;
    }

    @OnOpen
    public void onOpen(Session session) {
        sessionStore.addSession(session);
    }

    public void onClose(Session session) {
        sessionStore.removeSession(session);
    }

}
