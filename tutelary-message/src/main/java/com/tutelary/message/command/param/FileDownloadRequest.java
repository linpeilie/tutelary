package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ProtobufClass
@Command(CommandEnum.FILE_DOWNLOAD)
public class FileDownloadRequest extends CommandRequest {

    private String filePath;

    private String identifier;

}
