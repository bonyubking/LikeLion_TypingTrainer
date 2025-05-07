package com.typing.service.Comment;

import java.util.List;

import com.typing.dao.Comment.CommentDAO;
import com.typing.dao.Comment.CommentDAOImpl;
import com.typing.model.dto.CommentDTO;


public class CommentServiceImpl implements CommentService {
	
	private final CommentDAO CommentDAO = new CommentDAOImpl();
	

	@Override
	public List<CommentDTO> selectCommentsByPostId(int postId) {
		
		List<CommentDTO> comments = CommentDAO.selectCommentsByPostId(postId);
		
		return comments;
	}

	@Override
	public int createComment(CommentDTO commentDTO) {
    	
		int result = CommentDAO.createComment(commentDTO);  
        
    	return result;
	}

}
