package com.typing.dao.Comment;

import java.util.List;

import com.typing.model.dto.CommentDTO;



public interface CommentDAO {
	
	String selectAll =  
            "SELECT c.comment_id, " +
            "c.user_id, " +
            "u.uid, " +
            "u.nickname, " +
            "c.post_id, " +
            "c.content, " +
            "c.created_at "+
       "FROM comments c "+
       "JOIN users u ON c.user_id = u.user_id "+
       "WHERE c.post_id = ? "+
      "ORDER BY c.created_at ASC ";
	
	String insert = "INSERT INTO comments(user_id, content, post_id, created_at) VALUES (?,?,?,NOW())";
 
	
	
	
	List<CommentDTO> selectCommentsByPostId(int postId);
    int createComment(CommentDTO commentDTO);
}
		
	

