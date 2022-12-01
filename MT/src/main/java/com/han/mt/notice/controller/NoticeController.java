package com.han.mt.notice.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.han.mt.club.model.ClubDTO;
import com.han.mt.fileUpload.model.FileUploadDTO;
import com.han.mt.fileUpload.service.FileUploadService;
import com.han.mt.notice.model.NoticeBoardDTO;
import com.han.mt.notice.service.NoticeService;
import com.han.mt.user.model.UserDTO;
import com.han.mt.util.Paging;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {

	@Autowired
	private NoticeService service; 

	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping(value="") //로그인 화면 띄우기
	public String notice (@RequestParam(defaultValue="info") String category,
			@RequestParam(required = false) String search,
			Model model,@RequestParam(defaultValue = "1", required = false) int page) {	
		service.getList(category);
		List list = service.getList(category);
		if(search == null) {
			list =service.getList(category);
		} else {
			list =service.getListTitle(search);
			String field="";				
			if(category.equals("info")) {
				field= "정보";			
				} else if(category.equals("notice")) {
					field= "공지";				
				} else if(category.equals("event")) {
					field= "이벤트";				
				}

			model.addAttribute("search", field);	
			model.addAttribute("key", search);			
		}
		
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
	public String noticeWrite (HttpServletRequest request,HttpSession session,
			@SessionAttribute("loginData") UserDTO user
			,@ModelAttribute NoticeBoardDTO dto,
			@RequestParam("fileUpload") MultipartFile[] files) throws Exception {
		

		int num = service.getNoticeNum();
		dto.setNoticeNum(num);
		System.out.println("컨트롤(noticeWrite) : 받은 내용" +dto);
		System.out.println(files.length);
		service.write(dto);
		for(MultipartFile file: files) {
			String location = request.getServletContext().getRealPath("/resources/board/upload/");
			String url = "/static/board/upload";
			String type = "notice";
			FileUploadDTO fileData = new FileUploadDTO(num, location, url, type,user.getEmail());
			
			try {
				int fileResult = fileUploadService.upload(file, fileData,type,session);
				System.out.println("컨트롤(fileResult) :" +fileResult);
				if(fileResult == -1) {
					service.deleteNotice(num,session);
					request.setAttribute("error", "파일업로드 초과.");
					return"notice/noticeWrite";		
				}
			} catch(Exception e) {
				service.deleteNotice(num,session);
				request.setAttribute("error", "에러발생.");
				return"notice/noticeWrite";		
			}
			
		}
		
		
		
		
		return "redirect:/notice/detail?id=" + dto.getNoticeNum();
	}
	
	@GetMapping(value="/detail") //로그인 화면 띄우기
	public String getDetail (Model model,@RequestParam int id) {	
		
		NoticeBoardDTO data = service.getDetail(id);
		model.addAttribute("data",data);
		model.addAttribute("download", fileUploadService.getDatas(id, "notice"));
		System.out.println("컨트롤(detail) : "+"\n"+id+"번째 공지 다운로드 파일 리스트"+ fileUploadService.getDatas(id, "notice"));			
		System.out.println("컨트롤(getDetail) : 공지 디테일 동작");			
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
