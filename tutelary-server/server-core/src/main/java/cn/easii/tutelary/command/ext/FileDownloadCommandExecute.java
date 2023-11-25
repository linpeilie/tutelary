package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.command.store.FileDownloadMetadata;
import cn.easii.tutelary.command.store.FileDownloadStore;
import cn.easii.tutelary.command.AbstractTemporaryCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.enums.StateEnum;
import cn.easii.tutelary.message.command.param.FileDownloadRequest;
import cn.easii.tutelary.message.command.result.FileDownloadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Extension(commandCode = CommandConstants.fileDownload)
public class FileDownloadCommandExecute
    extends AbstractTemporaryCommandExecuteAdapter<FileDownloadRequest, FileDownloadResponse> {

    @Override
    protected void callResult(final String instanceId, final String taskId, final FileDownloadResponse response) {
        final FileDownloadMetadata fileDownloadMetadata = FileDownloadStore.get(response.getIdentifier());
        if (fileDownloadMetadata == null) {
            return;
        }
        try {
            if (StateEnum.SUCCESS.getValue() != response.getState()) {
                if (!fileDownloadMetadata.completed()) {
                    FileDownloadStore.remove(response.getIdentifier());
                    fileDownloadMetadata.handleError(response.getMessage());
                }
                return;
            }
            fileDownloadMetadata.handleMessage(response);
            if (response.isEnd()) {
                FileDownloadStore.remove(response.getIdentifier());
                fileDownloadMetadata.handleFinish();
            }
        } catch (Exception e) {
            log.error("file download call result occur exception", e);
            FileDownloadStore.remove(response.getIdentifier());
            fileDownloadMetadata.handleError(e.getMessage());
            // send cancel
            cancelTaskBiz.cancelTask(instanceId, taskId);
        }
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.fileDownload;
    }
}
