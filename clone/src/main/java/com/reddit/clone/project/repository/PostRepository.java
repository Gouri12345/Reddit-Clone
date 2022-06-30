package com.reddit.clone.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.SubReddit;
import com.reddit.clone.project.model.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(SubReddit subreddit);

    List<Post> findByUser(User user);
}