package cn.withwang.cs.controller;

import cn.withwang.cs.dto.ResponseDto;
import cn.withwang.cs.dto.response.MenuItem;
import cn.withwang.cs.entity.User;
import cn.withwang.cs.service.AuthService;
import cn.withwang.cs.service.MenuItemService;
import cn.withwang.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ResponseDto<List<MenuItem>> list(@RequestHeader("Authorization") String token) {
        int id = authService.getUserIdByToken(token);
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseDto<>(HttpStatus.UNAUTHORIZED, null, "获取菜单失败", false);
        }
        List<MenuItem> menus = menuItemService.getMenusForUser(user);
        return new ResponseDto<>(HttpStatus.OK, menus, "获取菜单成功", true);
    }
}
