package com.reddit.clone.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.project.dto.CommentsDto;
import com.reddit.clone.project.exception.PostNotFoundException;
import com.reddit.clone.project.mapper.CommentsMapper;
import com.reddit.clone.project.model.Comment;
import com.reddit.clone.project.model.NotificationEmail;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.User;
import com.reddit.clone.project.repository.CommentRepository;
import com.reddit.clone.project.repository.PostRepository;
import com.reddit.clone.project.repository.UserRepository;
import com.reddit.clone.project.service.AuthService;
import com.reddit.clone.project.service.CommentsService;
import com.reddit.clone.project.service.MailService;
import com.reddit.clone.project.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CommentsMapper commentsMapper;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	MailContentBuilder mailContentBuilder;
	@Autowired
	MailService mailService;
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public void createComment(CommentsDto commentsDto) {
		Post post=postRepository.findById(commentsDto.getPostId()).orElseThrow(()->new PostNotFoundException(commentsDto.getPostId().toString()));
		User user=authService.getCurrentUser();
		log.info("Current user:{}",user.getUserName());
		Comment comment=commentRepository.save(commentsMapper.map(commentsDto, post, user));
		log.info("Comment created:{}",comment);
		  String message = mailContentBuilder.build(user.getUserName() + " posted a comment on your post." + Constants.POST_URL);
	        sendCommentNotification(message, post.getUser());
	}

	@Override
	@Transactional(readOnly=true)
	public List<CommentsDto> getCommentByPost(Long postId) {
		Post post=postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentsMapper::commentsToDto).collect(Collectors.toList());
	}

	@Override
	public List<CommentsDto> getCommentsByUser(String userName) {
		User user=userRepository.findByUserName(userName).orElseThrow(()->new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user).stream().map(commentsMapper::commentsToDto).collect(Collectors.toList());
	}
	 private void sendCommentNotification(String message, User user) {
	        mailService.sendMail(new NotificationEmail(user.getUserName() + " Commented on your post", user.getEmail(), message));
	    }
}
