package com.reddit.clone.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.project.dto.VoteDto;
import com.reddit.clone.project.service.VoteService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/vote")
@Slf4j
public class VoteController {

	@Autowired
	VoteService voteService;
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
		log.info("Vote details:{}",voteDto);
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
