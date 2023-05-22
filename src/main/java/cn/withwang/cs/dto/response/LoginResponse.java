package cn.withwang.cs.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 登录操作的响应
 *
 * 包括用户信息和token
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private UserResponse userInfo;
    private String token;

    public UserResponse getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserResponse userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
