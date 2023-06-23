package com.tutelary.api;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.CommandTaskCreate;
import com.tutelary.bean.req.CommandCreateRequest;
import com.tutelary.bean.resp.CommandTaskResponse;
import com.tutelary.common.bean.R;
import com.tutelary.service.CommandService;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command")
public class CommandController {

    private final CommandService commandService;

    private final Converter converter;

    @PostMapping("/createCommand")
    public R<CommandTaskResponse> createThreadListCommand(@RequestBody CommandCreateRequest request) {
        final CommandTaskCreate commandTaskCreate = converter.convert(request, CommandTaskCreate.class);
        final CommandTask commandTask = commandService.createCommand(commandTaskCreate);
        return R.success(converter.convert(commandTask, CommandTaskResponse.class));
    }

}
