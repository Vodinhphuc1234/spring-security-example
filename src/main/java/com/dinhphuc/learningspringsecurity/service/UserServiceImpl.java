package com.dinhphuc.learningspringsecurity.service;

import com.dinhphuc.learningspringsecurity.model.Role;
import com.dinhphuc.learningspringsecurity.model.User;
import com.dinhphuc.learningspringsecurity.repository.RoleRepository;
import com.dinhphuc.learningspringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsersByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User name is not found");
        } else {
            List<SimpleGrantedAuthority> roles = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
            org.springframework.security.core.userdetails.User retUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
            return retUser;
        }
    }


    @Override
    public User saveUser(User user) {
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            Role retRole = roleRepository.findRoleByName(role.getName());
            if (retRole != null) roles.add(retRole);
        });

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }


}
