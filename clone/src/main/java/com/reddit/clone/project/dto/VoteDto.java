package com.reddit.clone.project.dto;

import com.reddit.clone.project.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

	private VoteType voteType;
	private Long postId;
	
}
