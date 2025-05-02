package com.typing.dao.Post;

import static com.typing.util.DBUtil.sharedConnection;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.typing.model.dto.PostDTO;

public class PostDAOImpl implements PostDAO {
	


	@Override
	public List<PostDTO> getPostsByTime() {
	    Connection conn = sharedConnection();
	    List<PostDTO> postList = new ArrayList<>();

	    try (PreparedStatement pstmt = conn.prepareStatement(selectAll);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            PostDTO postDTO = new PostDTO();
	            postDTO.setPostId(rs.getInt("post_id"));
	            postDTO.setUserId(rs.getInt("user_id"));
	            postDTO.setUid(rs.getString("uid"));          
	            postDTO.setTitle(rs.getString("title"));
	            postDTO.setContent(rs.getString("content"));
	            postDTO.setCreatedAt(rs.getDate("created_at"));
	            postList.add(postDTO);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return postList;
	}

	@Override
	public PostDTO getPostById(int postId) {
	    Connection conn = sharedConnection();
	    PostDTO post = null;

	    try (PreparedStatement pstmt = conn.prepareStatement(selectById)) {
	        pstmt.setInt(1, postId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                post = new PostDTO();
	                post.setPostId(   rs.getInt("post_id")   );
	                post.setUserId(   rs.getInt("user_id")   );
	                post.setUid(      rs.getString("uid")    );    
	                post.setTitle(    rs.getString("title")  );
	                post.setContent(  rs.getString("content"));
	                post.setCreatedAt(rs.getDate("created_at"));	            
	                }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return post;
	}
	
	@Override
	public int createPost(PostDTO post) {
	    Connection conn = sharedConnection();
	    int result = 0;
	    try {
	        
	        try (PreparedStatement pstmt = conn.prepareStatement(insert)) {

	            
	            pstmt.setInt(1, post.getUserId());
	            pstmt.setString(2, post.getTitle());
	            pstmt.setString(3, post.getContent());

	            result = pstmt.executeUpdate();
	            if (result > 0) {
	                conn.commit();
	            } else {
	                conn.rollback();
	            }
	        }
	    } catch (SQLException e) {
	        // 4) 예외 시 반드시 롤백 처리
	        try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        e.printStackTrace();
	    }
	    return result;
	}


}
