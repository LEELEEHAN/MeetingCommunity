package com.han.mt.notice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.han.mt.notice.service.NoticeService;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {

	@Autowired
	private NoticeService service; 
	

	@GetMapping(value="") //로그인 화면 띄우기
	public String notice (
			Model model) {	
		System.out.println("컨트롤(login) : 로그인 메서드 동작");
		
		//model.addAttribute("list",list);
		return"notice/notice";		
	}
	
}
