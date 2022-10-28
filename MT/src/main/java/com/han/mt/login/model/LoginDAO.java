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


	public List<UserDTO> login(LoginVO vo) throws Exception{
		String mapperId =String.format(mapper,"login");
		List<UserDTO> data = session.selectList(mapperId,vo);	
		return data;
	}

	
	public boolean SearchId(String id) {

		String mapperId =String.format(mapper,"SearchId");
		UserDTO data = session.selectOne(mapperId,id);
		if(data==null) {
			return false;
		}
			
		return true;
	}

	public String findId(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"findId");
		String data = session.selectOne(mapperId,dto);
		return data;

	}

	public int idChk(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"idChk");
		int result = session.selectOne(mapperId,dto);
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

	public int nickNameCheck(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"nickNameCheck");
		int result = session.selectOne(mapperId,dto);
		return result;
	}
	
	
}
