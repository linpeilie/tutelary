package com.tutelary.api;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.req.CommandCreateRequest;
import com.tutelary.bean.resp.CommandTaskResponse;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.bean.R;
import com.tutelary.constants.CommandConstants;
import com.tutelary.message.command.param.HeapDumpRequest;
import com.tutelary.message.command.param.StackRequest;
import com.tutelary.message.command.param.ThreadDetailRequest;
import com.tutelary.message.command.param.ThreadListRequest;
import com.tutelary.message.command.param.TraceRequest;
import com.tutelary.service.CommandService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @ApiResponse(description = "创建命令任务结果",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createThreadListCommand(@RequestBody CommandCreateRequest<ThreadListRequest> request) {
        return createCommand(CommandConstants.threadList, request.getInstanceId(), request.getParam());
    }

    @PostMapping("threadDetail")
    @Operation(summary = "线程详情命令", description = "创建线程详情命令")
    @ApiResponse(description = "创建命令任务结果",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createThreadDetailCommand(@RequestBody CommandCreateRequest<ThreadDetailRequest> request) {
        return createCommand(CommandConstants.threadDetail, request.getInstanceId(), request.getParam());
    }

    @PostMapping("traceMethod")
    @Operation(summary = "追踪方法命令", description = "创建追踪方法命令")
    @ApiResponse(description = "创建命令任务结果",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createTraceMethodCommand(@RequestBody CommandCreateRequest<TraceRequest> request) {
        return createCommand(CommandConstants.traceMethod, request.getInstanceId(), request.getParam());
    }

    @PostMapping("stackMethod")
    @Operation(summary = "方法执行堆栈信息", description = "创建打印方法执行堆栈信息命令")
    @ApiResponse(description = "创建命令任务结果",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createStackMethodCommand(@RequestBody CommandCreateRequest<StackRequest> request) {
        return createCommand(CommandConstants.stackMethod, request.getInstanceId(), request.getParam());
    }

    @PostMapping("heapDump")
    @Operation(summary = "实例HeapDump", description = "创建实例HeapDump命令")
    @ApiResponse(description = "创建命令任务结果",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CommandTaskResponse.class)))
    public R<CommandTaskResponse> createHeapDumpCommand(@RequestBody CommandCreateRequest<HeapDumpRequest> request) {
        return createCommand(CommandConstants.heapDump, request.getInstanceId(), request.getParam());
    }

}
