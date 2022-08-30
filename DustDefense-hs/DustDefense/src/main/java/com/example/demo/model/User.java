package com.example.demo.model;

import lombok.Data;

@Data
public class User {
	private String id;
	private String name;
	private String authority_type;
	private String nickname;
	private String email;
	private String password;
	private String singup_date;
	private String password_question_code;
	private String password_question_answer;
	private String valid_member;
}
