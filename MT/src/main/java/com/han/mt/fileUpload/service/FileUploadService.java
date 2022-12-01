package com.han.mt.fileUpload.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.han.mt.fileUpload.model.FileUploadDAO;
import com.han.mt.fileUpload.model.FileUploadDTO;

@Service
public class FileUploadService {
	@Autowired
	private FileUploadDAO dao;
	
	@Transactional
	public int upload(MultipartFile file, FileUploadDTO data,String type,HttpSession session) throws Exception {
		
		File folder = new File(data.getLocation());
		if(!folder.exists()) {
			folder.mkdirs();
		}

		UUID uuid = UUID.nameUUIDFromBytes(file.getBytes());
		
		data.setUuidName(uuid.toString());//파일이 고유코드부여
		data.setFileName(file.getOriginalFilename());//파일의 원래이름 저장
		data.setFileSize((int)file.getSize());
		data.setContentType(file.getContentType());
		int count=0;
		if(data.getFileName().equals("") && data.getFileSize()<=0 ){
			return 0;
		}//파일이 없는데도 DB에 접근하길래 차단
		if(type.equals("notice")) {
			System.out.println("서비스(upload) : 노티스");
			count = dao.getCount(data.getNoticeNum(),type);
			System.out.println("서비스(upload) : 노티스 다오 됨"+count);
		} else if(type.equals("social")) {
			System.out.println("서비스(upload) : 소셜");
			count = dao.getCount(data.getSocialNum(),type);
		} else if(type.equals("email")) {
			System.out.println("서비스(upload) : 이메일");
			count = dao.getCount(data.getEmail(),type);
		} else if(type.equals("board")) {
			System.out.println("서비스(upload) : 보드");
			count = dao.getCount(data.getBoardNum(),type);
		}
		
		if(type.equals("email")) {
			if(count >= 2) {
				System.out.println("서비스(upload) : 이베일 2장미만");
				//업로드 수량초과 프로필이미지는 1개만
				return -1;
			}
		} else if(count >= 4) {
			//업로드 수량초과 최대사진은 3장까지만
			System.out.println("서비스(upload) : 파일 3장이하");
			return -1;
		}

		System.out.println("서비스(upload) : insertData전");
		boolean result = dao.insertData(data,type);
		System.out.println("서비스(upload) : insertData"+result);
		if(result) {
			try {
				file.transferTo(new File(data.getLocation() + File.separatorChar + data.getUuidName()));
				if(type.equals("email")) {
					FileUploadDTO image = new FileUploadDTO();
					image = dao.getProfile(data.getEmail());
					session.setAttribute("image", image);
				}
				System.out.println("서비스(upload) : 트라이"+result);
				System.out.println("서비스(upload) : 트라이"+file);
				return 1;
			} catch (IOException e) {

				System.out.println("서비스(upload) : 예외");
				throw new Exception("서버 파일 업로드 실패");
			}
		} else {
			System.out.println("서비스(upload) : 리턴0");
			return 0;
		}
	}

	public List<FileUploadDTO> getDatas(int bId, String type) {
		List<FileUploadDTO> datas = dao.selectDatas(bId,type);
		return datas;
	}
	
}


