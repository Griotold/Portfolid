package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class MainController {
	@RequestMapping("/finedust")
	@ResponseBody
	public String index() {
		return "index";
	}
	
	@GetMapping("/pathfind")
	public void getPathFind() {
		log.info("pathfind..........");
	}
}
