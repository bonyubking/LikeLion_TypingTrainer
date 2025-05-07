package com.typing.controller;

import com.typing.service.Comment.CommentServiceImpl;

import java.util.List;

import com.typing.model.dto.CommentDTO;
import com.typing.service.Comment.CommentService;

public class CommentController {
	
	private final CommentService CommentService = new CommentServiceImpl();
	
    public List<CommentDTO> selectCommentsBypostId(int postid) {
        
    	return CommentService.selectCommentsByPostId(postid);
    }
    
    public int createComment(CommentDTO commentDTO) {
		
    	return CommentService.createComment(commentDTO);
    }
    
    public void increaseCommentCount(int postid) {
    	
    	CommentService.increaseCommentCount(postid);
    }
}
