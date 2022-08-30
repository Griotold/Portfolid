package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.BoardDTO;

@Controller
public class MapController {

	@GetMapping("/map/map.do")
	public String openMap() {
		//List<BoardDTO> boardList = boardService.getBoardList();
		//model.addAttribute("boardList", boardList);

		//return "board/list";
		return "map/map";
		
	} 
}
