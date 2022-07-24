package com.tutelary.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.HexUtil;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tutelary.MessageCmd;
import com.tutelary.bean.vo.R;
import com.tutelary.common.constants.CommandTypeConstants;
import com.tutelary.message.ClientCommandRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/protobuf")
public class ProtobufController {

    @SneakyThrows
    @PostMapping("transToProtobuf")
    public R<String> transToProtobuf(@RequestBody ClientCommandRequestMessage clientCommandRequestMessage) {
        Codec<ClientCommandRequestMessage> codec = ProtobufProxy.create(ClientCommandRequestMessage.class);
        byte[] bytes = codec.encode(clientCommandRequestMessage);
        int length = bytes.length;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte(MessageCmd.CLIENT_COMMAND_REQUEST);
        byteBuf.writeInt(length);
        byteBuf.writeBytes(bytes);
        byte[] tempBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(tempBytes);
        String s = HexUtil.encodeHexStr(tempBytes);
        return R.success(s);
    }

}
