package com.reddit.clone.project.service;

import java.util.List;

import com.reddit.clone.project.dto.CommentsDto;

public interface CommentsService {

	void createComment(CommentsDto commentsDto);

	List<CommentsDto> getCommentByPost(Long postId);

	List<CommentsDto> getCommentsByUser(String userName);

}
