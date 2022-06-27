package com.reddit.clone.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.clone.model.Post;
import com.reddit.clone.model.SubReddit;
import com.reddit.clone.model.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(SubReddit subreddit);

    List<Post> findByUser(User user);
}