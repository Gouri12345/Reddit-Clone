package com.reddit.clone.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.reddit.clone.project.dto.PostRequest;
import com.reddit.clone.project.dto.PostResponse;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.SubReddit;
import com.reddit.clone.project.model.User;
import com.reddit.clone.project.repository.CommentRepository;
import com.reddit.clone.project.repository.VoteRepository;
import com.reddit.clone.project.service.AuthService;

import com.github.marlonlom.utilities.timeago.TimeAgo;
@Mapper(componentModel = "spring")
public abstract class PostMapper {
	@Autowired
	CommentRepository commentRepository;
//	@Autowired
//	private VoteRepository voteRepository;
//	@Autowired
//	private AuthService authService;

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "voteCount", constant = "0")
	public abstract Post postRequestToPost(PostRequest postRequest, SubReddit subreddit, User user);

	@Mapping(target = "id", source = "post.postId")
	@Mapping(target = "postName", source = "post.postName")
	@Mapping(target = "description", source = "post.description")
	@Mapping(target = "url", source = "post.url")
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.userName")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponse postToPostResponse(Post post);

	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
