package com.typing.service.Post;

import java.util.List;

import com.typing.model.dto.PostDTO;

public interface PostService {
	
	List<PostDTO> getPostsByTime();
	PostDTO getPostById(int postId);
	int createPost(PostDTO postDTO);
	void increaseViewCount(int postId);
}
