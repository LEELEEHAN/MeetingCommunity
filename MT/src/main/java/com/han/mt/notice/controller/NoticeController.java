package com.han.mt.notice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.han.mt.notice.model.NoticeBoardDTO;
import com.han.mt.notice.service.NoticeService;
import com.han.mt.user.model.UserDTO;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {

	@Autowired
	private NoticeService service; 
	

	@GetMapping(value="") //로그인 화면 띄우기
	public String notice (@RequestParam(defaultValue="info") String category,
			Model model) {	
		System.out.println("컨트롤(login) : 로그인 메서드 동작");		
		System.out.println("컨트롤(login) : 로그인 메서드 동작"+category);		
		service.getList(category);
		model.addAttribute("category", category);
		model.addAttribute("list", service.getList(category));
		return"notice/notice";		
	}

	@GetMapping(value="/write") //로그인 화면 띄우기
	public String noticeWrite (Model model) {	
		System.out.println("컨트롤(login) : 로그인 메서드 동작");			
		return"notice/noticeWrite";		
	}
	
	@PostMapping(value="/write") //로그인 화면 띄우기
	public String noticeWrite (@SessionAttribute("loginData") UserDTO user
			,@ModelAttribute NoticeBoardDTO dto) {	
		System.out.println("컨트롤(noticeWrite) : 받은 내용" +dto);
		service.write(dto);
		return"notice/notice";		
	}
	
	
}
