package com.tutelary.client.command.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.tutelary.client.command.Command;
import com.tutelary.client.core.file.FileManager;
import com.tutelary.client.core.file.FileTypeEnum;
import com.tutelary.message.command.domain.FileInfo;
import com.tutelary.message.command.param.FileListRequest;
import com.tutelary.message.command.result.FileListResponse;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileListCommand implements Command<FileListResponse> {

    private final FileListRequest request;

    public FileListCommand(final FileListRequest request) {
        this.request = request;
    }

    @Override
    public FileListResponse execute() {
        final FileListResponse fileListResponse = new FileListResponse();
        String folder;
        if (request.getType() == FileTypeEnum.HEAP_DUMP.getType()) {
            folder = FileManager.dumpFolder();
        } else {
            fileListResponse.failed("unknown type");
            return fileListResponse;
        }
        File[] files = FileUtil.ls(folder);
        final int length = files.length;
        fileListResponse.setTotal(length);

        int from = Math.max((request.getPageIndex() - 1) * request.getPageSize(), 0);
        int to = Math.min(Math.max(request.getPageSize() * request.getPageIndex(), 0), length);

        if (length < from) {
            fileListResponse.setFileList(Collections.emptyList());
            return fileListResponse;
        }

        files = Arrays.copyOfRange(files, from, to);
        final List<FileInfo> fileList = Arrays.stream(files).map(file -> {
            final FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getName());
            fileInfo.setFilePath(file.getAbsolutePath());
            fileInfo.setFileSize(FileUtil.readableFileSize(file));
            fileInfo.setLastModifiedTime(new Date(file.lastModified()));
            return fileInfo;
        }).collect(Collectors.toList());
        fileListResponse.setFileList(fileList);
        return fileListResponse;
    }

    @Override
    public void terminated() {

    }
}
