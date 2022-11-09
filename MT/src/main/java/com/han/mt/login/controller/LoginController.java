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
	
	
	@GetMapping(value="/login") //�α��� ȭ�� ����
	public String login (HttpSession session) {	
		System.out.println("��Ʈ��(login) : �α��� �޼��� ����");
		return"user/login";		
	}
	
	@PostMapping(value="/login")//�α��� ������ �޾Ƽ� ó��
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
	@GetMapping(value="/login/logout")//�α׾ƿ�
	public String logout(HttpSession session) {

		System.out.println("�α׾ƿ�" );
		session.removeAttribute("loginData");
		return "redirect:/";
	}

	@GetMapping(value="/login/findId") //���̵� ã�� ȭ������ �ѱ�
	public String findId() {
		return "user/findId";
	}
	
	@PostMapping(value="/findId")//���̵� ã��
	@ResponseBody
	public String findId(@ModelAttribute UserDTO dto
			,Model model,HttpSession session) throws Exception {
		System.out.println("���̵� ã�� jsp���� ���� DTO�� :" + dto);
		String result = service.findId(session,dto);
		
		model.addAttribute("List",result);

		return result;
	}
	
	@PostMapping(value="/idChk")//���̵� �ߺ�üũ
	@ResponseBody
	public int idChk(@RequestParam String email
			,HttpSession session) throws Exception{
		System.out.println("��Ʈ�ѷ�(idChk) : ������ "+email);
		int result = service.idChk(email,session);
		System.out.println("��Ʈ�ѷ�(idChk) : �ߺ�Ȯ�� ���"+result);
		
		return result;
	}
	
	
	@GetMapping(value="/sign")//ȸ������ ȭ������ �Ѿ�� �޼ҵ�
	public String Signup(HttpSession session) {
		return "user/signup";
	} 
	
	
	
	
	
	
	@PostMapping(value="/nickNameCheck")//�г��� �ߺ�üũ
	@ResponseBody
	public int nickNameCheck(@ModelAttribute UserDTO dto,
			HttpSession session) throws Exception{
		System.out.println("��Ʈ��(nickNameCheck) : ���� �� "+dto);
		int result = service.nickNameCheck(dto,session);
		System.out.println("��Ʈ��(nickNameCheck) : �ߺ�Ȯ�� ���"+result);	
		return result;
	}

	@PostMapping(value="/emailRes")//�̸��� �ߺ�Ȯ���� �ش���Ϸ� ������ȣ ����
	public String emailRes(Model model,HttpSession session,
			@RequestParam String email
			)throws Exception{
		System.out.println("���̵� ���� JSP���� ���� ��:"+ email);
		session.setAttribute("id",email);
		model.addAttribute("email",email);
		model.addAttribute("id",email);
		
		return "forward:mailSend";
	} 
	//�������� ����
	@PostMapping(value="/mailSend")
	public String mailSend(Model model,UserDTO dto,final HttpServletRequest req
			)throws Exception {
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
		
			return "user/userDetail";
	}
	
	
	
	
	@PostMapping(value="/sign")//ȸ������ ����� �ް� ���ο� ȸ���� DB�� ����
	public String Signup(HttpSession session,
			@RequestParam String email,
			@RequestParam String auth
			)throws Exception{
		String cod = session.getAttribute("joinCode").toString();
		System.out.println("��Ʈ�ѷ�(Signup)���� �޾ƿ� email " +"\n"+ email);
		System.out.println("��Ʈ�ѷ�(Signup)���� �޾ƿ� auth " +"\n"+ auth);
		System.out.println("��Ʈ�ѷ�(Signup)���ǿ� ����� �ڵ�" +"\n"+ session.getAttribute("joinCode"));
		System.out.println("��Ʈ�ѷ�(Signup)���� �޾ƿ� cod " +"\n"+ cod);
		

		if(cod.equals(auth)) {
			session.setAttribute("email",email);
			return "user/userDetail";
		}
		return "redirect:/";
	} 
	
	

	@PostMapping(value="/userDetail")//�г��� ����
	public String userDetail(HttpSession session,
			@ModelAttribute UserDTO dto
			)throws Exception{
		System.out.println("��Ʈ��(userDetail) �󼼰��Կ��� ���� ���� :"+dto);
		boolean signup = service.signup(dto,session);
		if(signup) {
			session.setAttribute("error","������� �г���.");
			return "redirect:/";
		}
		return "home";
	} 
	
}
