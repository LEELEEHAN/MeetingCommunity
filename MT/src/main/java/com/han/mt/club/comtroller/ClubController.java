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
import org.springframework.web.multipart.MultipartFile;

import com.han.mt.club.model.BoardDTO;
import com.han.mt.club.model.ClubDTO;
import com.han.mt.club.model.ClubVO;
import com.han.mt.club.service.ClubService;
import com.han.mt.fileUpload.model.FileUploadDTO;
import com.han.mt.fileUpload.service.FileUploadService;
import com.han.mt.social.service.SocialService;
import com.han.mt.user.model.UserDTO;
import com.han.mt.user.service.UserService;
import com.han.mt.util.Paging;


@Controller
@RequestMapping(value = "/club")
public class ClubController {

	@Autowired
	private ClubService service;
	@Autowired
	private SocialService socialService;
	@Autowired
	private UserService userService;
	@Autowired
	private FileUploadService fileUploadService;
	
	@GetMapping(value = "")
	public String getClub(Model model,HttpSession session,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String search,
			@RequestParam(defaultValue = "1", required = false) int page) {
		List list;
		if(search == null) {
			list =service.getClub(category);
			if(category!=null) {
			model.addAttribute("keyword",category);
			}
		} else {
			list =service.getClubTitle(search);
			model.addAttribute("search",search);	
		}
  
		System.out.println("컨트롤(getClub) 정보"+"\n"+list);
		Paging paging = new Paging(list, page, 10);
		model.addAttribute("field",socialService.getCategory());
		model.addAttribute("real",socialService.getReal());
		model.addAttribute("list",paging.getPageData());
		model.addAttribute("pageData", paging);
		model.addAttribute("type", "club");
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

		System.out.println("컨트롤(socialDetail) 정보"+"\n"+service.getDetail(id));
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
			@ModelAttribute ClubDTO dto,
			@RequestParam("fileUpload") MultipartFile[] files) throws Exception {

		System.out.println(files);
		int num =service.getNum();
		dto.setSocialNum(num);
		int result=service.createClub(dto,session,user);
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
        } else {
            if (true) {
                boolean result = service.deleteSoical(id,user.getEmail(),session);
                if (result) {
                    json.put("code", "success");
                } else {
                    json.put("code", "fail");
                }
            } else {
                json.put("code", "permissionError");
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
	
	
	
//	@PostMapping(value="/getBoard")
//    @ResponseBody
//	public String getBoard(@ModelAttribute BoardDTO dto,Model model){
//		System.out.println("컨트롤(getBorad) 주입값 dto :"+"\n"+dto); 
//		List<BoardDTO> board = service.getBoard(dto);
//		System.out.println("컨트롤(getBorad)보드 조회 :"+"\n"+board); 
//		model.addAttribute("boeard",board);		
//		return "redirect:/";
//	}

	@GetMapping(value="/board")
	public String board(@ModelAttribute BoardDTO dto,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user){
		System.out.println("컨트롤(getBorad) 주입값 dto :"+"\n"+dto); 
		List<BoardDTO> board = service.getBoard(dto,session);
		System.out.println("컨트롤(getBorad)보드 조회 :"+"\n"+board); 
		model.addAttribute("board",board);		
		model.addAttribute("socialNum",dto.getSocialNum());		
		model.addAttribute("category",dto.getCategory());		
		return "club/board";
	}

	@GetMapping(value="/board/write")
	public String write(@ModelAttribute BoardDTO dto,Model model,
			@SessionAttribute("loginData") UserDTO user){ 
		model.addAttribute("category",dto.getCategory());
		model.addAttribute("socialNum",dto.getSocialNum());	
		
		
		return "club/boardwrite";
	}

	@PostMapping(value="/board/write")
	public String write(HttpServletRequest request,
			@ModelAttribute BoardDTO dto,Model model,HttpSession session,
			@SessionAttribute("loginData") UserDTO user,
			@RequestParam("fileUpload") MultipartFile[] files) throws Exception {
		
		int num =service.getBoardNum();
		dto.setNickName(user.getNickName());
		dto.setWriter(user.getEmail());
		dto.setSocialNum(dto.getSocialNum());
		dto.setBoardNum(num);	
			service.boardAdd(dto);
			

			String type = "board";
			for(MultipartFile file: files) {
				String location = request.getServletContext().getRealPath("/resources/board/upload/");
				String url = "/static/board/upload";
				FileUploadDTO fileData = new FileUploadDTO(num, location, url, type,user.getEmail());
				
				try {
					int fileResult = fileUploadService.upload(file, fileData,type,session);
					System.out.println("컨트롤(fileResult) :" +fileResult);
					if(fileResult == -1) {
						service.deleteBoard(num);
						request.setAttribute("error", "파일업로드 초과.");
						return "club/boardwrite";
					}
				} catch(Exception e) {
					service.deleteBoard(num);
					request.setAttribute("error", "에러발생.");
					return "club/boardwrite";
				}
				
			}

			return "redirect:/club/board/detail?category="+dto.getCategory()+"&id="+dto.getBoardNum();
	}

	@GetMapping(value="/board/detail")
	public String boardDetail(Model model,
			@SessionAttribute("loginData") UserDTO user,
			@RequestParam int id,
			@RequestParam String category){
		BoardDTO list = service.getBoardDetail(id);
		model.addAttribute("socialNum",list.getSocialNum());	
		model.addAttribute("category",category);	
		model.addAttribute("data",list);	
		model.addAttribute("master",service.getDetail(list.getSocialNum()).getEmail());
		System.out.println("컨트롤(boardDetail)보드 조회 :"+"\n"+list);
		System.out.println(service.getDetail(list.getSocialNum()).getEmail());
		
		
		return "club/boardDetail";
	}

	@GetMapping(value="/board/modify")
	public String boardModify(Model model,
			@RequestParam int id){
		BoardDTO data= service.getBoardDetail(id);
		model.addAttribute("data",data);	
		System.out.println("컨트롤(boardDetail)보드 조회 :"+"\n"+data);	
		return "club/boardwrite";
	}
	

	@PostMapping(value="/board/modify")
	public String boardModify (@SessionAttribute("loginData") UserDTO user,
			@ModelAttribute BoardDTO dto){
		System.out.println("컨트롤(boardModify)보드 조회 :"+"\n"+dto);	
		service.boardModify(dto);

		return "redirect:/club/board/detail?category="+dto.getCategory()+"&id="+dto.getBoardNum();	
		}

	@PostMapping(value="/board/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
	public String deleteBoard(@RequestParam int id
			,@SessionAttribute("loginData") UserDTO user
			,HttpSession session
			) {
		ClubDTO social = service.getDetail(id);
		
		JSONObject json = new JSONObject();

        if (social == null) {
            json.put("code", "notExists");
        } else {
            if (true) {
                boolean result = service.deleteSoical(id,user.getEmail(),session);
                if (result) {
                    json.put("code", "success");
                } else {
                    json.put("code", "fail");
                }
            } else {
                json.put("code", "permissionError");
            }
        }

        return json.toJSONString();
		
	}
}
