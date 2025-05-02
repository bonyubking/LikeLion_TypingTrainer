package com.typing.dao.Post;

import java.sql.SQLException;
import java.util.List;

import com.typing.model.dto.PostDTO;

public interface PostDAO {
	
	String insert = "INSERT INTO posts(user_id, title, content, created_at) VALUES (?,?,?,NOW())";
	String selectAll =  "SELECT "
			  + "p.post_id, p.user_id, p.title, p.content, p.created_at,"
			  + "u.uid "
			  + "FROM posts p "
			  + "JOIN users u ON p.user_id = u.user_id "
			  + "ORDER BY p.created_at DESC";
	
	String selectById = "SELECT "
			  + "p.post_id, p.user_id, p.title, p.content, p.created_at, "
			  + "u.uid "
			  + "FROM posts p "
			  + "JOIN users u ON p.user_id = u.user_id "
			  + "WHERE p.post_id = ? ";
	
    // 게시글 목록을 시간순으로 가져오기
    List<PostDTO> getPostsByTime();

    // 게시글 ID로 게시글 가져오기
    PostDTO getPostById(int postId);

    // 게시글 작성
    int createPost(PostDTO postDTO);
}
