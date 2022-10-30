package com.han.mt.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.SessionAttribute;

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
            model.addAttribute("loginMsg","다시 로그인 시도를 해주세요");
    		return"user/login";		
		case 3:
			System.out.println("로그인 실패 해당 아이디없음" );
            model.addAttribute("loginMsg","계정을 확인해주세요");
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
	public Map<String, Object> idChk(@ModelAttribute UserDTO dto
			,HttpSession session) throws Exception{
		System.out.println("컨트롤러(idChk) : 받은 값 "+dto);
		boolean result = service.idChk(dto,session);
		System.out.println("컨트롤러(idChk) : 중복확인 결과"+result);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		System.out.println(map);
		return map;
	}
	@PostMapping(value="/sign")
	public String Signup(HttpSession session,
			@ModelAttribute UserDTO dto
			)throws Exception{
		System.out.println("회원 가입jsp에서 받은 값 : "+ dto);
		boolean signup = service.signup(dto,session);
		System.out.println("회원 가입 결과: "+signup);

		return "user/userDetail";
	} 

	@PostMapping(value="/nickNameCheck")
	@ResponseBody
	public boolean nickNameCheck(@ModelAttribute UserDTO dto,
			HttpSession session) throws Exception{
		System.out.println("컨트롤러(nickNameCheck) : 받은 값 "+dto);
		boolean result = service.nickNameCheck(dto,session);
		System.out.println("컨트롤러(nickNameCheck) : 중복확인 결과"+result);

		return result;
	}
	@PostMapping(value="/userDetail")
	public String userDetail(HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute UserDTO dto
			)throws Exception{
		
		dto.setId(user.getId());
		
		System.out.println("닉네임 설정창에서 받은 정보 :"+dto);
		boolean signup = service.setNickName(dto,user,session);
		if(signup) {
			session.setAttribute("error","사용중인 닉네임입니다.");
			return "redirect:/";
		}
		return "home";
	} 
	
}
