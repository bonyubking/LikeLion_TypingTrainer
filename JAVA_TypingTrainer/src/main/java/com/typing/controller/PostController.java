package com.typing.controller;

import java.util.List;

import com.typing.model.dto.PostDTO;
import com.typing.service.Post.PostService;
import com.typing.service.Post.PostServiceImpl;

public class PostController {
	private final PostService postService = new PostServiceImpl();
	
	public List<PostDTO> getPostsByTime(){
		
		return postService.getPostsByTime();
	}	
	
	public PostDTO getPostById(int postId) {
		
		return postService.getPostById(postId);
	}
	
	public int createPost(PostDTO postDTO) {
		
		return postService.createPost(postDTO);
	}

}
