package com.reddit.clone.project.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.project.dto.SubRedditDto;
import com.reddit.clone.project.exception.SubredditNotFoundException;
import com.reddit.clone.project.mapper.SubRedditMapper;
import com.reddit.clone.project.model.SubReddit;
import com.reddit.clone.project.repository.SubredditRepository;
import com.reddit.clone.project.service.AuthService;
import com.reddit.clone.project.service.SubRedditService;
@Service
public class SubRedditServiceImpl implements SubRedditService {
	@Autowired
	SubredditRepository subredditRepository;
	@Autowired
	SubRedditMapper subRedditMapper;
	@Autowired
	AuthService authService;
	@Override
	@Transactional(readOnly = true)
	public List<SubRedditDto> getAll() {
		return subredditRepository.findAll().stream().
				map(subRedditMapper::mapSubredditToDto).collect(Collectors.toList());
	}
	
	private SubRedditDto mapToDto(SubReddit subReddit) {
		return SubRedditDto.builder().id(subReddit.getId()).name(subReddit.getName()).
				description(subReddit.getDescription()).numberOfPosts(subReddit.getPosts().size()).build();
	}

	@Override
	@Transactional(readOnly = true)
	public SubRedditDto getSubReddit(Long id) throws SubredditNotFoundException {
		SubReddit subReddit= subredditRepository.findById(id).orElseThrow(()->new SubredditNotFoundException("Subreddit not found with id -" + id));
		return subRedditMapper.mapSubredditToDto(subReddit);
	}
	
	private SubReddit mapToSubReddit(SubRedditDto subredditDto) {
		return SubReddit.builder().name(subredditDto.getName())
		.description(subredditDto.getDescription())
		.user(authService.getCurrentUser())
		.createdDate(Instant.now()).build();
	}

	@Override
	@Transactional
	public SubRedditDto save(@Valid SubRedditDto subredditDto) {
		SubReddit subReddit=subredditRepository.save
				(subRedditMapper.mapDtoToSubreddit(subredditDto));
//				(mapToSubReddit(subredditDto));
		subredditDto.setId(subReddit.getId());
        return subredditDto;
	}
}
