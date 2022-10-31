package com.han.mt.club.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.club.model.ClubDAO;
import com.han.mt.club.model.ClubDTO;
import com.han.mt.club.model.ClubDynamicDTO;
import com.han.mt.club.model.ClubMemberDTO;
import com.han.mt.club.model.ClubVO;
import com.han.mt.social.model.FieldCategory;
import com.han.mt.social.model.SocialDynamicDTO;


@Service
public class ClubService {
	
	@Autowired
	private ClubDAO dao;
	
	
	public List<ClubDTO> getClub(String category){
		List<ClubDTO> Club = dao.getClub(category);
		System.out.println("����(getClub) ������:"+"\n"+Club);

		return Club;
	}
	public List<ClubDTO> getClubTitle(String title){
		List<ClubDTO> Club = dao.getClubTitle(title);
		System.out.println("����(getClubTitle) ������:"+"\n"+Club);

		return Club;
	}

	public ClubDTO getDetail(int socialNum) {
		ClubDTO detail = dao.getDetail(socialNum);
		System.out.println("����(getDetail) ������:"+"\n"+detail);	
		return detail;
	}

	public List<ClubMemberDTO> getMember(int socialNum) {
		List<ClubMemberDTO> memberList = dao.getMemberList(socialNum);
		System.out.println("����(getMember) ������:"+"\n"+memberList);	
		return memberList;
	}
	public int getSocialNum() {
		int num = dao.getNum();
		System.out.println("����(getSocialNum) ������:"+"\n"+num);	
		return num;
	}
	public int createClub(ClubVO vo) {

		int re = dao.createClub(vo);
		if(re != 1) {return 9;}
		int sult = dao.createClubMaster(vo);
		if(sult != 1) {return 8;}
		return 1;

	}
	public boolean deleteSoical(int socialNum) {
		boolean result =dao.deleteSoical(socialNum);
		return result;
		
	}
	public int modifyClub(ClubVO vo) {

		System.out.println(vo);
		int re = dao.modifyClub(vo);
		return re;
	}
	
	public void entrust(ClubVO vo) {
		dao.entrustUser(vo);
		dao.entrustMaster(vo);
	}
	public void outcast(ClubVO vo) {
		dao.outcast(vo);
		
	}
	public void join(ClubVO vo) throws Exception {
		int real = dao.getReal(vo.getSocialNum());		
		ClubDTO detail = dao.getDetail(vo.getSocialNum());
		
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
		
	

}
