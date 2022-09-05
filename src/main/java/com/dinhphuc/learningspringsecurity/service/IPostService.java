package com.dinhphuc.learningspringsecurity.service;

import com.dinhphuc.learningspringsecurity.model.Post;

import java.util.Optional;

public interface IPostService {
    Optional<Post> getPost(long id);
}
