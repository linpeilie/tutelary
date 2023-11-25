package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
@ProtobufClass
public class FileInfo implements Serializable {

    private String fileName;

    private String filePath;

    private String fileSize;

    private Date lastModifiedTime;

}
