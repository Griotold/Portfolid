package com.example.demo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.UserDTO;
import com.example.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;

	@Override
	public void memberJoin(UserDTO user) throws Exception {
		
		userMapper.memberJoin(user);
		
	}
	
	@Override
	public int idCheck(String id) throws Exception {
		
		return userMapper.idCheck(id);
	}

	@Override
	public int nicknameCheck(String nickname) throws Exception {
		
		return userMapper.nicknameCheck(nickname);
	}
	// 마이페이지 회원정보보기
	@Override
	public UserDTO readMember(String id) {
		
		System.out.println("S : readMember()실행");
		UserDTO user = null;
		
		try {
			user = userMapper.readMember(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
		
	}
	//회원정보수정
	@Override
	public void editMember(UserDTO user) {
		try {
			userMapper.editMember(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	// 회원탈퇴
	@Override
	public void deleteMember(UserDTO user) {
		try {
			userMapper.deleteMember(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
