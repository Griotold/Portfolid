package com.example.demo.sample;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.security.dto.TbUserAuthDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

	@GetMapping("/all")
	public void exAll() {
		log.info("exAll..........");
	}
	
	@GetMapping("/member")
	public void exMember(@AuthenticationPrincipal TbUserAuthDTO tbUserAuth) {
		log.info("exMember..........");
		log.info(tbUserAuth);
	}
	
	@GetMapping("/admin")
	public void exAdmin() {
		log.info("exAdmin..........");
	}
	
	@GetMapping("/googleMapsSample")
	public void googleMaps() {
		log.info("googleMaps..........");
	}
	
	@GetMapping("/kakaoMapsSample")
	public void kakaoMaps() {
		log.info("kakaoMaps..........");
	}
	
	@GetMapping("/pathFind")
	public void pathFind() {
		log.info("pathFind..........");
	}
}