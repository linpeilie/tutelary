package cn.easii.tutelary;

public interface MessageCmd {

    byte ERROR = 0;

    byte CLIENT_REGISTER_REQUEST = 1;

    byte CLIENT_REGISTER_RESPONSE = 2;

    byte CLIENT_COMMAND_REQUEST = 3;

    byte CLIENT_COMMAND_RESPONSE = 4;

    byte INSTANCE_INFO_REPORT = 5;

    byte HEARTBEAT = 6;

    byte COMMAND_CANCEL_REQUEST = 7;

    byte COMMAND_CANCEL_RESPONSE = 8;

}
