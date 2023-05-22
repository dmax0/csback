package cn.withwang.cs.service;

import cn.withwang.cs.dto.response.UserResponse;
import cn.withwang.cs.entity.Role;
import cn.withwang.cs.entity.User;
import cn.withwang.cs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public User findById(int id) {
        return userMapper.selectById(id);
    }

    public boolean isUsernameExist(String username) {
        return userMapper.selectByUsername(username) != null;
    }
    public boolean isEmailExist(String email) {
        return userMapper.selectByEmail(email) != null;
    }
    @Transactional
    public void createUser(User user, ArrayList<Integer> rolesIdArray) {
        try {
            if (isUsernameExist(user.getUsername())) {
                throw new RuntimeException("用户名已存在");
            }else if (isEmailExist(user.getEmail())) {
                throw new RuntimeException("邮箱已存在");
            }
            userMapper.insert(user);
            userMapper.insertRoles(user.getId(), rolesIdArray);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建用户失败", e);
        }
    }
    public List<Role> getRolesByUserId(int id) {
        return roleService.getRolesByUserId(id);
    }

    public UserResponse getUserResponse(int userId) {
        User user = userMapper.selectById(userId);
        List<Role> roles = roleService.getRolesByUserId(userId);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setStatus(user.getStatus());
        userResponse.setNickname(user.getNickname());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setGender(user.getGender());
        userResponse.setBirthday(user.getBirthday());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setRoles(roles);



        return userResponse;
    }


    /**
     * 获取用户列表
     * @param page 页码
     * @param limit 每页数量
     * @return 用户列表
     */
    public List<UserResponse> getUsers(int page, int limit) {
        List<User> users = userMapper.selectByPage(page, limit);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = getUserResponse(user.getId());
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    public UserResponse updateUserStatus(int id, User user) {
        try {
            // 先检查用户是否存在
            User userInDb = userMapper.selectById(id);
            if (userInDb == null) {
                throw new RuntimeException("用户不存在");
            }
            // 修改用户状态
            userMapper.updateStatus(id, user.getStatus());
            return getUserResponse(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改用户状态失败", e);
        }
    }
//    public List<User> getUsers(int page, int limit) {
//        return userMapper.selectByPage(page, limit);
//    }
}
