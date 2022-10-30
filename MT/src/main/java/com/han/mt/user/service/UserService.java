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
		System.out.println("서비스(joinClub) 가져온 클럽리스트 :" +joinClub);
		return joinClub;
		
	}

	public List<SocialDTO> joinSocial(String loginId) {
		List<SocialDTO> joinSocial = dao.joinSocial(loginId);
		System.out.println("서비스(joinClub) 참가한 쇼셜리스트 :" +joinSocial);
		return joinSocial;
	}
}
