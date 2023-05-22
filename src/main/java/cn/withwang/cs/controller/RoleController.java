package cn.withwang.cs.controller;

import cn.withwang.cs.dto.ResponseDto;
import cn.withwang.cs.entity.Role;
import cn.withwang.cs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseDto<List<Role>> getRoles() {
        List<Role> roleResponses = roleService.getRoles();
        return new ResponseDto<>(HttpStatus.OK, roleResponses, "获取角色列表成功", true);
    }
}
