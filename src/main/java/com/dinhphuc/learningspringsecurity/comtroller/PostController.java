package com.dinhphuc.learningspringsecurity.comtroller;

import com.dinhphuc.learningspringsecurity.model.Post;
import com.dinhphuc.learningspringsecurity.objects.ResponseObject;
import com.dinhphuc.learningspringsecurity.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/post/")
public class PostController {
    @Autowired
    IPostService postService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getPostController(@PathVariable("id") long id) {
        Optional<Post> post = postService.getPost(id);

        System.out.println(id);

        ResponseObject responseObject = new ResponseObject();
        responseObject.setObject(post);
        if (post.isEmpty()) {
            responseObject.setMessage("Not found");
            responseObject.setError(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        } else {
            responseObject.setMessage("Found");
            responseObject.setError(HttpStatus.OK.value());
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }
}
