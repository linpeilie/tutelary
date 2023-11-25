package cn.easii.tutelary.client.task.factory.file;

import cn.easii.tutelary.client.command.file.FileDownloadCommand;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.FileDownloadRequest;
import cn.easii.tutelary.message.command.result.FileDownloadResponse;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.ContinuousTask;
import cn.easii.tutelary.client.task.Task;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
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
