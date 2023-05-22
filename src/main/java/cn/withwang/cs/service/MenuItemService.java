package cn.withwang.cs.service;

import cn.withwang.cs.dto.response.MenuItem;
import cn.withwang.cs.entity.Permission;
import cn.withwang.cs.entity.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuItemService {
    private final PermissionService permissionService;

    public MenuItemService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public List<MenuItem> getMenusForUser(User user) {
        List<Permission> permissions = permissionService.getPermissionsForUser(user);
        Set<Permission> uniquePermissions = new HashSet<>(permissions);

        Set<Integer> childPermissionIds = new HashSet<>();
        for (Permission permission : uniquePermissions) {
            List<Permission> childPermissions = permissionService.getPermissionsByParentId(permission.getId());
            for (Permission childPermission : childPermissions) {
                childPermissionIds.add(childPermission.getId());
            }
        }

        List<MenuItem> menus = permissions.stream()
                .filter(permission -> permission.getParentId() == 0 && "menu".equals(permission.getType()) && !childPermissionIds.contains(permission.getId()))
                .distinct()
                .sorted(Comparator.comparingInt(Permission::getSort))
                .map(permission -> permissionToMenuItem(permission, ""))
                .collect(Collectors.toList());

        return menus;
    }

    private MenuItem permissionToMenuItem(Permission permission, String parentPath) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(permission.getName());
        menuItem.setIcon(permission.getIcon());
        menuItem.setComponent(permission.getComponent());


        String fullPath = parentPath +"/"+ permission.getPath();
        menuItem.setPath(fullPath);
        menuItem.setKey(fullPath);

        List<Permission> childPermissions = permissionService.getPermissionsByParentId(permission.getId());
        if (childPermissions != null && !childPermissions.isEmpty()) {
            List<MenuItem> childMenuItems = childPermissions.stream()
                    .filter(childPermission -> "menu".equals(childPermission.getType()))
                    .distinct()
                    .sorted(Comparator.comparingInt(Permission::getSort))
                    .map(childPermission -> permissionToMenuItem(childPermission, fullPath))
                    .collect(Collectors.toList());
            if (!childMenuItems.isEmpty()) {
                menuItem.setChildren(childMenuItems);
            }
        }

        return menuItem;
    }


}

