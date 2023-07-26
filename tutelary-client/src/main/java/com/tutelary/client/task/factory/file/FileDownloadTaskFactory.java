package com.tutelary.client.task.factory.file;

import com.tutelary.client.command.file.FileDownloadCommand;
import com.tutelary.client.task.ContinuousTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.FileDownloadRequest;
import com.tutelary.message.command.result.FileDownloadResponse;
import java.lang.instrument.Instrumentation;

@Extension(commandCode = CommandConstants.fileDownload)
public class FileDownloadTaskFactory implements TaskFactory<FileDownloadRequest> {
    @Override
    public Task create(final String taskId, final Instrumentation inst, final FileDownloadRequest param) {
        return new ContinuousTask<FileDownloadResponse>(taskId, commandType(), new FileDownloadCommand(param));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.FILE_DOWNLOAD;
    }
}
