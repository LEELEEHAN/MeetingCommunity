 package com.han.mt.notice.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.mt.notice.model.NoticeBoardDTO;
import com.han.mt.notice.model.NoticeDAO;

@Service
public class NoticeService {
	
	
	@Autowired
	private NoticeDAO dao;
	

	public void write(NoticeBoardDTO dto) {
		dao.write(dto);
		
	}


	public List<NoticeBoardDTO> getList(String category) {
		List<NoticeBoardDTO> list = dao.getList(category);
		
		return list;
	}


	public NoticeBoardDTO getDetail(int id) {
		NoticeBoardDTO detail = dao.getDetail(id);
		return detail;
		
		
	}


	public boolean deleteNotice(int id, HttpSession session) {
		boolean result =dao.deleteNotice(id);
		return result;
	}


	public int getNoticeNum() {
		int nul = dao.getNoticeNum();
		return nul;
	}


	public boolean modify(NoticeBoardDTO dto) {
		System.out.println("서비스(modify) 에서 받은 데이터"+"\n"+dto);
		boolean result =dao.modify(dto);
	return result;	
	}

}
