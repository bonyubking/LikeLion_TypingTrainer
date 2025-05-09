package com.typing.model.dto;


public class UserDto {
	private Long userId; //pk
	private String uId; //회원ID
	private String password; //회원 비밀번호 
	private String nickname; //닉네임 
	
	public UserDto(String uId, String password, String nickname) {
		this.uId = uId;
		this.password = password;
		this.nickname = nickname;
	}
	public Long getUserId() {return userId;}
	public void setUserId(Long userId) {this.userId = userId;}
	public String getuId() {return uId;}
	public String getPassword() {return password;}
	public String getNickname() {return nickname;}
}
