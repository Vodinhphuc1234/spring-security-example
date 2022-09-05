package com.dinhphuc.learningspringsecurity.service;

import com.dinhphuc.learningspringsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    User saveUser(User user);
    User findUserByUsername (String username);
}
