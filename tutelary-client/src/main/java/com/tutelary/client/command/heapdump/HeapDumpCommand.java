package com.tutelary.client.command.heapdump;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.tutelary.client.command.Command;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.client.core.file.FileManager;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.param.HeapDumpRequest;
import com.tutelary.message.command.result.HeapDumpResponse;
import java.io.File;
import java.util.Date;

public class HeapDumpCommand implements Command<HeapDumpResponse> {

    private static final Log LOG = LogFactory.get(HeapDumpCommand.class);

    private final HeapDumpRequest request;

    public HeapDumpCommand(final HeapDumpRequest request) {
        this.request = request;
    }

    @Override
    public HeapDumpResponse execute() {
        final String dumpFolder = FileManager.heapDumpFolder();
        final File dumpFolderFile = new File(dumpFolder);
        final HeapDumpResponse heapDumpResponse = new HeapDumpResponse();
        if (!dumpFolderFile.exists()) {
            if (dumpFolderFile.mkdir()) {
                LOG.debug("mkdir heap dump folder : {}", dumpFolder);
            } else {
                heapDumpResponse.failed("mkdir heap dump folder failure, folder path : " + dumpFolder);
                return heapDumpResponse;
            }
        }
        String dumpFile = dumpFolder + File.separator + "heapdump-"
                          + DateUtil.format(new Date(), "yyyyMMddHHmmss") + "-"
                          + UUID.randomUUID().toString(true) + (request.isLive() ? "-live" : "") + ".hprof";
        LOG.debug("heap heap dump file : {}", dumpFile);
        try {
            final HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.getHotSpotDiagnosticMXBean();
            hotSpotDiagnosticMXBean.dumpHeap(dumpFile, request.isLive());
            heapDumpResponse.setDumpFile(dumpFile);
        } catch (Throwable t) {
            LOG.error("heap dump error : ", t);
            heapDumpResponse.failed("heap dump error [ " + t.getMessage() + "]");
        }
        return heapDumpResponse;
    }

    @Override
    public void terminated() {

    }
}
