package com.typing.model.dto;

import java.sql.Date;

public class CommentDTO {
    
	private int commentId;
    private int userId;
    private String uid;     // Users 테이블에서 가져온 닉네임 혹은 uid
    private int postId;
    private String content;
    private Date createdAt;
	
    public CommentDTO(int commentId, int userId, String uid, int postId, String content, Date createdAt) {
		
    	this.commentId = commentId;
		this.userId = userId;
		this.uid = uid;
		this.postId = postId;
		this.content = content;
		this.createdAt = createdAt;
	}

	public CommentDTO() {

	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
    
}
