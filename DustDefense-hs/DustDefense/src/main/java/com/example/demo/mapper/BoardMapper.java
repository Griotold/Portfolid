package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardDTO;

//mapper 어노테이션. 기존의 스프링은 DAO 클래스에 @Repository를 선언해서
//해당 클래스가 데이터베이스와 통신하는 클래스임을 나타냈다.
//하지만 스프링부트에서는 인터페이스에 @Mapper만 지정해주면 XML Mapper에서 메서드의
//이름과 일치하는 SQL문을 찾아 실행해준다.
//Mapper 영역은 데이터베이스와의 통신, 즉 SQL 쿼리를 호출하는 것이 전부이다.
@Mapper
public interface BoardMapper {
	
	//params에는 게시글의 정보가 담긴다.
	public int insertBoard(BoardDTO params);

	//하나의 게시글을 조회하는 쿼리
	//각 컬럼에 해당하는 결괏값이 리턴 타입으로 지정된 BoardDTO 클래스의 멤버 변수에 매핑
	//파라미터로 게시글 번호(idx, PK)
	//WHERE 조건으로 idx를 사용해서 특정 게시글을 조회.
	public BoardDTO selectBoardDetail(Long idx);

	//게시글을 수정하는 UPDATE쿼리를 호출하는 메서드
	public int updateBoard(BoardDTO params);

	//게시글을 삭제하는 DELETE쿼리를 호출하는 메서드
	//delete_yn 컬럼 상태가 'N'이면 노출되지 않게끔 구현
	//실제로 지우는 게 아니다.
	public int deleteBoard(Long idx);

	//게시글 목록을 조회하는 SELECT 쿼리를 호출하는 메서드
	public List<BoardDTO> selectBoardList();

	//delete_yn컬럼이 'N'인 게시글의 개수를 조회하는 SELECT 쿼리를 호출하는 메서드,
	//페이징 처리때 필요
	public int selectBoardTotalCount();
}

