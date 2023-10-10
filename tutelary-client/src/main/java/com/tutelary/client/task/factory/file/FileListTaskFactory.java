package com.tutelary.client.task.factory.file;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.file.FileListCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.FileListRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.fileList)
public class FileListTaskFactory implements TaskFactory<FileListRequest> {
    @Override
    public Task create(final String taskId, final Instrumentation inst, final FileListRequest param) {
        return new DefaultTask(taskId, commandType(), new FileListCommand(param));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.FILE_LIST;
    }
}
