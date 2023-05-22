package cn.withwang.cs.service;

import cn.withwang.cs.entity.User;
import cn.withwang.cs.mapper.UserMapper;
import cn.withwang.cs.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        if (user.getStatus() == 0) {
            return null;
        }
        return jwtUtils.generateToken(user.getId());
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    public Integer getUserIdByToken(String token) {
        Claims claims = jwtUtils.parseToken(token);
        if (claims == null) {
            return null;
        }
        try {
            return Integer.parseInt(claims.getSubject());
        } catch (NumberFormatException e) {

            return null;
        }
    }

    /**
     * 验证token是否过期
     * @param token token
     * @return true:过期 false:未过期
     */
    public boolean validateToken(String token) {
        return jwtUtils.isTokenExpired(token);
    }
}
