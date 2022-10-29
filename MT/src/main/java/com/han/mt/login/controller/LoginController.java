package com.han.mt.login.controller;

import java.util.List;

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
		System.out.println("컨트롤러(login) : 로그인 메서드 실행");
		return"user/login";		
	}
	
	@PostMapping(value="/login")
	public String login(Model model,LoginVO vo,
			HttpSession session)  throws Exception{	
		System.out.println("로그인 jsp에서 받은 vo값 :" + vo);

		int result = service.getLogin(vo,session);
		switch(result) {

		case 1:
			System.out.println("로그인 성공" );

			return"home";
		case 2:
			System.out.println("로그인 실패 비밀번호 틀림" );
            model.addAttribute("msg","다시 로그인 시도를 해주세요");
    		return"user/login";		
		case 3:
			System.out.println("로그인 실패 해당 아이디없음" );
            model.addAttribute("msg","계정을 확인해주세요");
    		return"user/login";		
		case 4:	
			System.out.println("로그인 성공 닉네임 설정이동" );
			return"user/userDetail";
		}
		return"home";

	}
	@GetMapping(value="/login/logout")
	public String logout(HttpSession session) {

		System.out.println("로그아웃" );
		session.removeAttribute("loginData");
		return "redirect:/";
	}

	@GetMapping(value="/login/findId")
	public String findId() {
		return "user/findId";
	}
	
	@PostMapping(value="/login/findId")
	public String findId(@ModelAttribute UserDTO dto
			,Model model,HttpSession session) throws Exception {
		System.out.println("아이디찾기 jsp에서 받은 DTO값 :" + dto);
		List result = service.findId(session,dto);
		
		model.addAttribute("List",result);
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
		System.out.println("컨트롤러(nickNameCheck) : 받은 값 "+dto);
		int result = service.nickNameCheck(dto);
		System.out.println("중복확인 값");
 
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
