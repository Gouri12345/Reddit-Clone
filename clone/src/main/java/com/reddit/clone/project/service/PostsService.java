package com.reddit.clone.project.service;

import java.util.List;

import com.reddit.clone.project.dto.PostRequest;
import com.reddit.clone.project.dto.PostResponse;
import com.reddit.clone.project.exception.SubredditNotFoundException;

public interface PostsService {

	void save(PostRequest postRequest) throws SubredditNotFoundException;

	List<PostResponse> getAllPosts();

	PostResponse getPost(Long id);

	List<PostResponse> getPostsBySubreddit(Long id) throws SubredditNotFoundException;

	List<PostResponse> getPostsByUsername(String username);

}
