package com.tutelary.api;

import com.tutelary.bean.domain.CommandTaskCreate;
import com.tutelary.bean.req.CommandCreateRequest;
import com.tutelary.common.bean.R;
import com.tutelary.service.CommandService;
import io.github.linpeilie.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/command")
public class CommandAdapter {

    private CommandService commandService;

    private Converter objectMapper;

    @PostMapping("/createCommand")
    public R createThreadListCommand(@RequestBody CommandCreateRequest request) {
        final CommandTaskCreate commandTaskCreate = objectMapper.convert(request, CommandTaskCreate.class);
        commandService.createCommand(commandTaskCreate);
        return R.success();
    }

    @Autowired
    public void setCommandService(final CommandService commandService) {
        this.commandService = commandService;
    }

    @Autowired
    public void setObjectMapper(final Converter objectMapper) {
        this.objectMapper = objectMapper;
    }
}
