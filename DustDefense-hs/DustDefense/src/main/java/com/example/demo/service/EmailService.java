package com.example.demo.service;

public interface EmailService {
	//public String sendSimpleMessage(String email);
	
	/*난수 생성 */
	public void makeRandomNumber();
	
	/*이메일 보낼 양식*/
	public String joinEmail(String email);
	
	//이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content);
	
}
