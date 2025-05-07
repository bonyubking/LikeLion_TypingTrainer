package com.typing.service.Comment;

import java.util.List;

import com.typing.model.dto.CommentDTO;

public interface CommentService {
	
	List<CommentDTO> selectCommentsByPostId(int postId);
    int createComment(CommentDTO commentDTO);

}
