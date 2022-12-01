package com.han.mt.fileUpload.model;

public class FileUploadDTO {


	
	private int socialNum;
	private int noticeNum;
	private int boardNum;
	private String email;
	private int fileNum;
	private String fileName;
	private String uploader;
	private String uuidName;
	private String location;
	private String url;
	private int fileSize;
	private String contentType;
	
	public FileUploadDTO() {}
	
	public FileUploadDTO(int bId, String location, String url, String type,String uploader) {
		
		if(type.equals("notice")) {
			this.noticeNum = bId;
			} else if(type.equals("board")) {
				this.boardNum = bId;
			} else if(type.equals("social")) {
				this.socialNum = bId;
			}	
		this.location = location;
		this.url = url;
		this.uploader = uploader;
	}
	public FileUploadDTO(String bId, String location, String url, String type,String uploader) {
						
			this.email = bId;					
			this.location = location;
			this.url = url;
			this.uploader = uploader;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public int getSocialNum() {
		return socialNum;
	}

	public void setSocialNum(int socialNum) {
		this.socialNum = socialNum;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public int getNoticeNum() {
		return noticeNum;
	}

	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}
	
	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getUuidName() {
		return uuidName;
	}

	public void setUuidName(String uuidName) {
		this.uuidName = uuidName;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return "FileUploadDTO [socialNum=" + socialNum + ", noticeNum=" + noticeNum + ", boardNum=" + boardNum
				+ ", email=" + email + ", fileNum=" + fileNum + ", fileName=" + fileName + ", uploader=" + uploader
				+ ", uuidName=" + uuidName + ", location=" + location + ", url=" + url + ", fileSize=" + fileSize
				+ ", contentType=" + contentType + "]"+"\n";
	}

	
}
