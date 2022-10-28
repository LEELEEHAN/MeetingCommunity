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
import com.han.mt.user.model.UserDTO;


@Controller
@RequestMapping(value = "/club")
public class ClubController {

	@Autowired
	private ClubService service;
	
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
		
		model.addAttribute("field",service.getCategory());
		model.addAttribute("real",service.getReal());
		return "club/social";
	}
	
	
	@GetMapping(value = "/detail")
	public String socialDetail(Model model,HttpSession session,
			@RequestParam(required = false) int id) {
		String loginId = (String) session.getAttribute("loginId");
	
//		model.addAttribute("clubList",service.cluList(loginId));
		
		boolean chk= service.joinChk(loginId,id);
		
		model.addAttribute("detail", service.getDetail(id));
		model.addAttribute("real",service.getReal(id));
		model.addAttribute("memberList",service.getMember(id));
		return"club/detail";
	}
	
	
	
	
	@GetMapping(value = "/create")//작성양식
	public String createSocial(Model model,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute ClubVO vo) {

		System.out.println(user);
		System.out.println("겟 크리에이트 브이오"+vo);
		model.addAttribute("field",service.getCategory());
		return "club/create";
	}
	
	@PostMapping(value = "/create")//작성된 양식 저장
	public String createSocial(HttpServletRequest request,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute ClubVO vo) {
		
		vo.setSocialNum(service.getSocialNum());
		vo.setId(user.getId());
		vo.setNickName(user.getNickName());

		
		System.out.println("포스트 크리에이트 브이오"+vo);
		System.out.println("포스트 크리에이트 유저"+user);

		
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
