package cn.withwang.cs.controller;

import cn.withwang.cs.dto.ResponseDto;
import cn.withwang.cs.dto.response.LoginResponse;
import cn.withwang.cs.dto.response.UserResponse;

import cn.withwang.cs.dto.request.LoginRequest;
import cn.withwang.cs.entity.User;
import cn.withwang.cs.service.AuthService;
import cn.withwang.cs.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (token != null) {
            User user = userService.findByUsername(loginRequest.getUsername());
            UserResponse userResponse = new UserResponse();

            BeanUtils.copyProperties(user, userResponse);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserInfo(userResponse);
            loginResponse.setToken(token);


//            List<MenuItem> menuItems = menuService.getMenuItemsForUser(user);
//            loginResponse.setMenuItems(menuItems);

            return new ResponseDto<>(HttpStatus.OK, loginResponse, "登录成功", true);
        }

        return new ResponseDto<>(HttpStatus.UNAUTHORIZED, null, "用户名或密码错误", false);
    }


    /**
     * 获取用户信息
     * @param token 请求头中的token
     * @return 用户信息
     */
    @GetMapping("/info")
    public ResponseDto<UserResponse> info(@RequestHeader("Authorization") String token) {
        // 根据请求头中的token获取用户信息
        int userId = authService.getUserIdByToken(token);
        UserResponse userResponse = userService.getUserResponse(userId);
        if (userResponse == null) {
            return new ResponseDto<>(HttpStatus.UNAUTHORIZED, null, "获取用户信息失败", false);
        }


        return new ResponseDto<>(HttpStatus.OK, userResponse, "获取用户信息成功", true);

    }

    /**
     * 验证token是否过期
     * @param token token
     * @return true:过期 false:未过期
     */
    @GetMapping("/validateToken")
    public ResponseDto<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        boolean isExpired = authService.validateToken(token);
        if (isExpired) {
            return new ResponseDto<>(HttpStatus.UNAUTHORIZED, false, "token已过期", false);
        }else {
            return new ResponseDto<>(HttpStatus.OK, true, "验证成功", true);
        }
    }
}
