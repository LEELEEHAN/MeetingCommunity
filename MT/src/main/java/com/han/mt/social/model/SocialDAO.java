package com.han.mt.social.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.han.mt.club.model.ClubVO;
import com.han.mt.user.model.UserDTO;

@Repository
public class SocialDAO {
	
	@Autowired // 어노테이션 밑줄친 객체에 의존하기 위해만든다
	private SqlSession session;

	private String mapper = "socialMapper.%s";
	private String userMapper = "userMapper.%s";

	public List<SocialDTO> getSocial(String category) {
		String mapperId =String.format(mapper,"getSocial");
		List<SocialDTO> social = session.selectList(mapperId,category);
		System.out.println("카테고리값입니"+category);
		return social;
	}
	public List<SocialDTO> getSocialTitle(String title) {
		String mapperId =String.format(mapper,"getSocialTitle");
		List<SocialDTO> social = session.selectList(mapperId,title);
		System.out.println("카테고리값입니"+title);
		return social;
	}
	
	
	public List<FieldCategory> getCategory(){
		String mapperId =String.format(mapper,"getCategory");
		List<FieldCategory> category = session.selectList(mapperId);
		return category;
	}

	public List<SocialDynamicDTO> getReal() {
		String mapperId =String.format(mapper,"getSocialMemberCount");
		List<SocialDynamicDTO> real = session.selectList(mapperId);
		return real;
	}
	
	public int getReal(int socialNum) {
		String mapperId =String.format(mapper,"getSocialMemberCountSocial");
		int real = session.selectOne(mapperId,socialNum);
		return real;
	}

	public SocialDTO getDetail(int socialNum) {
		String mapperId =String.format(mapper,"getDetail");
		SocialDTO detail = session.selectOne(mapperId,socialNum);
		return detail;
	}

	public List<SocialMemberDTO> getMemberList(int socialNum) {
		String mapperId =String.format(mapper,"getMemberList");
		List<SocialMemberDTO> list = session.selectList(mapperId,socialNum);	
		return list;
	}
	public int getNum() {
		String mapperId =String.format(mapper,"getNum");
		int num = session.selectOne(mapperId);		
		return num;
	}
	public int createSocial(SocialVO vo) {
		String mapperId =String.format(mapper,"createSocial");
		int result;
		result = session.insert(mapperId,vo);
		return result;
		
	}
	public int createSocialMaster(SocialVO user) {
		String mapperId =String.format(mapper,"createSocialMaster");
		int result;
	    result = session.insert(mapperId,user);		
		return result;
	}
	public boolean deleteSoical(int socialNum) {
		String mapperId =String.format(mapper,"deleteSocial");
		int result = session.delete(mapperId,socialNum);
		return result == 1? true:false;
	}
	
	public int modifySocial(SocialVO vo) {

		System.out.println(vo);
		String mapperId =String.format(mapper,"modifySocial");
		int result;
		result = session.update(mapperId,vo);
		return result;
	}
	public void entrustMaster(SocialVO vo) {
		String mapperId =String.format(mapper,"entrustMaster");
		int result;
		result = session.update(mapperId,vo);	
	}
	public void entrustUser(SocialVO vo) {
		String mapperId =String.format(mapper,"entrustUser");
		session.update(mapperId,vo);	
	}
	public void outcast(SocialVO vo) {
		String mapperId =String.format(mapper,"outcast");
		session.delete(mapperId,vo);	

	}
	public void join(SocialVO vo) {
		String mapperId =String.format(mapper,"join");
		session.insert(mapperId,vo);	

	}
	public boolean joinChk(ClubVO vo) { 
		String mapperId =String.format(mapper,"joinChk");
		List<ClubVO> data = session.selectList(mapperId,vo);
		System.out.println("DAO(joinChk) 내가 가입했나 결과 받은 값: "+data);  
		
		boolean re;
		if(data.isEmpty()) {
			re = false;
		}else {
			re=true;
		}
		System.out.println("DAO(joinChk) 내가 가입했나 결과 받은 값: "+re); 
			
		return re;
	}
	
}
