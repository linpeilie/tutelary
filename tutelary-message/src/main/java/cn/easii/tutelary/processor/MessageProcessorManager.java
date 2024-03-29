package cn.easii.tutelary.processor;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageProcessorManager {

    private static final Log LOGGER = LogFactory.get(MessageProcessorManager.class);

    private static final Map<Byte, MessageProcessor<? extends BaseMessage>> HANDLER_MAP = new ConcurrentHashMap<>();

    public static MessageProcessor<? extends BaseMessage> getHandler(byte cmd) {
        return HANDLER_MAP.get(cmd);
    }

    public static void register(MessageProcessor<? extends BaseMessage> handler) {
        Class<? extends BaseMessage> cmdClass = handler.getCmdClass();
        Message message = cmdClass.getAnnotation(Message.class);
        if (message == null) {
            LOGGER.error("Class [ {} ] @Message annotation cannot null", cmdClass.getName());
            throw new RuntimeException("Class [" + cmdClass.getName() + "] @Message annotation cannot null");
        }
        LOGGER.debug("MessageProcessor[ {} ] register, cmd : {}", handler.getClass().getName(), message.cmd());
        MessageProcessor<? extends BaseMessage> oldMessageHandler = HANDLER_MAP.putIfAbsent(message.cmd(), handler);
        if (oldMessageHandler != null) {
            LOGGER.error("MessageHandler [ {} ] and [ {} ] repeated command", handler.getClass().getName(),
                oldMessageHandler.getClass().getName()
            );
            throw new RuntimeException("MessageHandler [ " + handler.getClass().getName() +
                                       " ] and [ " +
                                       oldMessageHandler.getClass().getName() +
                                       " ] repeated command");
        }

    }

}
