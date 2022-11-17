package com.han.mt.social.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.club.model.ClubVO;
import com.han.mt.social.controller.SocialController;
import com.han.mt.social.model.FieldCategory;
import com.han.mt.social.model.SocialCommentDTO;
import com.han.mt.social.model.SocialDAO;
import com.han.mt.social.model.SocialDTO;
import com.han.mt.social.model.SocialDynamicDTO;
import com.han.mt.social.model.SocialMemberDTO;
import com.han.mt.social.model.SocialVO;
import com.han.mt.user.model.UserDAO;
import com.han.mt.user.model.UserDTO;

@Service
public class SocialService {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialService.class);

	
	@Autowired 
	private SocialDAO dao;
	

	@Autowired
	private UserDAO userDao;
	
	public List<SocialDTO> getSocial(String category){
		List<SocialDTO> social = dao.getSocial(category);
//		logger.info("getSocial(social={})",social);

		System.out.println("서비스(getSocial) 받은값:"+"\n"+social);

		return social;
	}
	public List<SocialDTO> getSocialTitle(String title){
		List<SocialDTO> social = dao.getSocialTitle(title);
//		logger.info("getSocial(social={})",social);
		System.out.println("서비스(getSocialTitle) 받은값:"+"\n"+social);
		return social;
	}
	public List<FieldCategory> getCategory() {// �꽕鍮� 移댄뀒怨좊━ 異쒕젰
		List<FieldCategory> category = dao.getCategory();
		System.out.println("서비스(getCategory) 받은값:"+"\n"+category);
		return category;
	}

	public List<SocialDynamicDTO> getReal() {
		List<SocialDynamicDTO> real = dao.getReal();	
		System.out.println("서비스(getReal) 받은값:"+"\n"+real);	
		return real;
	}

	public int getReal(int socialNum) {
		int real = dao.getReal(socialNum);	
		System.out.println("서비스(getReal) 받은값:"+"\n"+real);	

		return real;
	}

	public SocialDTO getDetail(int socialNum) {
		SocialDTO detail = dao.getDetail(socialNum);
		System.out.println("서비스(getDetail) 받은값:"+"\n"+detail);
		return detail;
	}

	public List<SocialMemberDTO> getMember(int socialNum) {
		List<SocialMemberDTO> memberList = dao.getMemberList(socialNum);
		System.out.println("서비스(getMember) 받은값:"+"\n"+memberList);
		return memberList;
	}
	public int getSocialNum() {
		int num = dao.getNum();
		System.out.println("서비스(getSocialNum) 받은값:"+"\n"+num);	
		return num;
	}
	public int createSocial(SocialVO vo, SocialDTO dto,HttpSession session) {

		int re = dao.createSocial(vo);
		if(re != 1) {return 9;}
		if(re == 1) {
		int sult = dao.createSocialMaster(vo);
		dao.createSocialDetail(dto);
		if(sult != 1) {return 8;}
		}
		session.removeAttribute("joinSocial");
		session.setAttribute("joinSocial",userDao.joinSocial(vo.getId()));
		return re;

	}
	public boolean deleteSoical(int socialNum,String email,HttpSession session)  {
		boolean result =dao.deleteSoical(socialNum);
		session.removeAttribute("joinSocial");
		session.setAttribute("joinSocial",userDao.joinSocial(email));

		return result;
		
	}
	public int modifySocial(SocialVO vo) {

		System.out.println(vo);
		int re = dao.modifySocial(vo);
		return re;
	}
	
	public void entrust(SocialVO vo,HttpSession session) {
		dao.entrustUser(vo);
		dao.entrustMaster(vo);
		session.removeAttribute("joinSocial");
		session.setAttribute("joinSocial",userDao.joinSocial(vo.getId()));
	}
	public void outcast(SocialVO vo,HttpSession session) {
		dao.outcast(vo);
		session.removeAttribute("joinSocial");
		session.setAttribute("joinSocial",userDao.joinSocial(vo.getId()));
		
	}
	public void join(SocialVO vo,HttpSession session) throws Exception {
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
	public List<SocialCommentDTO> getComment(int id) {
		System.out.println("서비스(getComment) 받은 SocialNum :"+"\n"+id);
		List<SocialCommentDTO> list = dao.getSocialComment(id);
		System.out.println("서비스(getComment) 조회 된 SocialComment :"+"\n"+list);
		return list;
	}
	public void onComment(SocialCommentDTO dto) {
		System.out.println("서비스(onComment) 받은 dto :"+"\n"+dto);
		dao.onComment(dto);
		
	}
	public int getCommentNum() {
		int num = dao.getCommentNum();
		System.out.println("서비스(getCommentNum) 받은값:"+"\n"+num);	
		return num;
	}
		
}
