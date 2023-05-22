package cn.withwang.cs.service;

import cn.withwang.cs.entity.Permission;
import cn.withwang.cs.entity.Role;
import cn.withwang.cs.entity.User;
import cn.withwang.cs.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleService roleService;

    /**
     * @description 根据角色ID查找权限
     * @param id    角色ID
     * @return    权限列表
     */
    public List<Permission> getPermissionsByRoleId(int id) {
        return permissionMapper.selectPermissionsByRoleId(id);
    }

    /**
     * @description 根据用户查找权限
     * @param user    用户信息
     * @return    权限列表
     */
    public List<Permission> getPermissionsForUser(User user) {
        int userId = user.getId();
        List<Role> roles = roleService.getRolesByUserId(userId);

        return roles.stream()
                .map(Role::getId)
                .map(this::getPermissionsByRoleId)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @description 根据父ID查找权限
     * @param parentId   父ID
     * @return  权限列表
     */
    public List<Permission> getPermissionsByParentId(int parentId) {
        return permissionMapper.selectByParentId(parentId);
    }


}
