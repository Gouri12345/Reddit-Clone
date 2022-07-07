package com.reddit.clone.project.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.clone.project.dto.SubRedditDto;
import com.reddit.clone.project.model.Post;
import com.reddit.clone.project.model.SubReddit;
@Mapper(componentModel = "spring")
public interface SubRedditMapper {
	 @Mapping(expression = "java(mapPosts(subReddit.getPosts()))",target = "numberOfPosts")
	    SubRedditDto mapSubredditToDto(SubReddit subReddit);

	    default Integer mapPosts(List<Post> numberOfPosts) {
	        return numberOfPosts.size();
	    }

	    @InheritInverseConfiguration
	    @Mapping(target = "posts", ignore = true)
	    SubReddit mapDtoToSubreddit(SubRedditDto subReddit);
}
