 package com.han.mt.notice.service;

import java.util.List;

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

}
