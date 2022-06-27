package com.reddit.clone.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.clone.model.SubReddit;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findByName(String subredditName);
}