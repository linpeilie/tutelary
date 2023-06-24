package com.tutelary.api;

import com.tutelary.bean.req.LoginRequest;
import com.tutelary.bean.resp.LoginResponse;
import com.tutelary.common.bean.R;
import com.tutelary.service.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@Tag(name = "AuthController", description = "认证相关接口")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping(value = "login")
    public R<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        final String token = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        final LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return R.success(loginResponse);
    }

}
