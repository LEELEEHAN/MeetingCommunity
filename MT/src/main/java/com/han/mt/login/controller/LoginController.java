package com.han.mt.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
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
	@Inject
	private JavaMailSenderImpl mailSender;
	
	
//	
//	@PostMapping(value="newJoin")
//	public String newJoin()
	
	
	@GetMapping(value="/login") //로그인 화면 띄우기
	public String login (HttpSession session) {	
		System.out.println("컨트롤(login) : 로그인 메서드 동작");
		return"user/login";		
	}
	
	@PostMapping(value="/login")//로그인 정보를 받아서 처리
	public String login(Model model,LoginVO vo,
			HttpSession session)  throws Exception{	
		System.out.println("로그인 jsp에서 받아온 vo값 :" + vo);

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
			System.out.println("로그인 실패: 비밀번호 틀림" );
            model.addAttribute("loginMsg","계정을 확인해주세요");
    		return"user/login";		
		case 4:	
			System.out.println("로그인 성공 : 닉네임 설정으로" );
			return"user/userDetail";
		}
		return"home";

	}
	@GetMapping(value="/login/logout")//로그아웃
	public String logout(HttpSession session) {

		System.out.println("로그아웃" );
		session.removeAttribute("loginData");
		return "redirect:/";
	}

	@GetMapping(value="/login/findId") //아이디 찾기 화면으로 넘김
	public String findId() {
		return "user/findId";
	}
	
	@PostMapping(value="/findId")//아이디 찾기
	@ResponseBody
	public String findId(@ModelAttribute UserDTO dto
			,Model model,HttpSession session) throws Exception {
		System.out.println("아이디 찾기 jsp에서 받은 DTO값 :" + dto);
		String result = service.findId(session,dto);
		
		model.addAttribute("List",result);

		return result;
	}
	
	@PostMapping(value="/idChk")//아이디 중복체크
	@ResponseBody
	public int idChk(@RequestParam String email
			,HttpSession session) throws Exception{
		System.out.println("컨트롤러(idChk) : 받은값 "+email);
		int result = service.idChk(email,session);
		System.out.println("컨트롤러(idChk) : 중복확인 결과"+result);
		
		return result;
	}
	
	
	@GetMapping(value="/sign")//회원가입 화면으로 넘어가는 메소드
	public String Signup(HttpSession session) {
		return "user/signup";
	} 
	
	
	
	
	
	
	@PostMapping(value="/nickNameCheck")//닉네임 중복체크
	@ResponseBody
	public int nickNameCheck(@ModelAttribute UserDTO dto,
			HttpSession session) throws Exception{
		System.out.println("컨트롤(nickNameCheck) : 받은 값 "+dto);
		int result = service.nickNameCheck(dto,session);
		System.out.println("컨트롤(nickNameCheck) : 중복확인 결과"+result);	
		return result;
	}

	@PostMapping(value="/emailRes")//이메일 중복확인후 해당메일로 인증번호 전송
	public String emailRes(Model model,HttpSession session,
			@RequestParam String email
			)throws Exception{
		System.out.println("아이디 인증 JSP에서 받은 값:"+ email);
		session.setAttribute("id",email);
		model.addAttribute("email",email);
		model.addAttribute("id",email);
		
		return "forward:mailSend";
	} 
	//인증메일 전송
	@PostMapping(value="/mailSend")
	public String mailSend(Model model,UserDTO dto,final HttpServletRequest req
			)throws Exception {
		MimeMessagePreparator pre = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception{
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
				
				//인증코드 세션에 담아 제한시간 설정하기
				Random rd =new Random();
				int joinCode = rd.nextInt(9000)+1000;
				HttpSession session = req.getSession();
				//인증번호 세션에 저장
				session.setAttribute("joinCode", joinCode);
				System.out.println("컨트롤러(mailSend)에서 생성한 인증코드 "+"\n"+joinCode);
				System.out.println("컨트롤러(mailSend) 세션에 저장된 코드" +"\n"+ session.getAttribute("joinCode"));
				//인증번호 유효시간 10분
				session.setMaxInactiveInterval(600); // 인증유효 10분
				
				String email = req.getParameter("email");
				System.out.println("컨트롤러(mailSend)에서 보낼 이메일 주소 "+"\n"+email);
				helper.setTo(email);
				helper.setSubject("우동에서 인증번호를 보냅니다");
				helper.setText("인증번호 :" + joinCode+"\n"+"가입창으로 돌아가 입력해주세요");
				}
			};
			
			model.addAttribute("email",req.getParameter("email"));
			model.addAttribute("id", req.getParameter("email"));
			
			mailSender.send(pre);
		
			return "user/userDetail";
	}
	
	
	
	
	@PostMapping(value="/sign")//회원가입 양식을 받고 새로운 회원을 DB에 저장
	public String Signup(HttpSession session,
			@RequestParam String email,
			@RequestParam String auth
			)throws Exception{
		String cod = session.getAttribute("joinCode").toString();
		System.out.println("컨트롤러(Signup)에서 받아온 email " +"\n"+ email);
		System.out.println("컨트롤러(Signup)에서 받아온 auth " +"\n"+ auth);
		System.out.println("컨트롤러(Signup)세션에 저장된 코드" +"\n"+ session.getAttribute("joinCode"));
		System.out.println("컨트롤러(Signup)에서 받아온 cod " +"\n"+ cod);
		

		if(cod.equals(auth)) {
			session.setAttribute("email",email);
			return "user/userDetail";
		}
		return "redirect:/";
	} 
	
	

	@PostMapping(value="/userDetail")//닉네임 설정
	public String userDetail(HttpSession session,
			@ModelAttribute UserDTO dto
			)throws Exception{
		System.out.println("컨트롤(userDetail) 상세가입에서 받은 정보 :"+dto);
		boolean signup = service.signup(dto,session);
		if(signup) {
			session.setAttribute("error","사용중인 닉네임.");
			return "redirect:/";
		}
		return "home";
	} 
	
}
