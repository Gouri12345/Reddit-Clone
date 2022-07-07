package com.reddit.clone.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.ResponseEntity.status;
import com.reddit.clone.project.dto.CommentsDto;
import com.reddit.clone.project.service.CommentsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	@Autowired
	CommentsService commentsService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
		commentsService.createComment(commentsDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/postId/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return status(HttpStatus.OK).body(commentsService.getCommentByPost(postId));
	}

	@GetMapping("/userName/{userName}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable String userName) {
		return status(HttpStatus.OK).body(commentsService.getCommentsByUser(userName));
	}
}
