package com.han.mt.user.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.login.model.LoginDAO;
import com.han.mt.social.model.SocialDTO;
import com.han.mt.user.model.FavCategory;
import com.han.mt.user.model.UserDAO;
import com.han.mt.user.model.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;
	@Autowired
	private LoginDAO loginDao;
	public List<SocialDTO> joinClub(String loginId) {
		List<SocialDTO> joinClub = dao.joinClub(loginId);
		System.out.println("����(joinClub) ������ Ŭ������Ʈ:" +joinClub);
		return joinClub;
		
	}

	public List<SocialDTO> joinSocial(String loginId) {
		List<SocialDTO> joinSocial = dao.joinSocial(loginId);
		System.out.println("����(joinClub) ������ ��ȸ���Ʈ :" +joinSocial);
		return joinSocial;
	}

	public void updateProfile(UserDTO dto,HttpSession session) throws Exception {
		dao.updateProfile(dto);

		session.removeAttribute("loginData");
		System.out.println("����(updateProfile): �α׾ƿ�");
		session.setAttribute("loginData", loginDao.getLogin(dto.getEmail()));
		System.out.println("����(updateProfile): �α���");
	}

	public boolean deleteUser(String email, HttpSession session) {
		
		boolean result = dao.deleteUser(email);
		if(result) {
			session.removeAttribute("loginData");
			session.removeAttribute("loginId");
			session.removeAttribute("joinSocial");
			session.removeAttribute("joinClub");
			session.removeAttribute("adminAcc");
			session.removeAttribute("image");		
		}
		
		return result;
	}

	public void setCategory(FavCategory cate, String email) {
		FavCategory dto = new FavCategory();
		dao.setCategory(dto);
		
	}
}
