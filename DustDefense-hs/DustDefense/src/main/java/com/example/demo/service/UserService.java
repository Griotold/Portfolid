package com.example.demo.service;


import java.util.HashMap;
import java.util.List;

import com.example.demo.domain.UserDTO;
import com.example.demo.model.User;

public interface UserService {

	//회원가입
	public void memberJoin(UserDTO user) throws Exception;
		
	// 아이디 중복 검사
	public int idCheck(String id) throws Exception;
	
	// 닉네임 중복 검사
	public int nicknameCheck(String nickname) throws Exception;
	
	// 마이페이지 회원정보 보기
	public UserDTO readMember(String id);
	
	//회원정보 수정
	public void editMember(UserDTO user);
	
	//회원탈퇴
	public void deleteMember(UserDTO user);

	
}
