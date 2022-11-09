package com.han.mt.login.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;

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
				result=4; // �땳�꽕�엫�꽕�젙�쓣 �븞�뻽�떎
			}
			System.out.println("서비스(getLogin) : 아이디를 찾아 세션에 정보저장");
			session.setAttribute("loginData", data);
			session.setAttribute("loginId", data.getEmail());
			
		} else {
			boolean searchId = dao.searchId(vo.getEmail());
			System.out.println(searchId);
			System.out.println("서비스(getLogin) : 로그인 실패 원인 찾음");

			if(searchId) {
				result = 2; // 2, 아이디는 존재
			} else {
				result = 3;  // 3, 아이디가 존재하질 않음
			}
		}
		return result; 
	}

	public String findId(HttpSession session, UserDTO dto) throws Exception {
		String idList = dao.findId(dto);
		System.out.println("서비스(findId) 아이디 중복조뢰 리스트:"+idList);

		if(idList==null) {
			System.out.println("아이디 조회 실패 세션저장");
			session.setAttribute("findIdResult", "해당계정이 없습니다");
		} else {
			System.out.println("받은 값으로 조회결과"+idList);
			session.setAttribute("findId", idList);
		}
		return idList;	
		
	}

	public int idChk(String email
			, HttpSession session) throws Exception{
		int chek;
		boolean result = dao.idChk(email);
		System.out.println("서비스(idChk) 아이디 중복 확인 결과:"+result);
		session.setAttribute("idCheckResult", result);
		if(result) {
			chek =1;
		} else {
			chek=0;
		}
		return chek;
	}
	
	
	public int nickNameCheck(UserDTO dto
			, HttpSession session) throws Exception{
		int chek;
		boolean result = dao.nickNameCheck(dto);
		System.out.println("서비스(nickNameCheck) 닉네임 중복 확인 결과:"+result);
		session.setAttribute("nickCheckResult", result);
		if(result) {
			chek =1;
		} else {
			chek=0;
		}
		return chek;
	} 

	
	
	public boolean signup(UserDTO dto, HttpSession session) throws Exception{
		boolean signup = dao.signup(dto);
		if(signup) {
			LoginVO vo = new LoginVO();
			vo.setEmail(dto.getEmail());
			vo.setPassword(dto.getPassword());
			UserDTO data = dao.getLogin(vo);
			session.setAttribute("loginData", data);
		}
		return signup;
	}

	public boolean sign(UserDTO dto
			,HttpSession session) throws Exception {
		
		boolean result = dao.nickNameCheck(dto);
		
		boolean setNickName;
		if(result ==true) {			
			setNickName = dao.setNickName(dto);
			System.out.println("서비스(setNickName) 닉네임 설정결과 :" +setNickName);

		} else {
			setNickName= false;
		}
		return setNickName;
	}

	public boolean emailRes(String id, HttpSession session) {
		boolean result = dao.emailRes(id);
		return result;
	}

	
}
