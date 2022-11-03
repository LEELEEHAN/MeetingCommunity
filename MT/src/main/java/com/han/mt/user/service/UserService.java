package com.han.mt.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.social.model.SocialDTO;
import com.han.mt.user.model.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;

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
}
