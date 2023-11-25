package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.common.RequestMessage;

import static cn.easii.tutelary.MessageCmd.HEARTBEAT;

@Message(cmd = HEARTBEAT)
public class HeartbeatRequest extends RequestMessage {

}
