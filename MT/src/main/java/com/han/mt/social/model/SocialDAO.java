package com.han.mt.social.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.han.mt.club.model.ClubVO;
import com.han.mt.user.model.UserDTO;

@Repository
public class SocialDAO {
	
	@Autowired // �뼱�끂�뀒�씠�뀡 諛묒쨪移� 媛앹껜�뿉 �쓽議댄븯湲� �쐞�빐留뚮뱺�떎
	private SqlSession session;

	private String mapper = "socialMapper.%s";
	private String userMapper = "userMapper.%s";
	private String commentMapper = "commentMapper.%s";

	public List<SocialDTO> getSocial(String category) {
		String mapperId =String.format(mapper,"getSocial");
		List<SocialDTO> social = session.selectList(mapperId,category);
		System.out.println("DAO(getSocial) 카테고리 검색 :"+"\n"+category);
		return social;
	}
	public List<SocialDTO> getSocialTitle(String title) {
		String mapperId =String.format(mapper,"getSocialTitle");
		List<SocialDTO> social = session.selectList(mapperId,title);
		System.out.println("DAO(getSocial) 제목 검색 :"+"\n"+title);
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
		System.out.println("DAO(joinChk) 가입 했나 결과 받은값: "+data);  
		
		boolean re;
		if(data.isEmpty()) {
			re = false;
		}else {
			re=true;
		}
		System.out.println("DAO(joinChk) 가입 했나 결과 받은값 "+re); 
			
		return re;
	}
	public List<SocialCommentDTO> getSocialComment(int id) {
		String mapperId =String.format(commentMapper,"getSocialComment");
		System.out.println("DAO(getSocialComment) 받은 socialNum: "+"\n"+id);  
		List<SocialCommentDTO> data = session.selectList(mapperId,id);
		System.out.println("DAO(getSocialComment) 조회 된 SocialComment :"+"\n"+data);  
		return data;
	}
	public boolean onComment(SocialCommentDTO dto) {
		String mapperId =String.format(commentMapper,"onComment");
		System.out.println("DAO(onComment) 받은 dto: "+"\n"+dto);  
		int insert =session.insert(mapperId, dto);	
		boolean result;
		if (insert == 1) {
			result =true;
		}else { 
			result= false;
		};
		System.out.println("DAO(onComment) 처리 결과 result: "+"\n"+result);  
		return result;		
		
	}
	public int getCommentNum() {
		String mapperId =String.format(commentMapper,"getCommentNum");
		int num = session.selectOne(mapperId);		
		System.out.println("DAO(getCommentNum) 부여될 commentNum: "+"\n"+num);  
		return num;
	}
	public void createSocialDetail(SocialDTO dto) {
		System.out.println("DAO(createSocialDetail) 받은 dto: "+"\n"+dto);  
		String mapperId =String.format(mapper,"createSocialDetail");
		session.insert(mapperId,dto);
		
	}
	
	
	
	
	
}
