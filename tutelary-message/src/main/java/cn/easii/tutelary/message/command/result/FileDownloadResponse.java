package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
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
