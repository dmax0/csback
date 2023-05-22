package cn.withwang.cs.config;

import cn.withwang.cs.entity.Role;
import cn.withwang.cs.entity.User;
import cn.withwang.cs.service.RoleService;
import cn.withwang.cs.service.UserService;
//import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class DatabaseInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Value("${initial.root.admin.username}")
    private String initialUsername;
    @Value("${initial.root.admin.password}")
    private String initialPassword;

    @Autowired
    public DatabaseInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @description 初始化数据库
     *  创建超级管理员角色
     */
//    @PostConstruct
    public void initDatabase() {
        Role existRootRole = roleService.findByName("ROLE_ROOT_ADMIN");
        ArrayList<Integer> RolesIdArray = new ArrayList<>();
        if (existRootRole == null) {
            Role rootRole = new Role();
            rootRole.setName("ROLE_ROOT_ADMIN");
            rootRole.setLevel(1);
            ArrayList<Integer> permissions = new ArrayList<>();
            permissions.add(1);
            permissions.add(2);
            permissions.add(3);
            permissions.add(4);
            roleService.addRoleWithPermissions(rootRole, permissions);
            RolesIdArray.add(rootRole.getId());
        }

        User existUser = userService.findByUsername(initialUsername);
        if (existUser == null) {
            User user = new User();
            user.setUsername(initialUsername);
            user.setPassword(passwordEncoder.encode(initialPassword));
            userService.createUser(user, RolesIdArray);
        }
    }
}
