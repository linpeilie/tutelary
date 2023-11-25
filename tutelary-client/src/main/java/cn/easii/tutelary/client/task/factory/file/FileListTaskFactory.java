package cn.easii.tutelary.client.task.factory.file;

import cn.easii.tutelary.client.command.file.FileListCommand;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.FileListRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
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
