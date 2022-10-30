package com.han.mt.club.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.han.mt.social.model.FieldCategory;
import com.han.mt.social.model.SocialDynamicDTO;
import com.han.mt.club.model.ClubDTO;
import com.han.mt.club.model.ClubDynamicDTO;
import com.han.mt.club.model.ClubMemberDTO;
import com.han.mt.club.model.ClubVO;

@Repository
public class ClubDAO {

	@Autowired // 어노테이션 밑줄친 객체에 의존하기 위해만든다
	private SqlSession session;

	private String mapper = "clubMapper.%s";
	private String userMapper = "userMapper.%s";

	public List<ClubDTO> getClub(String category) {
		String mapperId =String.format(mapper,"getClub");
		List<ClubDTO> Club = session.selectList(mapperId,category);
		System.out.println("DAO(getClub) 받은 값: "+Club);
		return Club;
	}
	public List<ClubDTO> getClubTitle(String title) {
		String mapperId =String.format(mapper,"getClubTitle");
		List<ClubDTO> Club = session.selectList(mapperId,title);
		System.out.println("DAO(getClubTitle) 받은 값: "+Club);
		return Club;
	}
	
	
	public List<FieldCategory> getCategory(){
		String mapperId =String.format(mapper,"getCategory");
		List<FieldCategory> category = session.selectList(mapperId);
		System.out.println("DAO(getCategory) 받은 값: "+category);
		return category;
	}

	public List<SocialDynamicDTO> getReal() {
		String mapperId =String.format(mapper,"getClubMemberCount");
		List<SocialDynamicDTO> real = session.selectList(mapperId);
		System.out.println("DAO(getReal) 받은 값: "+real);
		return real;
	}
	
	public int getReal(int socialNum) {
		String mapperId =String.format(mapper,"getClubMemberCountClub");
		int real = session.selectOne(mapperId,socialNum);
		System.out.println("DAO(getReal) 받은 값: "+real);
		return real;
	}

	public ClubDTO getDetail(int socialNum) {
		String mapperId =String.format(mapper,"getDetail");
		ClubDTO detail = session.selectOne(mapperId,socialNum);
		System.out.println("DAO(getDetail) 받은 값: "+detail);
		return detail;
	}

	public List<ClubMemberDTO> getMemberList(int socialNum) {
		String mapperId =String.format(mapper,"getMemberList");
		List<ClubMemberDTO> list = session.selectList(mapperId,socialNum);
		System.out.println("DAO(getMemberList) 받은 값: "+list);	
		return list;
	}
	public int getNum() {
		String mapperId =String.format(mapper,"getNum");
		int num = session.selectOne(mapperId);		
		System.out.println("DAO(getNum) 받은 값: "+num);	
		return num;
	}
	public int createClub(ClubVO vo) {
		String mapperId =String.format(mapper,"createClub");
		int result;
		result = session.insert(mapperId,vo);
		System.out.println("DAO(createClub) 받은 값: "+result);	
		return result;
		
	}
	public int createClubMaster(ClubVO user) {
		String mapperId =String.format(mapper,"createClubMaster");
		int result;
	    result = session.insert(mapperId,user);		
		System.out.println("DAO(createClubMaster) 받은 값: "+result);
		return result;
	}
	public boolean deleteSoical(int socialNum) {
		String mapperId =String.format(mapper,"deleteClub");
		int result = session.delete(mapperId,socialNum);
		System.out.println("DAO(deleteSoical) 받은 값: "+result);
		return result == 1? true:false;
	}
	
	public int modifyClub(ClubVO vo) {

		System.out.println(vo);
		String mapperId =String.format(mapper,"modifyClub");
		int result;
		result = session.update(mapperId,vo);
		System.out.println("DAO(modifyClub) 받은 값: "+result);
		return result;
	}
	public void entrustMaster(ClubVO vo) {
		String mapperId =String.format(mapper,"entrustMaster");
		int result;
		result = session.update(mapperId,vo);	
		System.out.println("DAO(entrustMaster) 받은 값: "+result);
	}
	public void entrustUser(ClubVO vo) {
		System.out.println("DAO(entrustUser)");
		String mapperId =String.format(mapper,"entrustUser");
		session.update(mapperId,vo);	
	}
	public void outcast(ClubVO vo) {
		System.out.println("DAO(outcast)");
		String mapperId =String.format(mapper,"outcast");
		session.delete(mapperId,vo);	

	}
	public void join(ClubVO vo) {
		System.out.println("DAO(join)");
		String mapperId =String.format(mapper,"join");
		session.insert(mapperId,vo);	

	}
	public List<Integer> clubList(String id) {
		String mapperId =String.format(mapper,"clubList");
		List<Integer> real = session.selectList(mapperId,id);
		System.out.println("DAO(clubList) 받은 값: "+real);
		return real;
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
