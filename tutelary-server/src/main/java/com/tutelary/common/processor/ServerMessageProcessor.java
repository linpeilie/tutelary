package com.tutelary.common.processor;

import com.tutelary.common.BaseMessage;
import com.tutelary.processor.MessageProcessor;

public interface ServerMessageProcessor<T extends BaseMessage> extends MessageProcessor<T> {
}
