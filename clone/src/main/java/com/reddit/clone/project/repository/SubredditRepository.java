package com.reddit.clone.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.clone.project.model.SubReddit;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findByName(String subredditName);
}