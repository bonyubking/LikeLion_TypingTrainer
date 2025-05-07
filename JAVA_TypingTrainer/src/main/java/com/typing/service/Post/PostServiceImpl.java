package com.typing.service.Post;

import java.util.List;

import com.typing.dao.Chat.ChatDao;
import com.typing.dao.Chat.ChatDaoImpl;
import com.typing.dao.Post.PostDAO;
import com.typing.dao.Post.PostDAOImpl;
import com.typing.model.dto.PostDTO;

public class PostServiceImpl implements PostService {
	
	private final PostDAO postDAO = new PostDAOImpl();
	
	@Override
	public List<PostDTO> getPostsByTime() {
		
		List<PostDTO> posts = postDAO.getPostsByTime(); 
        
		return posts;
	}

	@Override
	public PostDTO getPostById(int postId) {
		
		postDAO.increaseViewCount(postId);
		PostDTO post = postDAO.getPostById(postId);
		
		return post;
	}

    @Override
    public int createPost(PostDTO postDTO) {
        
    	int result = postDAO.createPost(postDTO);  
        
    	return result;
    }

	@Override
	public void increaseViewCount(int postId) {
		
		postDAO.increaseViewCount(postId);
	}

}
