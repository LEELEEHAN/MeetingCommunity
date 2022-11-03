package com.han.mt.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.han.mt.user.model.UserDTO;
import com.han.mt.user.service.UserService;


@Controller 
@RequestMapping(value="/mypage")
public class UserController {

	/*
	  System.out.println();
	 */
	@Autowired
	private UserService service;
	
	@GetMapping(value="")
	public String mypage(Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user) {
		System.out.println("마이페이지 접속, 로그인 데이타 :"+user);
		
		String loginId = (String) session.getAttribute("loginId");
		model.addAttribute("joinClub",service.joinClub(loginId));
		model.addAttribute("joinSocial",service.joinSocial(loginId));
		
		return"user/mypage";
	}

}
