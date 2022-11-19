package com.han.mt.notice.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO {

		@Autowired 
		private SqlSession session;
		private String mapper = "noticeMapper.%s";
		
	public void write(NoticeBoardDTO dto) {
		String getNumId =String.format(mapper,"getNum");
		int num = session.selectOne(getNumId);
		dto.setNoticeNum(num);
		if(dto.getCategory().equals("info,")){
			dto.setCategory("info");
		} else {
			dto.setCategory("event");				
		}
		String mapperId =String.format(mapper,"write");
		session.insert(mapperId,dto);
	}

	public List<NoticeBoardDTO> getList(String category) {
		String mapperId =String.format(mapper,"getList");	
		List<NoticeBoardDTO> list = session.selectList(mapperId,category);		
		return list;
	}
	public NoticeBoardDTO getDetail(int id) {
		String mapperId =String.format(mapper,"getDetail");	
		NoticeBoardDTO detail = session.selectOne(mapperId,id);

		return detail;
		
	}

	public boolean deleteNotice(int id) {
		String mapperId =String.format(mapper,"deleteNotice");
		int result = session.delete(mapperId,id);
		// TODO Auto-generated method stub
		return result == 1? true:false;
	}

}
