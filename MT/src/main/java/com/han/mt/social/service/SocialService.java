package com.han.mt.social.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.club.model.ClubVO;
import com.han.mt.social.controller.SocialController;
import com.han.mt.social.model.FieldCategory;
import com.han.mt.social.model.SocialDAO;
import com.han.mt.social.model.SocialDTO;
import com.han.mt.social.model.SocialDynamicDTO;
import com.han.mt.social.model.SocialMemberDTO;
import com.han.mt.social.model.SocialVO;
import com.han.mt.user.model.UserDTO;

@Service
public class SocialService {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialService.class);

	
	@Autowired
	private SocialDAO dao;
	
	
	public List<SocialDTO> getSocial(String category){
		List<SocialDTO> social = dao.getSocial(category);
//		logger.info("getSocial(social={})",social);

		System.out.println("서비스(getSocial) 받은 값:"+"\n"+social);

		return social;
	}
	public List<SocialDTO> getSocialTitle(String title){
		List<SocialDTO> social = dao.getSocialTitle(title);
//		logger.info("getSocial(social={})",social);
		System.out.println("서비스(getSocialTitle) 받은 값:"+"\n"+social);

		return social;
	}
	public List<FieldCategory> getCategory() {// 네비 카테고리 출력
		List<FieldCategory> category = dao.getCategory();
		System.out.println("서비스(getCategory) 받은 값:"+"\n"+category);
		return category;
	}

	public List<SocialDynamicDTO> getReal() {
		List<SocialDynamicDTO> real = dao.getReal();	
		System.out.println("서비스(getReal) 받은 값:"+"\n"+real);	
		return real;
	}

	public int getReal(int socialNum) {
		int real = dao.getReal(socialNum);	
		System.out.println("서비스(getReal) 받은 값:"+"\n"+real);	

		return real;
	}

	public SocialDTO getDetail(int socialNum) {
		SocialDTO detail = dao.getDetail(socialNum);
		System.out.println("서비스(getDetail) 받은 값:"+"\n"+detail);
		return detail;
	}

	public List<SocialMemberDTO> getMember(int socialNum) {
		List<SocialMemberDTO> memberList = dao.getMemberList(socialNum);
		System.out.println("서비스(getMember) 받은 값:"+"\n"+memberList);
		return memberList;
	}
	public int getSocialNum() {
		int num = dao.getNum();
		System.out.println("서비스(getSocialNum) 받은 값:"+"\n"+num);	
		return num;
	}
	public int createSocial(SocialVO vo) {

		int re = dao.createSocial(vo);
		if(re != 1) {return 9;}
		int sult = dao.createSocialMaster(vo);
		if(sult != 1) {return 8;}
		return 1;

	}
	public boolean deleteSoical(int socialNum) {
		boolean result =dao.deleteSoical(socialNum);
		return result;
		
	}
	public int modifySocial(SocialVO vo) {

		System.out.println(vo);
		int re = dao.modifySocial(vo);
		return re;
	}
	
	public void entrust(SocialVO vo) {
		dao.entrustUser(vo);
		dao.entrustMaster(vo);
	}
	public void outcast(SocialVO vo) {
		dao.outcast(vo);
		
	}
	public void join(SocialVO vo) throws Exception {
		int real = dao.getReal(vo.getSocialNum());		
		SocialDTO detail = dao.getDetail(vo.getSocialNum());
		int max= detail.getMaximum();
		if(real<max) {
		dao.join(vo);}
		
	}
	public boolean joinChk(String loginId, int id) {
		ClubVO vo =new ClubVO();
		vo.setId(loginId);
		vo.setSocialNum(id);
		
		System.out.println(vo);
		boolean result = dao.joinChk(vo);
		return result;
	}
		
}
