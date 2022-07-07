package com.reddit.clone.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.project.dto.PostRequest;
import com.reddit.clone.project.dto.PostResponse;
import com.reddit.clone.project.exception.PostNotFoundException;
import com.reddit.clone.project.exception.SubredditNotFoundException;
import com.reddit.clone.project.mapper.PostMapper;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.SubReddit;
import com.reddit.clone.project.model.User;
import com.reddit.clone.project.repository.PostRepository;
import com.reddit.clone.project.repository.SubredditRepository;
import com.reddit.clone.project.repository.UserRepository;
import com.reddit.clone.project.service.AuthService;
import com.reddit.clone.project.service.PostsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostsService {

	@Autowired
	PostRepository postRepository;
	@Autowired
	SubredditRepository subredditRepository;
	@Autowired
	PostMapper postMapper;
	@Autowired
	AuthService authService;
	@Autowired
	UserRepository userRepository;

	@Override
	public void save(PostRequest postRequest) throws SubredditNotFoundException {
		SubReddit subReddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException("Subreddit not found"));
		postRepository.save(postMapper.postRequestToPost(postRequest, subReddit, authService.getCurrentUser()));

	}

	@Override
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		List<Post> postList = postRepository.findAll();
		log.info("Posts:{}",postList);
		return postList.stream().map(postMapper::postToPostResponse).collect(Collectors.toList());
		
	}

	@Override
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.postToPostResponse(post);

	}

	@Override
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long id) throws SubredditNotFoundException {
		SubReddit subReddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SubredditNotFoundException(id.toString()));
		return postRepository.findAllBySubreddit(subReddit).stream().map(postMapper::postToPostResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::postToPostResponse).collect(Collectors.toList());
	}

}
