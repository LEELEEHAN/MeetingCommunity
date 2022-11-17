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

import com.han.mt.club.model.BoardDTO;
import com.han.mt.club.model.ClubDTO;
import com.han.mt.club.model.ClubVO;
import com.han.mt.club.service.ClubService;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;
import com.han.mt.user.service.UserService;


@Controller
@RequestMapping(value = "/club")
public class ClubController {

	@Autowired
	private ClubService service;
	@Autowired
	private SocialService socialService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "")
	public String getClub(Model model,HttpSession session,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String search) {

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
		
		if(session.getAttribute("loginId")!=null) {
			String loginId = session.getAttribute("loginId").toString();	
			boolean chk = service.joinChk(loginId,id);
			if(chk) {
				model.addAttribute("chk","true");
			}
		}
		
		BoardDTO dto = new BoardDTO();
		dto.setSocialNum(id);
		dto.setCategory("I");		
		
		model.addAttribute("detail", service.getDetail(id));
		model.addAttribute("real",socialService.getReal(id));
		model.addAttribute("memberList",socialService.getMember(id));
		model.addAttribute("board",service.getBoard(dto));
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
			@ModelAttribute ClubDTO dto) {
		
		dto.setSocialNum(service.getSocialNum());
		dto.setEmail(user.getEmail());
		dto.setNickName(user.getNickName());

		System.out.println("컨트롤(create) 클럽 작성 내용"+"\n"+dto);

		System.out.println("컨트롤(create) 작성자 정보"+"\n"+user);

		
		int result=service.createClub(dto,session);
		
		switch(result) {
		case 9:
			return "error/error";
		case 8:
			service.deleteSoical(dto.getSocialNum(),user.getEmail(),session);
			return "error/error";
		}
		
		return "redirect:/club/detail?id=" + dto.getSocialNum();
	}

	@PostMapping(value="/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String delete(@RequestParam int id
			,@SessionAttribute("loginData") UserDTO user
			,HttpSession session
			) {
		ClubDTO social = service.getDetail(id);
		
		JSONObject json = new JSONObject();

        if (social == null) {
            json.put("code", "notExists");
            json.put("message", "�씠誘� �궘�젣 �맂 �뜲�씠�꽣 �엯�땲�떎.");
        } else {
            if (true) {
                boolean result = service.deleteSoical(id,user.getEmail(),session);
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
	public String entrust(@ModelAttribute ClubVO vo,HttpSession session) {
		System.out.println(vo); 
		service.entrust(vo,session);

		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	
	@PostMapping(value="/outcast")
	public String outcast(@ModelAttribute ClubVO vo,HttpSession session) {
		System.out.println(vo.getNickName()); 
		service.outcast(vo,session);

		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	
	
	@PostMapping(value="/join")
	public String join(@ModelAttribute ClubVO vo,HttpSession session)throws Exception {
		System.out.println(vo); 
		service.join(vo,session);

		session.removeAttribute("joinSocial");
		session.setAttribute("joinSocial",userService.joinSocial(vo.getId()));
		return "redirect:/club/detail?id=" + vo.getSocialNum();
	}
	
	
	
	@PostMapping(value="/getBoard")
    @ResponseBody
	public String getBoard(@ModelAttribute BoardDTO dto,Model model){
		System.out.println("컨트롤(getBorad) 주입값 dto :"+"\n"+dto); 
		List<BoardDTO> board = service.getBoard(dto);
		System.out.println("컨트롤(getBorad)보드 조회 :"+"\n"+board); 
		model.addAttribute("boeard",board);
		
		
		return "redirect:/";
	}
	
}
