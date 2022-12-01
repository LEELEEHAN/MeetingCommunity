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
import org.springframework.web.multipart.MultipartFile;

import com.han.mt.fileUpload.model.FileUploadDTO;
import com.han.mt.fileUpload.service.FileUploadService;
import com.han.mt.social.model.SocialCommentDTO;
import com.han.mt.social.model.SocialDTO;
import com.han.mt.social.model.SocialDynamicDTO;
import com.han.mt.social.model.SocialMemberDTO;
import com.han.mt.social.model.SocialVO;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;
import com.han.mt.user.service.UserService;
import com.han.mt.util.Paging;

@Controller
@RequestMapping(value = "/social")
public class SocialController {
	private static final Logger logger = LoggerFactory.getLogger(SocialController.class);

	@Autowired
	private SocialService service;
	@Autowired
	private UserService userService;

	@Autowired
	private FileUploadService fileUploadService;
	
	@GetMapping(value = "")
	public String getSocial(Model model,HttpSession session,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String search,
			@RequestParam(defaultValue = "1", required = false) int page) {
		List list;
		if(search == null) {
			list =service.getSocial(category);
			if(category!=null) {
			model.addAttribute("keyword",category);
			}
		} else {
			list =service.getSocialTitle(search);
			model.addAttribute("search",search);
		}
		

		Paging paging = new Paging(list, page, 10);
		model.addAttribute("field",service.getCategory());
		model.addAttribute("real",service.getReal());
		model.addAttribute("list",paging.getPageData());
		model.addAttribute("pageData", paging);
		model.addAttribute("type", "social");
		return "social/social";
	}
	
	
	
	
	@GetMapping(value = "/detail")
	public String socialDetail(Model model,HttpSession session,
			@RequestParam(required = false) int id) {
		if(session.getAttribute("loginId")!=null) {
		String loginId = session.getAttribute("loginId").toString();
		System.out.println("컨트롤(getdetail)"+"\n"+service.getDetail(id));
		boolean chk = service.joinChk(loginId,id);
			if(chk) {
				model.addAttribute("chk","true");
			}
		}
		
		
		model.addAttribute("detail", service.getDetail(id));
		model.addAttribute("real",service.getReal(id));
		model.addAttribute("memberList",service.getMember(id));
		model.addAttribute("comment",service.getComment(id));
		
		
		return"social/detail";
	}
	
	
	
	
	@GetMapping(value = "/create")//�옉�꽦�뼇�떇
	public String createSocial(Model model,
			@SessionAttribute("loginData") UserDTO user) {

		System.out.println("컨트롤(create) 작성자 정보"+"\n"+user);
		model.addAttribute("field",service.getCategory());
		return "social/create";
	}
	
	@PostMapping(value = "/create")
	public String createSocial(HttpServletRequest request,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute SocialVO vo,
			@ModelAttribute SocialDTO dto,
			@RequestParam("fileUpload") MultipartFile[] files) throws Exception {
		int num =service.getSocialNum();
		vo.setSocialNum(num);
		dto.setSocialNum(vo.getSocialNum());
		vo.setId(user.getEmail());
		vo.setNickName(user.getNickName());


		System.out.println("컨트롤(create) 쇼셜로 할 내용"+"\n"+vo);
		System.out.println("컨트롤(create) 쇼셜로 할 내용"+"\n"+dto);
		System.out.println("컨트롤(create) 작성자 정보"+"\n"+user);
	
		int result=service.createSocial(vo,dto,session);

		String type = "social";
		for(MultipartFile file: files) {
			String location = request.getServletContext().getRealPath("/resources/board/upload");
			String url = "/static/board/upload";
			FileUploadDTO fileData = new FileUploadDTO(num, location, url, type,user.getEmail());
			
			try {
				int fileResult = fileUploadService.upload(file, fileData,type,session);
				System.out.println("컨트롤(fileResult) :" +fileResult);
				if(fileResult == -1) {
					service.deleteSoical(num,user.getEmail(),session);
					request.setAttribute("error", "파일업로드 초과.");
					return "social/create";
				}
			} catch(Exception e) {
				service.deleteSoical(num,user.getEmail(),session);
				request.setAttribute("error", "에러발생.");
				return "social/create";
			}
			
		}
		
		switch(result) {
		case 9:
			service.deleteSoical(dto.getSocialNum(),user.getEmail(),session);
			request.setAttribute("error", "에러발생.");
			return "social/create";
		case 8:
			service.deleteSoical(dto.getSocialNum(),user.getEmail(),session);
			request.setAttribute("error", "에러발생.");
			return "social/create";
		}
		
		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}

	@PostMapping(value="/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String delete(@RequestParam int id
			,@SessionAttribute("loginData") UserDTO user,
			HttpSession session
			) {
		SocialDTO social = service.getDetail(id);
		
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
		model.addAttribute("field",service.getCategory());
		model.addAttribute("detail",service.getDetail(id));	
		return "social/modify";
	}
	@PostMapping(value = "/modify")
	public String modifySocial(HttpServletRequest request,
			@ModelAttribute SocialVO vo) {
		System.out.println(vo);
		service.modifySocial(vo);		
		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	
	@PostMapping(value="/entrust")
	public String entrust(@ModelAttribute SocialVO vo,HttpSession session) {
		System.out.println("컨트롤(entrust) 받은 값"+vo); 
		service.entrust(vo ,session);
		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	@PostMapping(value="/outcast")
	public String outcast(Model model,@ModelAttribute SocialVO vo) {
		System.out.println(vo.getNickName()); 
		System.out.println("컨트롤(outcast) 받은 값"+vo); 

		model.addAttribute("vo",vo);

		System.out.println("컨트롤(outcast) 설정한 값"+model.getAttribute("vo")); 

        return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	
	@PostMapping(value="/join")
	public String join(@ModelAttribute SocialVO vo,HttpSession session)throws Exception {
		System.out.println(vo); 
		service.join(vo,session);

		session.removeAttribute("joinSocial");
		session.setAttribute("joinSocial",userService.joinSocial(vo.getId()));
		return "redirect:/social/detail?id=" + vo.getSocialNum();
	}
	
	

	@PostMapping(value="/onComment")
	public String onComment(@ModelAttribute SocialCommentDTO dto)throws Exception {
		System.out.println(dto); 
		dto.setCommentNum(service.getCommentNum());
		service.onComment(dto);

		return "redirect:/social/detail?id=" + dto.getSocialNum();
	}
	
	
	
}
