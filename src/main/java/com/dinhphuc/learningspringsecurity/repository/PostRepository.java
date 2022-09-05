package com.dinhphuc.learningspringsecurity.repository;

import com.dinhphuc.learningspringsecurity.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostRepository extends JpaRepository<Post, Long>{
}
