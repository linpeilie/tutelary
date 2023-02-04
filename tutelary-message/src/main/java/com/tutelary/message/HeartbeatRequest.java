package com.tutelary.message;

import com.tutelary.annotation.Message;
import com.tutelary.common.RequestMessage;

import static com.tutelary.MessageCmd.HEARTBEAT;

@Message(cmd = HEARTBEAT)
public class HeartbeatRequest extends RequestMessage {

}
