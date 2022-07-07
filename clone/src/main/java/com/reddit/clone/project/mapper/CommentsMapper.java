package com.reddit.clone.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.clone.project.dto.CommentsDto;
import com.reddit.clone.project.model.Comment;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.User;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
	
	@Mapping(target = "postId", source = "post.postId")
	@Mapping(target = "userName", source = "user.userName")
	CommentsDto commentsToDto(Comment comment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	@Mapping(target="user", source="user")
	Comment map(CommentsDto commentsDto, Post post, User user);
}
