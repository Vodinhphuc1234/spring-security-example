package com.dinhphuc.learningspringsecurity.repository;

import com.dinhphuc.learningspringsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;

@Component
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName (String name);
}
