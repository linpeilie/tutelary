package cn.easii.tutelary.client.command.file;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.easii.tutelary.message.command.param.FileDownloadRequest;
import cn.easii.tutelary.message.command.result.FileDownloadResponse;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.util.concurrent.RateLimiter;
import cn.easii.tutelary.client.command.Command;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class FileDownloadCommand implements Command<FileDownloadResponse> {

    private static final Log LOG = LogFactory.get(FileDownloadCommand.class);

    private static final double DEFAULT_RATE_LIMITER_PERMITS_PER_SECOND = 1024 * 1024 * 10;

    private final FileDownloadRequest request;

    private final RateLimiter rateLimiter;

    private byte[] bytes;

    private InputStream inputStream;

    public FileDownloadCommand(final FileDownloadRequest request) {
        this.request = request;
        rateLimiter = RateLimiter.create(DEFAULT_RATE_LIMITER_PERMITS_PER_SECOND);
        bytes = new byte[4096];
    }

    @Override
    public FileDownloadResponse execute() {
        FileDownloadResponse response = new FileDownloadResponse();
        response.setIdentifier(request.getIdentifier());
        final File file = new File(request.getFilePath());
        if (!file.exists()) {
            LOG.error("download file , path : {} not exist", request.getFilePath());
            response.failed("file not exist");
            return response;
        }
        try {
            inputStream = ObjectUtil.defaultIfNull(inputStream, Files.newInputStream(file.toPath()));

            final int read = inputStream.read(bytes);

            response.setFileName(file.getName());
            response.setBytes(bytes);

            if (read == -1) {
                inputStream.close();
                response.setEnd(true);
                return response;
            }

            if (read > 0) {
                rateLimiter.acquire(read);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return response;
    }

    @Override
    public void terminated() {
        if (inputStream != null) {
            ThrowableUtil.safeExec(inputStream::close);
        }
    }
}
