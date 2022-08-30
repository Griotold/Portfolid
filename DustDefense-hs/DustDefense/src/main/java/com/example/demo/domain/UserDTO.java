package com.example.demo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class UserDTO {
	//자바로 가져올때는 카멜케이스 
	//DB는 그냥 스네이크임.
	/** 번호 (PK) */
	
	private String id;
	
	
	private String name;
	
	private String authorityType;
	
	
	private String nickname;
	
	
	private String email;
	
	
	private String password;
	
	
	private LocalDateTime signupDate;
	
	
	private String passwordQuestionCode;
	
	
	private String passwordQuestionAnswer;
	
	private String validMember;
	
	
	
	
}
