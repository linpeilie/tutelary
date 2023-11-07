package com.tutelary.api;

import cn.hutool.core.lang.UUID;
import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.req.CommandCreateRequest;
import com.tutelary.bean.resp.CommandTaskResponse;
import com.tutelary.command.store.FileDownloadMetadata;
import com.tutelary.command.store.FileDownloadStore;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.bean.R;
import com.tutelary.constants.CommandConstants;
import com.tutelary.message.command.param.DecompileRequest;
import com.tutelary.message.command.param.FileDownloadRequest;
import com.tutelary.message.command.param.FileListRequest;
import com.tutelary.message.command.param.GetStaticRequest;
import com.tutelary.message.command.param.HeapDumpRequest;
import com.tutelary.message.command.param.LoggerInfoRequest;
import com.tutelary.message.command.param.StackRequest;
import com.tutelary.message.command.param.ThreadDetailRequest;
import com.tutelary.message.command.param.ThreadListRequest;
import com.tutelary.message.command.param.TraceRequest;
import com.tutelary.message.command.param.UpdateLoggerLevelRequest;
import com.tutelary.message.command.param.VmOptionRequest;
import com.tutelary.service.CommandService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command/creation")
@Tag(name = "CommandCreationController", description = "命令创建接口")
public class CommandCreationController {

    private final CommandService commandService;

    private final Converter converter;

    private R<CommandTaskResponse> createCommand(Integer commandCode, String instanceId, CommandRequest param) {
        final CommandTask commandTask = commandService.createCommand(commandCode, instanceId, param);
        return R.success(converter.convert(commandTask, CommandTaskResponse.class));
    }

    @PostMapping("threadList")
    @Operation(summary = "线程列表命令", description = "创建线程列表命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createThreadListCommand(@RequestBody CommandCreateRequest<ThreadListRequest> request) {
        return createCommand(CommandConstants.threadList, request.getInstanceId(), request.getParam());
    }

    @PostMapping("threadDetail")
    @Operation(summary = "线程详情命令", description = "创建线程详情命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createThreadDetailCommand(@RequestBody CommandCreateRequest<ThreadDetailRequest> request) {
        return createCommand(CommandConstants.threadDetail, request.getInstanceId(), request.getParam());
    }

    @PostMapping("traceMethod")
    @Operation(summary = "追踪方法命令", description = "创建追踪方法命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createTraceMethodCommand(@RequestBody CommandCreateRequest<TraceRequest> request) {
        return createCommand(CommandConstants.traceMethod, request.getInstanceId(), request.getParam());
    }

    @PostMapping("stackMethod")
    @Operation(summary = "方法执行堆栈信息", description = "创建打印方法执行堆栈信息命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createStackMethodCommand(@RequestBody CommandCreateRequest<StackRequest> request) {
        return createCommand(CommandConstants.stackMethod, request.getInstanceId(), request.getParam());
    }

    @PostMapping("heapDump")
    @Operation(summary = "实例HeapDump", description = "创建实例HeapDump命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createHeapDumpCommand(@RequestBody CommandCreateRequest<HeapDumpRequest> request) {
        return createCommand(CommandConstants.heapDump, request.getInstanceId(), request.getParam());
    }

    @PostMapping("fileList")
    @Operation(summary = "文件列表", description = "创建查看文件列表的命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createFileListCommand(@RequestBody CommandCreateRequest<FileListRequest> request) {
        return createCommand(CommandConstants.fileList, request.getInstanceId(), request.getParam());
    }

    @PostMapping("fileDownload")
    @Operation(summary = "下载文件", description = "创建下载文件命令")
    public void createFileDownloadCommand(@RequestBody CommandCreateRequest<FileDownloadRequest> request,
        HttpServletResponse response) {
        try {
            final FileDownloadRequest param = request.getParam();
            final String identifier = UUID.fastUUID().toString(true);
            param.setIdentifier(identifier);
            final CountDownLatch latch = new CountDownLatch(1);
            final FileDownloadMetadata fileDownloadMetadata = new FileDownloadMetadata(response, latch);
            FileDownloadStore.add(identifier, fileDownloadMetadata);
            commandService.createCommand(CommandConstants.fileDownload, request.getInstanceId(), param);
            latch.await(10, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("download file occur error, param : {}", request.getParam());
        }
    }

    @PostMapping("decompile")
    @Operation(summary = "反编译类", description = "创建反编译类命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createDecompileCommand(@RequestBody CommandCreateRequest<DecompileRequest> request) {
        return createCommand(CommandConstants.decompile, request.getInstanceId(), request.getParam());
    }

    @PostMapping("loggerInfo")
    @Operation(summary = "Logger", description = "创建Logger信息命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createLoggerInfoCommand(@RequestBody CommandCreateRequest<LoggerInfoRequest> request) {
        return createCommand(CommandConstants.loggerInfo, request.getInstanceId(), request.getParam());
    }

    @PostMapping("updateLoggerLevel")
    @Operation(summary = "Logger", description = "创建更改日志级别命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createLoggerCommand(@RequestBody CommandCreateRequest<UpdateLoggerLevelRequest> request) {
        return createCommand(CommandConstants.updateLoggerLevel, request.getInstanceId(), request.getParam());
    }

    @PostMapping("getStatic")
    @Operation(summary = "GetStatic", description = "创建获取静态属性值命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createGetStaticCommand(@RequestBody CommandCreateRequest<GetStaticRequest> request) {
        return createCommand(CommandConstants.getStatic, request.getInstanceId(), request.getParam());
    }

    @PostMapping("getVmOption")
    @Operation(summary = "GetVmOption", description = "创建获取静态属性值命令")
    @ApiResponse(description = "创建命令任务结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createGetVmOptionCommand(@RequestBody CommandCreateRequest<VmOptionRequest> request) {
        return createCommand(CommandConstants.getVmOption, request.getInstanceId(), request.getParam());
    }

}
