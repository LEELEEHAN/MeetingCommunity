package com.han.mt.login.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.login.model.LoginDAO;
import com.han.mt.login.model.LoginVO;
import com.han.mt.user.model.UserDTO;

@Service
public class LogintService {

	@Autowired
	private LoginDAO dao;
	 
	public int getLogin(LoginVO vo, HttpSession session) throws Exception {
		int result = 1; 
		System.out.println("서비스(getLogin) : 로그인 메서드 실행");
		boolean result2 = dao.login(vo);
		if(result2) {
			UserDTO data = dao.getLogin(vo); 
			dao.loginLog(data);
			System.out.println("서비스(getLogin) : 로그인 로그 저장");
			if(data.getNickName()==null) { 
				result=4; // 닉네임설정을 안했다
			}
			System.out.println("서비스(getLogin) : 아이디를 찾아 세션에 정보 저장");
			session.setAttribute("loginData", data);
			session.setAttribute("loginId", data.getId());
			
		} else {
			boolean searchId = dao.searchId(vo.getId());
			System.out.println(searchId);
			System.out.println("서비스(getLogin) : 로그인 실패 원인을 찾음");

			if(searchId) {
				result = 2; // 2를 리턴시키면 아이디가 존재한다
			} else {
				result = 3; // 3를 리턴시키면 아이디가 존재하지않는다
			}
		}
		return result; 
	}

	public List<String> findId(HttpSession session, UserDTO dto) throws Exception {
		List idList = dao.findId(dto);

		if(idList.isEmpty()) {
			System.out.println("아이디 조회실패 세션저장");
			session.setAttribute("findIdResult", "해당계정이 없습니다");
		} else {
			System.out.println("받은 값으로 조회결과"+idList);
		}
		return idList;	
		
	}

	public boolean idChk(UserDTO dto
			, HttpSession session) throws Exception{
		boolean result = dao.idChk(dto);
		session.setAttribute("idCheckResult", result);
		return result;
	}
	public boolean nickNameCheck(UserDTO dto
			, HttpSession session) throws Exception{
		boolean result = dao.nickNameCheck(dto);
		session.setAttribute("nickCheckResult", result);
		return result;
	} 

	public boolean signup(UserDTO dto, HttpSession session) throws Exception{
		boolean signup = dao.signup(dto);
		if(signup) {
			LoginVO vo = new LoginVO();
			vo.setId(dto.getId());
			vo.setPassword(dto.getPassword());
			UserDTO data = dao.getLogin(vo);
			session.setAttribute("loginData", data);
		}
		return signup;
	}

	
}
