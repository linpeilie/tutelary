package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.command.AbstractSingleTemporaryCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.message.command.param.FileListRequest;
import cn.easii.tutelary.message.command.result.FileListResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.fileList)
public class FileListCommandExecute
    extends AbstractSingleTemporaryCommandExecuteAdapter<FileListRequest, FileListResponse> {
    @Override
    public Integer commandCode() {
        return CommandConstants.fileList;
    }
}
