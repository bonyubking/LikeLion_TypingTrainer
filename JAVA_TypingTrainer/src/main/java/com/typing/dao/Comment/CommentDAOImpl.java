package com.typing.dao.Comment;

import static com.typing.util.DBUtil.sharedConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.typing.model.dto.CommentDTO;
import com.typing.model.dto.PostDTO;

public class CommentDAOImpl implements CommentDAO {

	@Override
	public List<CommentDTO> selectCommentsByPostId(int postId) {
        
	    Connection conn = sharedConnection();
	    List<CommentDTO> commentList = new ArrayList<>();
		
		try (PreparedStatement stmt = conn.prepareStatement(selectAll)) {
            stmt.setInt(1, postId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CommentDTO dto = new CommentDTO();
                    dto.setCommentId(rs.getInt("comment_id"));
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setUid(rs.getString("uid"));
                    dto.setNickname(rs.getString("nickname"));
                    dto.setPostId(rs.getInt("post_id"));
                    dto.setContent(rs.getString("content"));
                    dto.setCreatedAt(rs.getDate("created_at"));
                    commentList.add(dto);
                }
            }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
                return commentList;
            }


	@Override
	public int createComment(CommentDTO comment) {
		   Connection conn = sharedConnection();
		   int result = 0;
		   try {
		        
		        try (PreparedStatement pstmt = conn.prepareStatement(insert)) {

		            
		            pstmt.setInt(1, comment.getUserId());
		            pstmt.setString(2, comment.getContent());
		            pstmt.setInt(3, comment.getPostId());

		            result = pstmt.executeUpdate();
		            if (result > 0) {
		                conn.commit();
		            } else {
		                conn.rollback();
		            }
		        }
		    } catch (SQLException e) {
		    	
		        try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
		        e.printStackTrace();
		    }
		    return result;
		}
	}
		
		

	

	
