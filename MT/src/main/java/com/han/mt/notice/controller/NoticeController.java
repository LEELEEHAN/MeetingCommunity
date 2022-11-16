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
	

	@GetMapping(value="") //�α��� ȭ�� ����
	public String notice (
			Model model) {	
		System.out.println("��Ʈ��(login) : �α��� �޼��� ����");
		
		//model.addAttribute("list",list);
		return"notice/notice";		
	}
	
}
