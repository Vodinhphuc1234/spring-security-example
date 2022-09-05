package com.dinhphuc.learningspringsecurity.repository;

import com.dinhphuc.learningspringsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
     public User findUsersByUsername (String username);
}
