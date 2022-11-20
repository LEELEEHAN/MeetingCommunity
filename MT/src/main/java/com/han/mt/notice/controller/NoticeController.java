package com.han.mt.notice.controller;

import java.util.List;

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
import com.han.mt.notice.model.NoticeBoardDTO;
import com.han.mt.notice.service.NoticeService;
import com.han.mt.user.model.UserDTO;
import com.han.mt.util.Paging;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {

	@Autowired
	private NoticeService service; 
	

	@GetMapping(value="") //로그인 화면 띄우기
	public String notice (@RequestParam(defaultValue="info") String category,
			Model model,@RequestParam(defaultValue = "1", required = false) int page) {	
		service.getList(category);
		List list = service.getList(category);
		Paging paging = new Paging(list, page, 10);
		model.addAttribute("category", category);
		model.addAttribute("list",paging.getPageData());
		model.addAttribute("pageData", paging);
		return"notice/notice";		
	}

	@GetMapping(value="/write") //로그인 화면 띄우기
	public String noticeWrite (Model model) {		
		return"notice/noticeWrite";		
	}
	
	@PostMapping(value="/write") //로그인 화면 띄우기
	public String noticeWrite (@SessionAttribute("loginData") UserDTO user
			,@ModelAttribute NoticeBoardDTO dto) {	
		System.out.println("컨트롤(noticeWrite) : 받은 내용" +dto);

		int num = service.getNoticeNum();
		dto.setNoticeNum(num);
		service.write(dto);
		
		return "redirect:/notice/detail?id=" + dto.getNoticeNum();
	}
	
	@GetMapping(value="/detail") //로그인 화면 띄우기
	public String getDetail (Model model,@RequestParam int id) {	
		
		NoticeBoardDTO data = service.getDetail(id);
		model.addAttribute("data",data);
		System.out.println("컨트롤(login) : 로그인 메서드 동작");			
		return"notice/detail";		
	}
	
	
	@GetMapping(value = "/modify")
	public String modify(Model model, @SessionAttribute("loginData") UserDTO user
			,@RequestParam int id,
			@RequestParam(required = false) String category) {
		System.out.println("컨트롤(modify)작동 받은값 " +id);	

		NoticeBoardDTO data = service.getDetail(id);
		data.setNoticeNum(id);
		System.out.println("컨트롤(modify) 불러온 데이터"+"\n"+data);
		model.addAttribute("data", data);	
		model.addAttribute("id", id);	
		model.addAttribute("getCategory", data.getCategory());	
		System.out.println("컨트롤(modify) 불러온 카테고리"+"\n"+ data.getCategory());
		return "notice/noticeWrite";
	}
	
	@PostMapping(value="/modify") //로그인 화면 띄우기
	public String modify (@SessionAttribute("loginData") UserDTO user
			,@ModelAttribute NoticeBoardDTO dto) {	
		System.out.println("컨트롤(modify) 포스트로 받은 내용" +dto);
		service.modify(dto);
		
		return "redirect:/notice/detail?id=" + dto.getNoticeNum();
	}
	
	@PostMapping(value="/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String delete(@RequestParam int id
			,@SessionAttribute("loginData") UserDTO user
			,HttpSession session
			) {
		NoticeBoardDTO data = service.getDetail(id);
		
		JSONObject json = new JSONObject();

        if (data == null) {
            json.put("code", "notExists");
        } else {
            if (true) {
                boolean result = service.deleteNotice(id,session);
                if (result) {
                    json.put("code", "success");
                } else {
                    json.put("code", "fail");
                }
            } else { // 愿�由ъ옄,湲��옉�꽦�옄 �쇅
                json.put("code", "permissionError");
            }
        }

        return json.toJSONString();
		
	}
	
}
