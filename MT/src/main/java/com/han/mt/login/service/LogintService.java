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
		List<UserDTO> data = dao.login(vo);
		if(data.isEmpty()) {
			boolean SearchId = dao.SearchId(vo.getId());
			if(SearchId) {
				result = 2; // 2를 리턴시키면 아이디가 존재한다
			} else {
				result = 3; // 3를 리턴시키면 아이디가 존재하지않는다
			}
		} else {
			dao.loginLog(data.get(0));
			if(data.get(0).getNickName()==null) {
				result=4; // 닉네임설정을 안했다
			}
			session.setAttribute("loginData", data.get(0));
			session.setAttribute("loginId", data.get(0).getId());
		}
		return result; 
	}

	public boolean findId(HttpSession session, UserDTO dTO) throws Exception {
		String id = dao.findId(dTO);
		
		if(id==null) {
			session.setAttribute("findIdResult", "실패");
			return false;
		} else {
			session.setAttribute("findIdResult", "성공");
			session.setAttribute("id", id);
			return true;	
		}
		
		
	}

	public int idChk(UserDTO dto) throws Exception{
		int result = dao.idChk(dto);
		return result;
	}
	public int nickNameCheck(UserDTO dto) throws Exception{
		int result = dao.nickNameCheck(dto);
		return result;
	}

	public boolean signup(UserDTO dto, HttpSession session) throws Exception{
		boolean signup = dao.signup(dto);
		if(signup) {
			LoginVO vo = new LoginVO();
			vo.setId(dto.getId());
			vo.setPassword(dto.getPassword());
			List<UserDTO> data = dao.login(vo);
			session.setAttribute("loginData", data);
		}
		return signup;
	}

	
}
