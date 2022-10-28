package com.han.mt.social.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.han.mt.social.model.SocialDTO;
import com.han.mt.social.model.SocialDynamicDTO;
import com.han.mt.social.model.SocialMemberDTO;
import com.han.mt.social.model.SocialVO;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;

@Controller
@RequestMapping(value = "/social")
public class SocialController {
	private static final Logger logger = LoggerFactory.getLogger(SocialController.class);
	
	@Autowired
	private SocialService service;
	
	
	@GetMapping(value = "")
	public String getSocial(Model model,HttpSession session,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String search) {

		System.out.println(category);
		System.out.println(search);
		
		
		if(search == null) {
			model.addAttribute("list",service.getSocial(category));
		} else {
			model.addAttribute("list",service.getSocialTitle(search));
		}
		
		model.addAttribute("field",service.getCategory());
		model.addAttribute("real",service.getReal());
		return "social/social";
	}
	
	
	@GetMapping(value = "/detail")
	public String socialDetail(Model model,HttpSession session,
			@RequestParam(required = false) int id) {

		
		model.addAttribute("detail", service.getDetail(id));
		model.addAttribute("real",service.getReal(id));
		model.addAttribute("memberList",service.getMember(id));
		return"social/detail";
	}
	
	
	
	
	@GetMapping(value = "/create")//작성양식
	public String createSocial(Model model,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute SocialVO vo) {

		System.out.println(user);
		System.out.println("겟 크리에이트 브이오"+vo);
		model.addAttribute("field",service.getCategory());
		return "social/create";
	}
	
	@PostMapping(value = "/create")//작성된 양식 저장
	public String createSocial(HttpServletRequest request,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute SocialVO vo) {
		
		vo.setSocialNum(service.getSocialNum());
		vo.setId(user.getId());
		vo.setNickName(user.getNickName());

		
		System.out.println("포스트 크리에이트 브이오"+vo);
		System.out.println("포스트 크리에이트 유저"+user);

		

		
		int result=service.createSocial(vo);
		
		switch(result) {
		case 9:
			return "error/error";
		case 8:
			service.deleteSoical(vo.getSocialNum());
			return "error/error";
		}
		
		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}

	@PostMapping(value="/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String delete(@RequestParam int id
			,@SessionAttribute("loginData") UserDTO user
			) {
		SocialDTO social = service.getDetail(id);
		
		JSONObject json = new JSONObject();

        if (social == null) {
            json.put("code", "notExists");
            json.put("message", "이미 삭제 된 데이터 입니다.");
        } else {
            if (true) {
                boolean result = service.deleteSoical(id);
                if (result) {
                    json.put("message", "삭제 완료");
                } else {
                    json.put("code", "fail");
                    json.put("message", "삭제 중 문제발생");
                }
            } else { // 관리자,글작성자 외
                json.put("code", "permissionError");
                json.put("message", "삭제권한이 없습니다.");
            }
        }

        return json.toJSONString();
		
	}
	
	@GetMapping(value="modify")
	public String modify(Model model
			,@RequestParam int id) {
		
		model.addAttribute("field",service.getCategory());
		model.addAttribute("detail",service.getDetail(id));
		
		return "social/modify";
	}
	@PostMapping(value = "/modify")//작성된 양식 저장
	public String modifySocial(HttpServletRequest request,
			@ModelAttribute SocialVO vo) {
		

		System.out.println(vo);

		service.modifySocial(vo);
		
		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	@PostMapping(value="/entrust")
	public String entrust(@ModelAttribute SocialVO vo) {
		System.out.println(vo); 
		service.entrust(vo);

		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	@PostMapping(value="/outcast")
	public String outcast(@ModelAttribute SocialVO vo) {
		System.out.println(vo.getNickName()); 
		service.outcast(vo);

		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	
	@PostMapping(value="/join")
	public String join(@ModelAttribute SocialVO vo)throws Exception {
		System.out.println(vo); 
		service.join(vo);

		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	
	
}
