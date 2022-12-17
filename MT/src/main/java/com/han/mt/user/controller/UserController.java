package com.han.mt.user.controller;

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
import com.han.mt.login.service.LogintService;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.FavCategory;
import com.han.mt.user.model.UserDTO;
import com.han.mt.user.service.FavCategoryService;
import com.han.mt.user.service.UserService;


@Controller 
@RequestMapping(value="/mypage")
public class UserController {

	/*
	  System.out.println();
	 */
	@Autowired
	private UserService service;
	@Autowired
	private LogintService loginService; 
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private SocialService socialService;
	
	@GetMapping(value="")
	public String myPage(HttpServletRequest request,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user) throws Exception {
		System.out.println("마이페이지 접속, 로그인 데이타 :"+user);

		
		String loginId = (String) session.getAttribute("loginId");
		model.addAttribute("joinClub",service.joinClub(loginId));
		model.addAttribute("joinSocial",service.joinSocial(loginId));		
		return"user/mypage";
	}

	@GetMapping(value="/modify")
	public String myPageModify(HttpServletRequest request,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user) throws Exception {
		System.out.println("마이페이지 수정페이지 접속, 로그인 데이타 :"+user);
		
		String loginId = (String) session.getAttribute("loginId");
		model.addAttribute("joinClub",service.joinClub(loginId));
		model.addAttribute("mypage","mypage");
		model.addAttribute("joinSocial",service.joinSocial(loginId));	
		return"user/mypage";
	}
	
	@PostMapping(value="/modify")
	public String myPageModify(HttpServletRequest request,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,@ModelAttribute UserDTO dto,
			@RequestParam("fileUpload") MultipartFile[] files) throws Exception {
		System.out.println("마이페이지수정 데이터 전송, 로그인 데이타 :"+dto);
		System.out.println("마이페이지수정 데이터 전송, files 데이타 :"+files);
		
		for(MultipartFile file: files) {
			String location = request.getServletContext().getRealPath("/resources/img/profile/");
			String url = "/static/img/profile";
			String type = "email";
			FileUploadDTO fileData = new FileUploadDTO(user.getEmail(), location, url, type,user.getEmail());			
			try {
				int fileResult = fileUploadService.upload(file, fileData,type,session);
				System.out.println("컨트롤(fileResult) :" +fileResult);
				if(fileResult == -1) {
					request.setAttribute("error", "파일업로드 초과.");
					return"user/mypage";
				}
			} catch(Exception e) {
				request.setAttribute("error", "에러발생.");
				return"user/mypage";
			}			
		}					
		return"user/mypage";
	}
	
	@PostMapping(value="/idChk")//아이디 중복체크
	@ResponseBody
	public int idChk(@RequestParam String nickName,HttpSession session,@RequestParam String type) throws Exception{
		System.out.println("컨트롤러(idChk) : 받은값 "+nickName);
		UserDTO dto = new UserDTO();
		dto.setNickName(nickName);
		int result = loginService.idChk(dto,session,type);
		System.out.println("컨트롤러(idChk) : 중복확인 결과"+result);		
		return result;
	}
	
	@PostMapping(value="/nickSave")//아이디 중복체크
	@ResponseBody
	public int nickSave(@RequestParam String nickName,HttpSession session,@RequestParam String email) throws Exception{
		System.out.println("컨트롤러(nickSave) : 받은값 "+nickName);
		int result = loginService.nickSave(nickName,session,email);
		System.out.println("컨트롤러(nickSave) : 반영 결과"+result);		
		return result;
	}

	@PostMapping(value="/nickChk")//아이디 중복체크
	@ResponseBody
	public int nickChk(@RequestParam String nickName,HttpSession session,@RequestParam String email) throws Exception{
		System.out.println("컨트롤러(nickChk) : 받은값 "+nickName);
		int result = loginService.nickChk(nickName,session,email);
		System.out.println("컨트롤러(nickChk) : 반영 결과"+result);	
		return result;
	}
	
	
	@PostMapping(value="/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String deleteUser(@SessionAttribute("loginData") UserDTO user
			,HttpSession session,@RequestParam String email
			) {
		JSONObject json = new JSONObject();

       
        if (true) {
            boolean result = service.deleteUser(user.getEmail(),session);
            if (result) {
                json.put("code", "success");
            } else {
                json.put("code", "fail");
            }
        }
        

        return json.toJSONString();
		
	}

	@GetMapping(value="/passChange") //로그인 화면 띄우기
	public String login (HttpSession session) {	
		System.out.println("컨트롤(login) 로그인 메서드 동작");
		return"user/passChange";		
	}
	
	
	
	@PostMapping(value="/favorite")
	public String favorite(HttpServletRequest request,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,@ModelAttribute UserDTO dto,
			@RequestParam("category") FavCategory[] cates) throws Exception {
		System.out.println("마이페이지수정 데이터 전송, 로그인 데이타 :"+user);
		System.out.println("마이페이지수정 데이터 전송, files 데이타 :"+cates);
		
		for(FavCategory cate: cates) {
			service.setCategory(cate,user.getEmail());
		}					
		return"user/mypage";
	}
	
	@GetMapping(value="/favorite")
	public String favorite(HttpServletRequest request,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user) throws Exception {
		System.out.println("마이페이지수정 데이터 전송, 로그인 데이타 :"+user);

		model.addAttribute("field",socialService.getCategory());
		return"user/favorite";
	}
	
}
