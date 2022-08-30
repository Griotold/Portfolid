package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.UserDTO;
import com.example.demo.mapper.UserMapper;

@SpringBootTest
public class UserMapperTests {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void memberJoin() throws Exception{
		UserDTO member = new UserDTO();
		
		member.setId("test");			
		member.setName("test");			
		member.setAuthorityType("0");		
		member.setNickname("test");		
		member.setEmail("test");		
		member.setPassword("test");		
		//member.setSignupDate("test");		
		member.setPasswordQuestionCode("cd");		
		member.setPasswordQuestionAnswer("test");		
		member.setValidMember("N");		
		
		userMapper.memberJoin(member);			//쿼리 메서드 실행
		
	}
	
	// 아이디 중복검사
	@Test
	public void idChk() throws Exception{
		String id = "test";	// 존재하는 아이디
		String id2 = "test123";	// 존재하지 않는 아이디
		userMapper.idCheck(id);
		userMapper.idCheck(id2);
	}
	
	// 닉네임 중복검사
	@Test
	public void nicknameCheck() throws Exception{
		String nickname = "test1";	// 존재하는 닉네임
		String nickname2 = "test123";	// 존재하지 않는 닉네임
		userMapper.nicknameCheck(nickname);
		userMapper.nicknameCheck(nickname2);
	}
}
