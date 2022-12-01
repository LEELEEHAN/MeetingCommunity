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

	
	public UserDTO getAdminLogin(LoginVO vo) throws Exception{
		String mapperId =String.format(mapper,"getAdminLogin");
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

	public String findId(UserDTO dto) throws Exception{
		String mapperId =String.format(mapper,"findId");
		String data = session.selectOne(mapperId,dto);
		return data;
	}

	public boolean idChk(String email) throws Exception{
		String mapperId =String.format(mapper,"idChk");
		boolean result =false;
		int idChk= session.selectOne(mapperId,email);
		System.out.println("DAO(idChk) 아이디 중복조뢰 리스트:"+idChk);
		if(idChk<1){
			result = true;
		}
		System.out.println("DAO(idChk) 아이디 중복 확인 결과:"+result);
		return result;
	}

	public boolean signup(UserDTO dto) {
		String mapperId =String.format(mapper,"signup");
		int result = session.insert(mapperId,dto);
		return result == 1? true: false;
	}
	public boolean kakaoSignup(UserDTO dto) {
		String mapperId =String.format(mapper,"kakaoSignup");
		int result = session.insert(mapperId,dto);
		return result == 1? true: false;
	}

	public void loginLog(UserDTO data) {
		String mapperId =String.format(mapper,"loginLog");
		session.update(mapperId,data);		
	}

	public boolean nickNameCheck(String nickName) throws Exception{
		String mapperId =String.format(mapper,"nickNameCheck");
		boolean result;
		int idChk= session.selectOne(mapperId,nickName);
		if(idChk<1){
			result = true;
		}else {
			result = false;
		}
		System.out.println("DAO(nickNameCheck) 중복닉네임 조회결과 :" +result);
		return result;
	}

	public boolean setNickName(UserDTO dto) {
		String mapperId =String.format(mapper,"setNickName");
		int result = session.update(mapperId,dto);
		System.out.println("DAO(setNickName) 닉네임 설정결과 :" +result);

		return result==1?true:false;
	}

	public boolean emailRes(String id) {
		String mapperId =String.format(mapper,"emailRes");
		int result = session.insert(mapperId,id);
		return false;
	}

	public boolean signupDetail(String email) {
		String mapperId =String.format(mapper,"signupDetail");
		int result = session.insert(mapperId,email);
		System.out.println("DAO(signupDetail) 결과 :" +result);
		
		return result ==1? true : false;
	}

	public void dropId(String email) {
		String mapperId =String.format(mapper,"signupDetail");
		session.delete(mapperId,email);
		
		
	}

	public UserDTO kakaoLogin(String email) {
		System.out.println("카카오 로그인 DAO 10"+email);
		String mapperId =String.format(mapper,"kakaoLogin");
		System.out.println("카카오 로그인 DAO 11"+mapperId);
		UserDTO data = session.selectOne(mapperId,email);
		System.out.println("카카오 로그인 DAO 13"+data);
		return data;
	}


	public int nickChk(UserDTO data) {
		String mapperId =String.format(mapper,"nickChk");
		int rrr = session.selectOne(mapperId,data);
		System.out.println("DAO(nickChk) : rrr "+rrr);
		return rrr;
	}

	public int nickSave(UserDTO data) {
		String mapperId =String.format(mapper,"setNickName");
		System.out.println("DAO(nickSave) : data "+data);
		int result = session.update(mapperId,data);
		System.out.println("DAO(nickSave) : result "+result);
		return result;
	}


	public UserDTO getLogin(String email) throws Exception{
		String mapperId =String.format(mapper,"searchUserEmail");
		UserDTO data = session.selectOne(mapperId,email);
		return data;
	}
	
}
