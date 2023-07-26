package com.tutelary.command.store;

import com.tutelary.common.utils.ThrowableUtil;
import com.tutelary.message.command.result.FileDownloadResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
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

    private AtomicBoolean downloadResponse = new AtomicBoolean(false);

    public void handleMessage(FileDownloadResponse message) {
        try {
            handleDownloadResponse(message.getFileName());
            response.getOutputStream().write(message.getBytes());
        } catch (Exception e) {
            log.error("[handleMessage] download file occur error", e);
            handleError(e.getMessage());
            latch.countDown();
        }
    }

    public void handleError(String errorMessage) {
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
    }

    public void handleFinish() {
        ThrowableUtil.safeExec(() -> {
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

}
