package com.han.mt.club.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.club.model.BoardDTO;
import com.han.mt.club.model.ClubDAO;
import com.han.mt.club.model.ClubDTO;
import com.han.mt.club.model.ClubDynamicDTO;
import com.han.mt.club.model.ClubMemberDTO;
import com.han.mt.club.model.ClubVO;
import com.han.mt.social.model.FieldCategory;
import com.han.mt.social.model.SocialDynamicDTO;
import com.han.mt.user.model.UserDAO;
import com.han.mt.user.model.UserDTO;


@Service
public class ClubService {
	
	@Autowired
	private ClubDAO dao;

	@Autowired
	private UserDAO userDao;
	
	
	public List<ClubDTO> getClub(String category){
		List<ClubDTO> Club = dao.getClub(category);
		System.out.println("서비스(getClub) 받은값:"+"\n"+Club);

		return Club;
	}
	public List<ClubDTO> getClubTitle(String title){
		List<ClubDTO> Club = dao.getClubTitle(title);
		System.out.println("서비스(getClubTitle) 받은값:"+"\n"+Club);

		return Club;
	}

	public ClubDTO getDetail(int socialNum) {
		ClubDTO detail = dao.getDetail(socialNum);
		System.out.println("서비스(getDetail) 받은값:"+"\n"+detail);	
		return detail;
	}

	public List<ClubMemberDTO> getMember(int socialNum) {
		List<ClubMemberDTO> memberList = dao.getMemberList(socialNum);
		System.out.println("서비스(getMember) 받은값:"+"\n"+memberList);	
		return memberList;
	}
	public int getSocialNum() {
		int num = dao.getNum();
		System.out.println("서비스(getSocialNum) 받은값:"+"\n"+num);	
		return num;
	}
	public int createClub(ClubDTO vo,HttpSession session) {

		int re = dao.createClub(vo);
		dao.createClubDetail(vo);
		if(re != 1) {return 9;}
		int sult = dao.createClubMaster(vo);
		if(sult != 1) {return 8;}
		session.removeAttribute("joinClub");
		session.setAttribute("joinClub",userDao.joinClub(vo.getEmail()));
		return 1;

	}
	public boolean deleteSoical(int socialNum,String email,HttpSession session) {
		boolean result =dao.deleteSoical(socialNum);

		session.removeAttribute("joinClub");
		session.setAttribute("joinClub",userDao.joinClub(email));
		return result;
		
	}
	public int modifyClub(ClubVO vo) {

		System.out.println(vo);
		int re = dao.modifyClub(vo);
		return re;
	}
	
	public void entrust(ClubVO vo,HttpSession session) {
		dao.entrustUser(vo);
		dao.entrustMaster(vo);
		session.removeAttribute("joinClub");
		session.setAttribute("joinClub",userDao.joinClub(vo.getId()));
	}
	public void outcast(ClubVO vo,HttpSession session) {
		dao.outcast(vo);
		session.removeAttribute("joinClub");
		session.setAttribute("joinClub",userDao.joinClub(vo.getId()));
		
	}
	public void join(ClubVO vo,HttpSession session) throws Exception {
		int real = dao.getReal(vo.getSocialNum());		
		ClubDTO detail = dao.getDetail(vo.getSocialNum());
		session.removeAttribute("joinClub");
		session.setAttribute("joinClub",userDao.joinClub(vo.getId()));
		
		dao.join(vo);
	}
	
	public List cluList(String loginId) {
		List<Integer> list = dao.clubList(loginId);
		System.out.println(list);
		return list;
	}
	public boolean joinChk(String loginId, int id) {
		ClubVO vo =new ClubVO();
		vo.setId(loginId);
		vo.setSocialNum(id);
		
		System.out.println(vo);
		boolean result = dao.joinChk(vo);
		return result;
	}
	public List<BoardDTO> getBoard(BoardDTO dto,HttpSession session) {
		System.out.println("서비스(getBorad) 주입값 dto :"+"\n"+dto); 
		List<BoardDTO> list = dao.getBorad(dto);	
		System.out.println("서비스(getBorad)보드 조회 :"+"\n"+list); 
		session.setAttribute("socialNum",dto.getSocialNum());
		return list;
	}
	public void boardAdd(BoardDTO dto) {
		dao.boardAdd(dto);
		
	}
	public int getBoardNum() {
		int num = dao.getBoardNum();
		return num;
	}
	

}
