package com.han.mt.user.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.han.mt.social.model.SocialDTO;

@Repository
public class UserDAO {

	@Autowired 
	private SqlSession session;
	private String mapper = "userMapper.%s";
	
	public List<SocialDTO> joinClub(String loginId) {
		String mapperId =String.format(mapper,"joinClub");
		List<SocialDTO> data = session.selectList(mapperId,loginId);
		return data;
	}

	public List<SocialDTO> joinSocial(String loginId) {
		String mapperId =String.format(mapper,"joinSocial");
		List<SocialDTO> data = session.selectList(mapperId,loginId);
		return data;
	}

}
