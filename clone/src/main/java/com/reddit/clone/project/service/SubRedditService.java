package com.reddit.clone.project.service;

import java.util.List;

import javax.validation.Valid;

import com.reddit.clone.project.dto.SubRedditDto;
import com.reddit.clone.project.exception.SubredditNotFoundException;

public interface SubRedditService {

	List<SubRedditDto> getAll();

	SubRedditDto getSubReddit(Long id) throws SubredditNotFoundException;

	SubRedditDto save(@Valid SubRedditDto subredditDto);

}
