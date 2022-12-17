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

	public void updateProfile(UserDTO dto) {
		String mapperId =String.format(mapper,"updateProfile");
		 session.update(mapperId,dto);
	}

	public boolean deleteUser(String email) {
		String mapperId =String.format(mapper,"deleteUser");
		 int re = session.update(mapperId,email);
		return re ==1? true: false;
	}

	public void setCategory(FavCategory dto) {
		String mapperId =String.format(mapper,"setCategory");
		session.insert(mapperId,dto);
		
	}

}
