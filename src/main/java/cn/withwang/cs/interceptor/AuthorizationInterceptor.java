package cn.withwang.cs.interceptor;

import cn.withwang.cs.entity.Permission;
import cn.withwang.cs.entity.Role;
import cn.withwang.cs.entity.User;
import cn.withwang.cs.service.AuthService;
import cn.withwang.cs.service.PermissionService;
import cn.withwang.cs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final List<String> WHITE_LIST = List.of("/auth", "/error", "/menu", "/upload", "/download", "/file");


    private final AuthService authService;
    private final UserService userService;
    private final PermissionService permissionService;

    public AuthorizationInterceptor(AuthService authService, UserService userService, PermissionService permissionService) {
        this.authService = authService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println(request.getRequestURI());

        if (isRequestInWhiteList(request) || isUserAuthorized(request, response)) {
            return true;
        }

        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not authorized");
        }

        return false;
    }

    private boolean isRequestInWhiteList(HttpServletRequest request) {
        return WHITE_LIST.stream().anyMatch(request.getRequestURI()::startsWith);
    }

    private boolean isUserAuthorized(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未登录");
            return false;
        }

        int userId;
        try {
            userId = authService.getUserIdByToken(token);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未登录");
            return false;
        }

        User user = userService.findById(userId);
        if (user == null || !isUserGrantedPermissions(user, request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户不存在");
            return false;
        }

        return true;
    }

    private boolean isUserGrantedPermissions(User user, HttpServletRequest request) {
        List<Role> roles = userService.getRolesByUserId(user.getId());

        for (Role role : roles) {
            List<Permission> permissions = permissionService.getPermissionsByRoleId(role.getId());
            if (isPermissionMatchesRequestedResource(permissions, request)) {
                return true;
            }
        }

        return false;
    }

    private boolean isPermissionMatchesRequestedResource(List<Permission> permissions, HttpServletRequest request) {
        for (Permission permission : permissions) {
            if (permissionMatchesRequestedResource(permission, request)) {
                return true;
            }
        }

        return false;
    }


    private boolean permissionMatchesRequestedResource(Permission permission, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String path = permission.getPath();
        return requestURI.startsWith(path);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
