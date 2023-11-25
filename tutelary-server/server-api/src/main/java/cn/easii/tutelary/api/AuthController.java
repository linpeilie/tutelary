package cn.easii.tutelary.api;

import cn.easii.tutelary.bean.req.LoginRequest;
import cn.easii.tutelary.bean.resp.LoginResponse;
import cn.easii.tutelary.bean.resp.UserInfoResponse;
import cn.easii.tutelary.common.bean.R;
import cn.easii.tutelary.service.UserService;
import cn.easii.tutelary.common.exception.NotLoginException;
import cn.easii.tutelary.service.AuthService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "AuthController", description = "认证相关接口")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final Converter converter;

    @PostMapping(value = "login")
    public R<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        final String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        final LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return R.success(loginResponse);
    }

    @PostMapping(value = "logout")
    public R<Void> logout() {
        authService.logout();
        return R.success();
    }

    @PostMapping(value = "notLogin")
    public R<Void> unAuthorized(HttpServletResponse response) throws IOException {
        throw new NotLoginException();
    }

}
