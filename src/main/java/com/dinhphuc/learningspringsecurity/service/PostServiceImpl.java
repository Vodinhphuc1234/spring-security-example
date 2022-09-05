package com.dinhphuc.learningspringsecurity.service;

import com.dinhphuc.learningspringsecurity.model.Post;
import com.dinhphuc.learningspringsecurity.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService{
    @Autowired
    PostRepository postRepository;
    @Override
    public Optional<Post> getPost(long id) {
        return postRepository.findById(id);
    }
}
