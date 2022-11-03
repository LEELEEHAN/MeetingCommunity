package com.han.mt.club.comtroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
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

import com.han.mt.club.model.ClubDTO;
import com.han.mt.club.model.ClubVO;
import com.han.mt.club.service.ClubService;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;


@Controller
@RequestMapping(value = "/club")
public class ClubController {

	@Autowired
	private ClubService service;
	@Autowired
	private SocialService socialService;
	
	@GetMapping(value = "")
	public String getClub(Model model,HttpSession session,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String search) {

		System.out.println(category);
		System.out.println(search);
		
		
		if(search == null) {
			model.addAttribute("list",service.getClub(category));
		} else {
			model.addAttribute("list",service.getClubTitle(search));
		}
		
		model.addAttribute("field",socialService.getCategory());
		model.addAttribute("real",socialService.getReal());
		return "club/social";
	}
	
	
	@GetMapping(value = "/detail")
	public String socialDetail(Model model,HttpSession session,
			@RequestParam(required = false) int id) {
		String loginId = (String) session.getAttribute("loginId");
	
//		model.addAttribute("clubList",service.cluList(loginId));
		
		
		
		boolean chk = service.joinChk(loginId,id);
		if(chk) {
			model.addAttribute("chk","true");
		}
		model.addAttribute("detail", service.getDetail(id));
		model.addAttribute("real",socialService.getReal(id));
		model.addAttribute("memberList",socialService.getMember(id));
		return"club/detail";
	}
	
	
	
	
	@GetMapping(value = "/create")//�옉�꽦�뼇�떇
	public String createSocial(Model model,
			@SessionAttribute("loginData") UserDTO user) {

		System.out.println("컨트롤(create) 작성자 정보"+"\n"+user);
		model.addAttribute("field",socialService.getCategory());
		return "club/create";
	}
	
	@PostMapping(value = "/create")//�옉�꽦�맂 �뼇�떇 ���옣
	public String createSocial(HttpServletRequest request,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute ClubVO vo) {
		
		vo.setSocialNum(service.getSocialNum());
		vo.setId(user.getId());
		vo.setNickName(user.getNickName());

		System.out.println("컨트롤(create) 클럽 작성 내용"+"\n"+vo);

		System.out.println("컨트롤(create) 작성자 정보"+"\n"+user);

		
		int result=service.createClub(vo);
		
		switch(result) {
		case 9:
			return "error/error";
		case 8:
			service.deleteSoical(vo.getSocialNum());
			return "error/error";
		}
		
		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}

	@PostMapping(value="/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String delete(@RequestParam int id
			,@SessionAttribute("loginData") UserDTO user
			) {
		ClubDTO social = service.getDetail(id);
		
		JSONObject json = new JSONObject();

        if (social == null) {
            json.put("code", "notExists");
            json.put("message", "�씠誘� �궘�젣 �맂 �뜲�씠�꽣 �엯�땲�떎.");
        } else {
            if (true) {
                boolean result = service.deleteSoical(id);
                if (result) {
                    json.put("message", "�궘�젣 �셿猷�");
                } else {
                    json.put("code", "fail");
                    json.put("message", "�궘�젣 以� 臾몄젣諛쒖깮");
                }
            } else { // 愿�由ъ옄,湲��옉�꽦�옄 �쇅
                json.put("code", "permissionError");
                json.put("message", "�궘�젣沅뚰븳�씠 �뾾�뒿�땲�떎.");
            }
        }

        return json.toJSONString();
		
	}
	
	@GetMapping(value="modify")
	public String modify(Model model
			,@RequestParam int id) {
		
		model.addAttribute("field",socialService.getCategory());
		model.addAttribute("detail",service.getDetail(id));
		
		return "social/modify";
	}
	@PostMapping(value = "/modify")//�옉�꽦�맂 �뼇�떇 ���옣
	public String modifySocial(HttpServletRequest request,
			@ModelAttribute ClubVO vo) {
		

		System.out.println(vo);
		
		service.modifyClub(vo);
		
		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	@PostMapping(value="/entrust")
	public String entrust(@ModelAttribute ClubVO vo) {
		System.out.println(vo); 
		service.entrust(vo);

		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	
	@PostMapping(value="/outcast")
	public String outcast(@ModelAttribute ClubVO vo) {
		System.out.println(vo.getNickName()); 
		service.outcast(vo);

		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	
	
	@PostMapping(value="/join")
	public String join(@ModelAttribute ClubVO vo)throws Exception {
		System.out.println(vo); 
		service.join(vo);
		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	
	
}
