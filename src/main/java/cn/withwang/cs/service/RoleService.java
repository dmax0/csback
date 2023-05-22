package cn.withwang.cs.service;

import cn.withwang.cs.entity.Role;
import cn.withwang.cs.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * @description 根据角色名查找角色
     * @param role_admin 角色名（英文名）
     * @return Role 角色信息
     */
    public Role findByName(String role_admin) {
        return roleMapper.selectByName(role_admin);
    }

    /**
     * @description 添加角色并关联权限
     * @param role 新的角色信息
     * @param permissions 权限ID列表
     */
    @Transactional
    public void addRoleWithPermissions(Role role, List<Integer> permissions) {
        roleMapper.insert(role);
        roleMapper.insertRolePermissions(role.getId(), permissions);
    }

    public List<Role> getRolesByUserId(int userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    public List<Role> getRoles() {
        return roleMapper.selectAllRoles();
    }
}
