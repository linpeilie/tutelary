package com.tutelary.api;

import cn.dev33.satoken.stp.StpUtil;
import com.tutelary.bean.req.AuthenticationRequest;
import com.tutelary.common.bean.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AutoAdapter {

    @PostMapping("/login")
    public R login(@RequestBody AuthenticationRequest authenticationRequest) {
        if ("admin".equals(authenticationRequest.getUsername()) && "123456".equals(authenticationRequest.getPassword())) {
            StpUtil.login(authenticationRequest.getUsername());
            return R.success();
        }

        return R.failure("用户名或者密码错误");
    }

}
