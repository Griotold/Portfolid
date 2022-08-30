package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.BoardDTO;
import com.example.demo.mapper.BoardMapper;

@SpringBootTest
class ServiceTests {

	@Autowired
	private BoardMapper boardMapper;

	@Test
	public void registerTest() {
		//given when then
		//BoardDTO의 데이터를 다른 값으로 수정 하는 Mapper의 
		//updateBoard메소드가 잘 동작하는지 테스트

		//1. given : 데이터 준비
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목을 수정합니다.");
		params.setContent("테스트함수에서바꾼 내용");
		params.setIdx((long) 1);

		//2. when : 준비된 데이터로 실행

	}

}
