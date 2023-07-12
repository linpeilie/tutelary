package com.tutelary.command.ext;

import com.tutelary.command.AbstractSingleTemporaryCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.message.command.param.FileListRequest;
import com.tutelary.message.command.result.FileListResponse;
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
