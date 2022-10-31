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
		System.out.println("��Ʈ��(login) : �α��� �޼��� ����");
		return"user/login";		
	}
	
	@PostMapping(value="/login")
	public String login(Model model,LoginVO vo,
			HttpSession session)  throws Exception{	
		System.out.println("�α��� jsp���� �޾ƿ� vo�� :" + vo);

		int result = service.getLogin(vo,session);
		switch(result) {

		case 1:
			System.out.println("�α��� ����" );

			return"home";
		case 2:
			System.out.println("�α��� ���� ��й�ȣ Ʋ��" );
            model.addAttribute("loginMsg","�ٽ� �α��� �õ��� ���ּ���");
    		return"user/login";		
		case 3:
			System.out.println("�α��� ����: ��й�ȣ Ʋ��" );
            model.addAttribute("loginMsg","������ Ȯ�����ּ���");
    		return"user/login";		
		case 4:	
			System.out.println("�α��� ���� : �г��� ��������" );
			return"user/userDetail";
		}
		return"home";

	}
	@GetMapping(value="/login/logout")
	public String logout(HttpSession session) {

		System.out.println("�α׾ƿ�" );
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
		System.out.println("���̵� ã�� jsp���� ���� DTO�� :" + dto);
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
	public int idChk(@ModelAttribute UserDTO dto
			,HttpSession session) throws Exception{
		System.out.println("��Ʈ�ѷ�(idChk) : ������ "+dto);
		int result = service.idChk(dto,session);
		System.out.println("��Ʈ�ѷ�(idChk) : �ߺ�Ȯ�� ���"+result);
		return result;
	}
	@PostMapping(value="/nickNameCheck")
	@ResponseBody
	public int nickNameCheck(@ModelAttribute UserDTO dto,
			HttpSession session) throws Exception{
		System.out.println("��Ʈ��(nickNameCheck) : ���� �� "+dto);
		int result = service.nickNameCheck(dto,session);
		System.out.println("��Ʈ��(nickNameCheck) : �ߺ�Ȯ�� ���"+result);	
		return result;
	}
	@PostMapping(value="/sign")
	public String Signup(HttpSession session,
			@ModelAttribute UserDTO dto
			)throws Exception{
		System.out.println("ȸ������ JSP���� ���� �� : "+ dto);
		boolean signup = service.signup(dto,session);
		System.out.println("ȸ�� ���� ���: "+signup);

		return "user/userDetail";
	} 

	@PostMapping(value="/userDetail")
	public String userDetail(HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute UserDTO dto
			)throws Exception{
		
		dto.setId(user.getId());
		
		System.out.println("�г��� ����â���� ���� ���� :"+dto);
		boolean signup = service.setNickName(dto,user,session);
		if(signup) {
			session.setAttribute("error","������� �г���.");
			return "redirect:/";
		}
		return "home";
	} 
	
}
