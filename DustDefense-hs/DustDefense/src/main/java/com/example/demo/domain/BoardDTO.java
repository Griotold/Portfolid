package com.example.demo.domain;

import lombok.Data;
@Data
public class BoardDTO {
	//자바로 가져올때는 카멜케이스 
	//DB는 그냥 스네이크임.
	//입력받은 데이터의 저장 및 전송을 해줘야 하므로 VO가 아니라 DTO
	/** 번호 (PK) */
	private Long idx;

	/** 제목 */
	private String title;

	/** 내용 */
	private String content;
}