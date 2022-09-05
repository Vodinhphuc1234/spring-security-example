package com.dinhphuc.learningspringsecurity.model;

import javax.persistence.*;

@Entity
@Table (name = "posts")
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Post(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }
}
