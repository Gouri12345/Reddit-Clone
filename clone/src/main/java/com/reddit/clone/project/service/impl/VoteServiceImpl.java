package com.reddit.clone.project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.project.dto.VoteDto;
import com.reddit.clone.project.exception.PostNotFoundException;
import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.Vote;
import com.reddit.clone.project.model.VoteType;
import com.reddit.clone.project.repository.PostRepository;
import com.reddit.clone.project.repository.VoteRepository;
import com.reddit.clone.project.service.AuthService;
import com.reddit.clone.project.service.VoteService;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	PostRepository postRepository;
	@Autowired
	VoteRepository voteRepository;
	@Autowired
	AuthService authService;

	@Override
	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(voteDto.getPostId().toString()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
		}
		if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));

		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}
}
