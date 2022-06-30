package com.reddit.clone.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.clone.project.model.Comment;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}