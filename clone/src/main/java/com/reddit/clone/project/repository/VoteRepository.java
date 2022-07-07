package com.reddit.clone.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.User;
import com.reddit.clone.project.model.Vote;
@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
