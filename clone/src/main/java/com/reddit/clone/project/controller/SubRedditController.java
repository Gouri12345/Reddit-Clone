package com.reddit.clone.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.project.dto.SubRedditDto;
import com.reddit.clone.project.exception.SubredditNotFoundException;
import com.reddit.clone.project.model.SubReddit;
import com.reddit.clone.project.service.SubRedditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubRedditController {

	@Autowired
	SubRedditService subRedditService;
	
	@GetMapping
	public List<SubRedditDto> getAllSubReddits(){
		return subRedditService.getAll();
	}
	
	@GetMapping("/{id}")
	public SubRedditDto getSubReddit(@PathVariable Long id) throws SubredditNotFoundException {
		return subRedditService.getSubReddit(id);
	}
	
	@PostMapping
    public SubRedditDto create(@RequestBody @Valid SubRedditDto subredditDto) {
        return subRedditService.save(subredditDto);
    }
}
