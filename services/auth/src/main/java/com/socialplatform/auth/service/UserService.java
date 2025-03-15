package com.socialplatform.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.socialplatform.auth.model.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User createUser(String username, String email, String password);
    boolean validateCredentials(String username, String password);
    String generateToken(String username);
    User getCurrentUser();
}
