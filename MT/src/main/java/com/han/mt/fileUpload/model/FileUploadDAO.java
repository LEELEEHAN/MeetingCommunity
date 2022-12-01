package com.han.mt.fileUpload.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileUploadDAO {
	
	@Autowired
	private SqlSession session;
	
	public int getCount(int bid,String type) {
		System.out.println("DAO(getCount) : bid" +bid);
		int res = 5;
		if(type.equals("notice")) {
			res =session.selectOne("fileUploadMapper.getCountNotice",bid);
		} else if(type.equals("social")) {
			res =session.selectOne("fileUploadMapper.getCountSocial",bid);
		} else if(type.equals("board")) {
			res =session.selectOne("fileUploadMapper.getCountBoard",bid);
		}
		System.out.println("DAO(getCount) : 받은 내용" +res);
		return res;
	}

	public int getCount(String bid,String type) {
		int res = session.selectOne("fileUploadMapper.getCountEmail", bid);
		return res;
	}

	public boolean insertData(FileUploadDTO data, String type) {
		System.out.println("DAO(insertData) : "+data);		

		int res = 5;
		if(type.equals("notice")) {
			System.out.println("DAO(insertData notice) : "+data);		
			res =session.insert("fileUploadMapper.insertDataNotice", data);
			System.out.println("DAO(insertData notice) : "+res);		
		} else if(type.equals("social")) {
			System.out.println("DAO(insertData social) : "+data);	
			res =session.insert("fileUploadMapper.insertDataSocial", data);
			System.out.println("DAO(insertData social) : "+res);		
		} else if(type.equals("board")) {
			System.out.println("DAO(insertData board) : "+data);	
			res =session.insert("fileUploadMapper.insertDataBoard", data);
			System.out.println("DAO(insertData board) : "+res);		
		} else if(type.equals("email")) {
			System.out.println("DAO(insertData email) : "+data);	
			res =session.update("fileUploadMapper.updateDataEmail", data);
			System.out.println("DAO(insertData email) : "+res);		
		}
		
		System.out.println(res);
		return res == 1 ? true : false;
	}

	public List<FileUploadDTO> selectDatas(int bId,String type) {
		
		List<FileUploadDTO> res = null;
		if(type.equals("notice")) {	
			res = session.selectList("fileUploadMapper.selectDatasNotice", bId);
		} else if(type.equals("social")) {
			res = session.selectList("fileUploadMapper.selectDatasSocial", bId);
		} else if(type.equals("board")) {
			res = session.selectList("fileUploadMapper.selectDatasBoard", bId);
		}
		
		return res;
	}
	public List<FileUploadDTO> selectDatas(String bId,String type) {
		List<FileUploadDTO> res = session.selectList("fileUploadMapper.selectDatas", bId);
		return res;
	}

	public int setName() {
		int res = session.selectOne("fileUploadMapper.setName");
		return res;
	}

	public FileUploadDTO getProfile(String email) {
		FileUploadDTO res = session.selectOne("fileUploadMapper.getProfile",email);
		return res;
	}

	public void setProfile(String email) {
		session.insert("fileUploadMapper.setProfile",email);
	}

}
