package com.tutelary.controller;

import com.tutelary.bean.api.req.CommandApiRequest;
import com.tutelary.common.bean.api.R;
import com.tutelary.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/command")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/createCommand")
    public R createThreadListCommand(@RequestBody CommandApiRequest request) {
        log.info("param : {}", request);
        commandService.createCommand(request);
        return R.success();
    }

}
