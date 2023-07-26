package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.google.protobuf.ByteString;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ProtobufClass
@Command(CommandEnum.FILE_DOWNLOAD)
public class FileDownloadResponse extends ContinuousCommandResponse {

    private String identifier;

    private String fileName;

    private byte[] bytes;

}
