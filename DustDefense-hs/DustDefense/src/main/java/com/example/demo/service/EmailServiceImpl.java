package com.example.demo.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.controller.UserController;

@Service
@Component
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSenderImpl mailSender;
    private JavaMailSender emailSender;

    private int authNumber; 
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    // 난수 발생 메서드
    public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
	}
    
	//이메일 보낼 양식! 
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom = "gotjd9773@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력 
		String toMail = email;
		String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목 
		String content = 
				"홈페이지를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 ! 
                "<br><br>" + 
			    "인증 번호는 " + authNumber + "입니다." + 
			    "<br>" + 
			    "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}
	
	//이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content) { 
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content,true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
    
    //public String sendSimpleMessage(String email) {
        //SimpleMailMessage message = new SimpleMailMessage();
        
        //JavaMailSenderImplclass
        //JavaMailSender 인터페이스의 구현을 제공
        //MimeMessage 및 SimpleMailMessage를 지원
        //SimpleMailMessageclass
        //발신인, 수신인, 참조인, 제목 및 텍스트 필드를 포함한 간단한 메일 메시지를 작성하는 데 사용
        //MimeMessagePreparatorinterface
        //MIME 메시지를 준비하기 위한 콜백 인터페이스를 제공
        //MimeMessageHelperclass
        //MIME 메시지를 만들기 위한 클래스
        //HTML 레이아웃에서 이미지, 일반적인 메일 첨부 파일 및 텍스트 내용을 지원
        
//        /* 인증번호(난수) 생성 */
//        Random random = new Random();
//        int checkNum = random.nextInt(888888) + 111111;
//        logger.info("인증번호 " + checkNum);
//        
//        
//      
//        /* 이메일 보내기 */
//        String setFrom = "gotjd9773@naver.com";
//        String toMail = email;
//        String title = "회원가입 인증 이메일 입니다.";
//        String content = 
//              "홈페이지를 방문해주셔서 감사합니다." +
//              "<br><br>" + 
//              "인증 번호는 " + checkNum + "입니다." + 
//              "<br>" + 
//              "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
//		
//		 try {
//		 
//		 MimeMessage message = emailSender.createMimeMessage(); MimeMessageHelper
//		 helper = new MimeMessageHelper(message, true, "utf-8");
//		 helper.setFrom(setFrom); helper.setTo(toMail); helper.setSubject(title);
//		 helper.setText(content,true); emailSender.send(message);
//		 
//		 }catch(Exception e) { e.printStackTrace(); }
//		 
//        String num = Integer.toString(checkNum);
//        
//        return num;
//	    }
}
