package com.typing.dao.Post;

import java.sql.SQLException;
import java.util.List;

import com.typing.model.dto.PostDTO;

public interface PostDAO {
	
	String insert = "INSERT INTO posts(user_id, title, content, created_at, view_count, comment_count) VALUES (?,?,?,NOW(),0,0)";
	String selectAll =  "SELECT "
			  + "p.post_id, p.user_id, p.title, p.content, p.created_at,"
			  + "p.view_count, p.comment_count, u.uid "
			  + "FROM posts p "
			  + "JOIN users u ON p.user_id = u.user_id "
			  + "ORDER BY p.created_at DESC";
	
	String selectById = "SELECT "
			  + "p.post_id, p.user_id, p.title, p.content, p.created_at, "
			  + "u.uid , p.view_count, p.comment_count "
			  + "FROM posts p "
			  + "JOIN users u ON p.user_id = u.user_id "
			  + "WHERE p.post_id = ? ";
	
	String increaseView = "UPDATE Posts SET view_count = view_count + 1 "
			+ "WHERE post_id = ? ";
	
	String increaseComment = "UPDATE Posts SET comment_count = comment_count + 1 "
			+ "WHERE post_id = ? ";
	
    // 게시글 목록을 시간순으로 가져오기
    List<PostDTO> getPostsByTime();

    // 게시글 ID로 게시글 가져오기
    PostDTO getPostById(int postId);

    // 게시글 작성
    int createPost(PostDTO postDTO);
    
    void increaseViewCount(int postId);
    
    void incraeaseCommentCount(int postId);
}
