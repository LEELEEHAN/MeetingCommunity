package com.han.mt.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.han.mt.login.model.LoginVO;
import com.han.mt.login.service.LogintService;
import com.han.mt.social.model.SocialVO;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;

@Controller 
@RequestMapping(value="/")
public class LoginController {
	
	@Autowired
	private LogintService service; 
	
	
	@GetMapping(value="/login")
	public String login (HttpSession session) {	
		return"user/login";		
	}
	
	@PostMapping(value="/login")
	public String login(Model model,LoginVO vo,
			HttpSession session)  throws Exception{	
		System.out.println(vo);
		int result = service.getLogin(vo,session);
		switch(result) {
		case 2:
            model.addAttribute("msg","해당계정을 확인해주세요");
		case 3:
            model.addAttribute("msg","올바르지않은 패스워드입니다");
		case 4:	
			return"user/userDetail";
		}
		return"home";
		
	}
	@GetMapping(value="/login/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginData");
		return "redirect:/";
	}

	@GetMapping(value="/login/findId")
	public String findId() {
		return "user/findId";
	}
	
	@PostMapping(value="/login/findId")
	public String findId(@ModelAttribute UserDTO DTO
			,Model model,HttpSession session) throws Exception {
		boolean result = service.findId(session,DTO);
		
		return "user/findId";
	}
	@GetMapping(value="/sign")
	public String Signup(HttpSession session) {
		return "user/signup";
	} 
	
	
	@PostMapping(value="/idChk")
	@ResponseBody
	public int idChk(@ModelAttribute UserDTO dto) throws Exception{
		System.out.println(dto);
		int result = service.idChk(dto);
		System.out.println(result);
 
		return result;
	}
	@PostMapping(value="/sign")
	public String Signup(HttpSession session,
			@ModelAttribute UserDTO dto
			)throws Exception{
		System.out.println(dto);
		boolean signup = service.signup(dto,session);

		return "user/signup";
	} 

	@PostMapping(value="/nickNameCheck")
	@ResponseBody
	public int nickNameCheck(@ModelAttribute UserDTO dto) throws Exception{
		System.out.println(dto);
		int result = service.nickNameCheck(dto);
		System.out.println(result);
 
		return result;
	}
	@PostMapping(value="/userDetail")
	public String userDetail(HttpSession session,
			@ModelAttribute UserDTO dto
			)throws Exception{
		System.out.println(dto);
		boolean signup = service.signup(dto,session);

		return "user/signup";
	} 
	
}
