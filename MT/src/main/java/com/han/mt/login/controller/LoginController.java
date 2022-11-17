package com.han.mt.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.han.mt.login.model.KakaoVO;
import com.han.mt.login.model.LoginVO;
import com.han.mt.login.service.LogintService;
import com.han.mt.social.model.SocialVO;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;
import com.sun.tools.javac.parser.ReferenceParser.ParseException;

@Controller 
@RequestMapping(value="/")
public class LoginController {
	
	@Autowired
	private LogintService service; 
	@Inject
	private JavaMailSenderImpl mailSender;

	@GetMapping(value="/login") //�α��� ȭ�� ����
	public String login (HttpSession session) {	
		System.out.println("��Ʈ��(login) �α��� �޼��� ����");
		return"user/login";		
	}

	
	@GetMapping(value="/adminLoginSystemMenu") //�α��� ȭ�� ����
	public String adminLogin (Model model,HttpSession session) {	
		System.out.println("��Ʈ��(adminLogin) ������ �α��� �޼��� ����");
		model.addAttribute("admin","admin");
		return"user/login";		
	}
	
	
	
	@PostMapping(value="/login")//�α��� ������ �޾Ƽ� ó��
	public String login(Model model,LoginVO vo,HttpSession session)  throws Exception{	
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
		}
		return"home";

	}	
	
	
	
	
	@GetMapping(value="/login/logout")//�α׾ƿ�
	public String logout(HttpSession session) {
		System.out.println("�α׾ƿ�" );
		session.removeAttribute("loginData");
		session.removeAttribute("loginId");
		session.removeAttribute("joinSocial");
		session.removeAttribute("joinClub");
		session.removeAttribute("adminAcc");
		return "redirect:/";
	}

	@GetMapping(value="/login/findId") //���̵� ã�� ȭ������ �ѱ�
	public String findId() {
		return "user/findId";
	}
	
	@PostMapping(value="/findId")//���̵� ã��
	@ResponseBody
	public String findId(@ModelAttribute UserDTO dto,Model model,HttpSession session) throws Exception {
		System.out.println("���̵� ã�� jsp���� ���� DTO�� :" + dto);
		String result = service.findId(session,dto);		
		model.addAttribute("List",result);
		return result;		
	}
	
	
	@PostMapping(value="/idChk")//���̵� �ߺ�üũ
	@ResponseBody
	public int idChk(@RequestParam String email,HttpSession session,@RequestParam String type) throws Exception{
		System.out.println("��Ʈ�ѷ�(idChk) : ������ "+email);
		int result = service.idChk(email,session,type);
		System.out.println("��Ʈ�ѷ�(idChk) : �ߺ�Ȯ�� ���"+result);
		
		return result;
	}

	
	@GetMapping(value="/sign")//ȸ������ ȭ������ �Ѿ�� �޼ҵ�
	public String Signup(HttpSession session) {
		return "user/signup";
	} 

	
	
	@RequestMapping(value="/login/kakaoLogin", method=RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code,HttpSession session,Model model) throws Exception {
		System.out.println("#########" + code);
		String access_Token = service.getAccessToken(code);
		HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
		System.out.println("��Ʈ��(kakaoLogin) īī���������� �޾ƿ� ��ū :"+"\n"+ access_Token);
		System.out.println("��Ʈ��(kakaoLogin) īī���������� �޾ƿ� name : " +"\n"+ userInfo.get("nickname"));
		System.out.println("��Ʈ��(kakaoLogin) īī���������� �޾ƿ� email : " +"\n"+ userInfo.get("email"));
		System.out.println("��Ʈ��(kakaoLogin) īī���������� �޾ƿ� gender : " +"\n"+ userInfo.get("gender"));
		int result = service.idChk(userInfo.get("email").toString(),session,"email");
			if(result == 0) {
				service.kakaoLogin(userInfo.get("email").toString(),session);
			return "home";					
			} else {
				session.setAttribute("email", userInfo.get("email").toString());
				session.setAttribute("name", userInfo.get("nickname").toString());
				model.addAttribute("kakao","ka");
			return "user/userDetail";
			}
    	}
	
	@PostMapping(value="/emailRes")//�̸��� �ߺ�Ȯ���� �ش���Ϸ� ������ȣ ����
	public String emailRes(Model model,HttpSession session,@RequestParam String email)throws Exception{
		System.out.println("���̵� ���� JSP���� ���� ��:"+ email);
		session.setAttribute("id",email);
		model.addAttribute("email",email);
		model.addAttribute("id",email);
		
		return "forward:mailSend";
	} 
	//�������� ����
	@PostMapping(value="/mailSend")
	public String mailSend(Model model,UserDTO dto,final HttpServletRequest req)throws Exception {
		MimeMessagePreparator pre = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception{
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
				
				//�����ڵ� ���ǿ� ��� ���ѽð� �����ϱ�
				Random rd =new Random();
				int joinCode = rd.nextInt(9000)+1000;
				HttpSession session = req.getSession();
				//������ȣ ���ǿ� ����
				session.setAttribute("joinCode", joinCode);
				System.out.println("��Ʈ�ѷ�(mailSend)���� ������ �����ڵ� "+"\n"+joinCode);
				System.out.println("��Ʈ�ѷ�(mailSend) ���ǿ� ����� �ڵ�" +"\n"+ session.getAttribute("joinCode"));
				//������ȣ ��ȿ�ð� 10��
				session.setMaxInactiveInterval(600); // ������ȿ 10��
				
				String email = req.getParameter("email");
				System.out.println("��Ʈ�ѷ�(mailSend)���� ���� �̸��� �ּ� "+"\n"+email);
				helper.setTo(email);
				helper.setSubject("�쵿���� ������ȣ�� �����ϴ�");
				helper.setText("������ȣ :" + joinCode+"\n"+"����â���� ���ư� �Է����ּ���");
				}
			};
			
			model.addAttribute("email",req.getParameter("email"));
			model.addAttribute("id", req.getParameter("email"));		
			mailSender.send(pre);
			model.addAttribute("kakaoLogin", "īī�� �α��� ����"+ "\n"+"����Ͻ� �г����� �������ּ��� ");
			return "user/userDetail";
	}
	
	
	
	
	@PostMapping(value="/sign")//ȸ������ ����� �ް� ���ο� ȸ���� DB�� ����
	public String Signup(HttpSession session,@RequestParam String email,@RequestParam String auth,Model model)throws Exception{
		String code = session.getAttribute("joinCode").toString();
		System.out.println("��Ʈ�ѷ�(Signup)���� �޾ƿ� email " +"\n"+ email);
		System.out.println("��Ʈ�ѷ�(Signup)���� �޾ƿ� auth " +"\n"+ auth);
		System.out.println("��Ʈ�ѷ�(Signup)���ǿ� ����� �ڵ�" +"\n"+ session.getAttribute("joinCode"));
		System.out.println("��Ʈ�ѷ�(Signup)���� �޾ƿ� code" +"\n"+ code);
		

		if(code.equals(auth)) {
			session.setAttribute("email",email);
			model.addAttribute("AuthMsg", "������ �����Ͽ����ϴ�"); 
			System.out.println("��Ʈ�ѷ�(Signup)�������� ���� �� �������� �̵�" +"\n");

			return "user/userDetail";
		}


		model.addAttribute("AuthMsg", "������ �����Ͽ����ϴ� \n �ٽ� �õ����ּ���"); 
		System.out.println("��Ʈ�ѷ�(Signup)�������� Ȩ���� �̵�" +"\n");
		return "user/signup";
	} 
	
	

	@PostMapping(value="/login/userDetail")//���� �� ����
	public String userDetail(HttpSession session,@ModelAttribute UserDTO dto,Model model)throws Exception{
		System.out.println("��Ʈ��(userDetail) �󼼰��Կ��� ���� ���� :"+dto);
		System.out.println("��Ʈ��(userDetail) �󼼰��Կ��� ���� ���� :"+dto.getKakao());
		
		boolean signup;
	
		if(dto.getKakao().equals("kakao")) {
			System.out.println("��Ʈ��(userDetail): kakao �ִ�");
			signup = service.kakaoSignup(dto,session);
		} else {
			signup = service.signup(dto,session);
			System.out.println("��Ʈ��(userDetail): kakao ����");	
		}
		
		if(signup) {
			session.setAttribute("loginId", dto.getEmail());
			session.removeAttribute("email");
			session.removeAttribute("name");
			return "home";	
		}
		
		model.addAttribute("joinError", "���Կ� �����Ͽ����ϴ�. \n �ٽ� �õ����ּ���"); 
		return "user/signup";	
	} 
	
	
	@PostMapping(value="/login/userDetail/login/userDetail")//���� �� ����
	public String userDetail2(HttpSession session,@ModelAttribute UserDTO dto,Model model)throws Exception{
		System.out.println("��Ʈ��(userDetail) �󼼰��Կ��� ���� ���� :"+dto);
		System.out.println("��Ʈ��(userDetail) �󼼰��Կ��� ���� ���� :"+dto.getKakao());
		boolean signup;
		
		if(dto.getKakao().equals("kakao")) {
			System.out.println("��Ʈ��(userDetail): kakao �ִ�");
			signup = service.kakaoSignup(dto,session);			
		} else {
			signup = service.signup(dto,session);
			System.out.println("��Ʈ��(userDetail): kakao ����");			
		}
		
		if(signup) {
			session.setAttribute("loginId", dto.getEmail());
			session.removeAttribute("email");
			session.removeAttribute("name");
			return "home";	
		}		
		
		model.addAttribute("joinError", "���Կ� �����Ͽ����ϴ�. \n �ٽ� �õ����ּ���"); 
		return "user/signup";	
	} 
	
}
