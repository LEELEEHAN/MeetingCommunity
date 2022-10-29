package com.han.mt.login.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.han.mt.user.model.UserDTO;

@Repository
public class LoginDAO {


	@Autowired
	private SqlSession session;
	
	private String mapper = "loginMapper.%s";


	public boolean login(LoginVO vo) throws Exception{
		String mapperId =String.format(mapper,"login");
		List<UserDTO> data = session.selectList(mapperId,vo);
		boolean resutl;
		if(data.isEmpty()) {
			resutl = false;
		}else {
			resutl =true;
		}

		return resutl;
	}
	
	public UserDTO getLogin(LoginVO vo) throws Exception{
		String mapperId =String.format(mapper,"login");
		UserDTO data = session.selectOne(mapperId,vo);
		return data;
	}

	
	public boolean searchId(String id) {
		String mapperId =String.format(mapper,"searchId");
		List<UserDTO> data = session.selectList(mapperId,id);
		if(data.isEmpty()) {
			return false;
		}			
		return true;
	}

	public List<String> findId(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"findId");
		List<String> data = session.selectList(mapperId,dto);
		return data;
	}

	public boolean idChk(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"idChk");
		boolean result =false;
		List<String> idChk= session.selectList(mapperId,dto);
		if(idChk.isEmpty()) {
			result = true;
		}
		return result;
	}

	public boolean signup(UserDTO dto) {
		String mapperId =String.format(mapper,"signup");
		int result = session.insert(mapperId,dto);
		
		return result == 1? true: false;
	}

	public void loginLog(UserDTO data) {
		String mapperId =String.format(mapper,"loginLog");
		session.update(mapperId,data);
		
		
	}

	public boolean nickNameCheck(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"nickNameCheck");
		boolean result =false;
		List<String> nick= session.selectList(mapperId,dto);
		if(nick.isEmpty()) {
			result = true;
		}
		return result;
	}
	
	
}
