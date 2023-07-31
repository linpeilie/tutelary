package com.tutelary.command.store;

import com.tutelary.common.utils.ThrowableUtil;
import com.tutelary.message.command.result.FileDownloadResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RequiredArgsConstructor
public class FileDownloadMetadata {

    private final HttpServletResponse response;
    private final CountDownLatch latch;
    private boolean completed = false;

    private AtomicBoolean downloadResponse = new AtomicBoolean(false);

    public void handleMessage(FileDownloadResponse message) throws IOException {
        handleDownloadResponse(message.getFileName());
        response.getOutputStream().write(message.getBytes());
    }

    public void handleError(String errorMessage) {
        completed = true;
        // If the download has started, flush outputStream
        if (downloadResponse.get()) {
            ThrowableUtil.safeExec(() -> response.getOutputStream().flush());
        } else {
            // returns error message
            ThrowableUtil.safeExec(() -> {
                response.getOutputStream().write(errorMessage.getBytes(StandardCharsets.UTF_8));
                response.getOutputStream().flush();
            });
        }
        latch.countDown();
    }

    public void handleFinish() {
        ThrowableUtil.safeExec(() -> {
            completed = true;
            response.getOutputStream().flush();
            latch.countDown();
        });
    }

    private void handleDownloadResponse(String fileName) {
        if (!downloadResponse.get()) {
            downloadResponse.compareAndSet(false, true);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.addHeader(HttpHeaderNames.CONTENT_DISPOSITION.toString(), "attachment;fileName=" + fileName);
        }
    }

    public boolean completed() {
        return this.completed;
    }

}
