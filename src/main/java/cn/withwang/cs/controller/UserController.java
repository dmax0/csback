package cn.withwang.cs.controller;

import cn.withwang.cs.dto.ResponseDto;
import cn.withwang.cs.dto.response.UserResponse;
import cn.withwang.cs.entity.User;

import cn.withwang.cs.service.UserService;
import cn.withwang.cs.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping
    public ResponseDto<List<UserResponse>> getUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {

        List<UserResponse> userResponses = userService.getUsers(page, size);
        return new ResponseDto<>(HttpStatus.OK, userResponses, "获取用户列表成功", true);
    }

    // 修改用户status
    @PutMapping("/{id}/status")
    public ResponseDto<UserResponse> updateUserStatus(@PathVariable int id, @RequestBody User user) {
        UserResponse userResponse = userService.updateUserStatus(id, user);
        return new ResponseDto<>(HttpStatus.OK, userResponse, "修改用户状态成功", true);
    }
}

