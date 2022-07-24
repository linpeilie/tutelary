package com.tutelary.processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tutelary.annotation.Message;
import com.tutelary.common.BaseMessage;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

public class MessageProcessorManager {

    private static final Log LOG = LogFactory.get();

    private static final Map<Byte, MessageProcessor<? extends BaseMessage>> HANDLER_MAP = new ConcurrentHashMap<>();

    public static MessageProcessor<? extends BaseMessage> getHandler(byte cmd) {
        return HANDLER_MAP.get(cmd);
    }

    public static void register(MessageProcessor<? extends BaseMessage> handler) {
        Class<? extends BaseMessage> cmdClass = handler.getCmdClass();
        Message message = cmdClass.getAnnotation(Message.class);
        if (message == null) {
            LOG.error("Class [ {} ] @Message annotation cannot null", cmdClass.getName());
            throw new RuntimeException("Class [" + cmdClass.getName() + "] @Message annotation cannot null");
        }
        MessageProcessor<? extends BaseMessage> oldMessageHandler = HANDLER_MAP.putIfAbsent(message.cmd(), handler);
        if (oldMessageHandler != null) {
            LOG.error("MessageHandler [ {} ] and [ {} ] repeated command", handler.getClass().getName(),
                oldMessageHandler.getClass().getName());
            throw new RuntimeException("MessageHandler [ " + handler.getClass().getName() +
                    " ] and [ " +
                    oldMessageHandler.getClass().getName() +
                    " ] repeated command");
        }

    }

}
