package com.han.mt.login.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.han.mt.fileUpload.model.FileUploadDAO;
import com.han.mt.fileUpload.model.FileUploadDTO;
import com.han.mt.login.model.KakaoVO;
import com.han.mt.login.model.LoginDAO;
import com.han.mt.login.model.LoginVO;
import com.han.mt.user.model.UserDAO;
import com.han.mt.user.model.UserDTO;

@Service
public class LogintService {

	@Autowired
	private LoginDAO dao;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private FileUploadDAO fileDao;
	 
	public int getLogin(LoginVO vo, HttpSession session) throws Exception {
		int result = 1; 
		System.out.println("서비스(getLogin) : 로그인 메서드 실행");
		UserDTO data;
		System.out.println("서비스(getLogin) : 로그인 메서드 실행"+vo);

		FileUploadDTO image = new FileUploadDTO();
		if(vo.getAdmin().equals("admin")) {
			boolean result2 = dao.login(vo);
			if(result2) {
				data = dao.getAdminLogin(vo);
				System.out.println("서비스(getLogin) : 로그인 메서드 실행"+data);
				
				image = fileDao.getProfile(vo.getEmail());
				if(data.getAdmin().equals("USER")) {return 3;}
				System.out.println("서비스(getAdminLogin) : 관리자 로그인 로그 저장");
				session.setAttribute("loginData", data);
				session.setAttribute("image", image);
				session.setAttribute("adminAcc", "admin");
			} else {
				boolean searchId = dao.searchId(vo.getEmail());
				System.out.println(searchId);
				System.out.println("서비스(getLogin) : 로그인 실패 원인 찾음");
				if(searchId) {
					result = 2; // 2, 아이디는 존재
				} else {
					result = 3;  // 3, 아이디가 존재하질 않음
				}
			}		
		} else {			
			boolean result2 = dao.login(vo);
			if(result2) {
				data = dao.getLogin(vo); 
				if(data.getAdmin().equals("ADMIN")) {return 3;}
				System.out.println("서비스(getLogin) : 로그인 메서드 실행"+data);
				dao.loginLog(data);
				image = fileDao.getProfile(vo.getEmail());
				System.out.println("서비스(getLogin) : 로그인 로그 저장");
				System.out.println("서비스(getLogin) : 아이디를 찾아 세션에 정보저장");
				session.setAttribute("loginData", data);
				session.setAttribute("loginId", data.getEmail());	
				session.setAttribute("image", image);			
				session.setAttribute("joinSocial",userDao.joinSocial(data.getEmail()));
				session.setAttribute("joinClub",userDao.joinClub(data.getEmail()));			
			} else {
				boolean searchId = dao.searchId(vo.getEmail());
				System.out.println(searchId);
				System.out.println("서비스(getLogin) : 로그인 실패 원인 찾음");			
				if(searchId) {
					result = 2; // 2, 아이디는 존재
				} else {
					result = 3;  // 3, 아이디가 존재하질 않음
				}
			}
			
		}		
		return result; 
	}


	public String findId(UserDTO dto) throws Exception {
		String idList = dao.findId(dto);
		System.out.println("서비스(findId) 아이디 중복조뢰 리스트:"+idList);

		if(idList==null) {
			System.out.println("아이디 조회 실패 세션저장");
		} else {
			System.out.println("받은 값으로 조회결과"+idList);
		}
		return idList;			
	}

	
	//아이디 체크, 닉네임 중복 체크
	public int idChk(String nickName,HttpSession session,String type) throws Exception{
		int chek;
		if(type.equals("email")) {
			boolean result = dao.idChk(nickName);
			System.out.println("서비스(idChk) 아이디 중복 확인 결과:"+result);
			session.setAttribute("idCheckResult", result);
			if(result) {
				chek =1;
			} else {
				chek=0;
			}
		} else {
			boolean result = dao.nickNameCheck(nickName);
			System.out.println("서비스(nickNameCheck) 닉네임 중복 확인 결과:"+result);
			session.setAttribute("nickCheckResult", result);
			if(result) {
				chek =1;
			} else {
				chek=0;
			}
		}
		return chek;
	}
	
	   
	//받은 정보로 회원가입 디비에 저장
	public boolean signup(UserDTO dto, HttpSession session) throws Exception{
		boolean signup = dao.signup(dto);
		
		if(signup) {
			System.out.println("서비스(signupDetail) 받은 아이디:" +dto.getEmail().toString());
			boolean signupDetail = dao.signupDetail(dto);
			
			if(signupDetail) {
				fileDao.setProfile(dto.getEmail());
				LoginVO vo = new LoginVO();
				vo.setEmail(dto.getEmail());
				vo.setPassword(dto.getPassword());
				UserDTO data = dao.getLogin(vo);
				session.setAttribute("loginData", data);		
			} else {
				dao.dropId(dto.getEmail());
				signup = false;
			}
			
		}
		return signup;
	}
	
	//카카오 로그인
	public boolean kakaoSignup(UserDTO dto, HttpSession session) {
		boolean signup = dao.kakaoSignup(dto);
		if(signup) {
			System.out.println("서비스(signupDetail) 받은 아이디:" +dto.getEmail().toString());
			boolean signupDetail = dao.signupDetail(dto);
			FileUploadDTO image = new FileUploadDTO();
			if(signupDetail) {
				UserDTO data = dao.kakaoLogin(dto.getEmail());
				image = fileDao.getProfile(dto.getEmail());
				session.setAttribute("loginData", data);		
			} else {
				dao.dropId(dto.getEmail());
				signup = false;
			}
			
		}
		return signup;
	}


	
	//회원가입
	public boolean sign(UserDTO dto,HttpSession session) throws Exception {
		
		boolean result = dao.nickNameCheck(dto.getNickName());		
		boolean setNickName;
		if(result ==true) {			
			setNickName = dao.setNickName(dto);
			System.out.println("서비스(setNickName) 닉네임 설정결과 :" +setNickName);

		} else {
			setNickName= false;
		}
		return setNickName;
	}

	public boolean emailRes(String id, HttpSession session) {
		boolean result = dao.emailRes(id);
		return result;
	}
	
	 public String getAccessToken (String authorize_code) {
	     System.out.println("----------------------------토큰발급---------------------------");
		 String access_Token = "";
	     String refresh_Token = "";
	     
	     //토큰발급 요청을 보낼 주소
	     String reqURL = "https://kauth.kakao.com/oauth/token";
	        
	        try {
                //URL객체 생성
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로 
                //.setDoOutput(true): URLConnection이 서버에 데이터를 보내는데 사용할 수 있는 지의 여부를 설정하는 것
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            
	            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            
	            sb.append("&client_id=fb5709f6ea71c441a41f751304f20053");
	            sb.append("&redirect_uri=http://localhost:8080/mt/login/kakaoLogin");
	            
	            sb.append("&code=" + authorize_code);            
	            bw.write(sb.toString());
	            bw.flush();
	            
	            //응답확인 200이면 정상
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);
	 
	            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";
	            
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);

				System.out.println("카카오 로그인 토큰서비스 1");
	            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            JsonParser parser = new JsonParser();
				System.out.println("카카오 로그인 토큰서비스 2");
	            JsonElement element = parser.parse(result);
				System.out.println("카카오 로그인 토큰서비스 3");
	            
	            access_Token = element.getAsJsonObject().get("access_token").getAsString();
				System.out.println("카카오 로그인 토큰서비스 4");
	            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
				System.out.println("카카오 로그인 토큰서비스 5");;
	            
	            System.out.println("access_token : " + access_Token);
	            System.out.println("refresh_token : " + refresh_Token);
	            
	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        
	        return access_Token;
	    }


	 public HashMap<String, Object> getUserInfo(String access_Token) {
			HashMap<String, Object> userInfo = new HashMap<String, Object>();
			String reqURL = "https://kapi.kakao.com/v2/user/me";
			try {
				URL url = new URL(reqURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", "Bearer " + access_Token);
				int responseCode = conn.getResponseCode();
				System.out.println("responseCode : " + responseCode);
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				String result = "";
				while ((line = br.readLine()) != null) {
					result += line;
				}
				System.out.println("response body : " + result);
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);
				JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
				JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
				System.out.println("카카오 로그인 유저서비스 1");
				String nickname = properties.getAsJsonObject().get("nickname").getAsString();
				System.out.println("카카오 로그인 유저서비스 2");
				String email = kakao_account.getAsJsonObject().get("email").getAsString();
				System.out.println("카카오 로그인 유저서비스 3");
				userInfo.put("nickname", nickname);
				userInfo.put("email", email);
				System.out.println("카카오 로그인 유저서비스 4");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return userInfo;
		}
	    
	
	 
	 
	 public KakaoVO userInfo(String access_Token) throws IOException {
	 		System.out.println("-------------------------사용자 정보 보기---------------------------");	 		
	 		
	 		KakaoVO userInfo = new KakaoVO();
	 		
	 		//토큰을 이용하여 카카오에 회원의 정보를 요청한다. 
         // v1을 통한 '사용자 정보 요청'은 만료되었다. 
	 		String reqURl = "https://kapi.kakao.com/v2/user/me";
	 		
	 		try {
	 			//URL 객체 생성
	 			URL url = new URL(reqURl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				//연결 완료
				
				//헤더 필드 읽기 
				//요청에 필요한 Header에 포함 될 내용 // 문서에서 지정해둔 양식 
				conn.setRequestProperty("Authorization", "Bearer " + access_Token);
				
				//응답코드 확인하기 
				int responseCode = conn.getResponseCode();
				System.out.println("responseCode: "+ responseCode);
				
				//입력스트림을 가지고 오고 데이터 읽기
				//inputStream은 데이터를 바이트의 배열로 읽어 오는 low-level의 메서드
				//따라서 데이터를 문자 '데이터'로 읽기 위해서 InputStreamReader로 매핑
				//데이터를 문자'열'로 읽기 위해서 inputStream을 BufferedReader로 매핑하기
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
				
				
				String line="";
				String result ="";
				
				while((line= br.readLine()) != null) {
					result += line;
				}
				System.out.println("response body: " + result);
				System.out.println("카카오 로그인 2서비스 1");

				// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            JsonParser parser = new JsonParser();
				System.out.println("카카오 로그인 2서비스 2");
	            JsonElement element = parser.parse(result);
				System.out.println("카카오 로그인 2서비스 3");
	            
	            //JsonElement.getAsJsonObject().get("key value").getAs타입(); 의 형태로 파싱한다. 
	           	//응답데이터(JSON)
				JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
				System.out.println("카카오 로그인 2서비스 4");
	            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
				System.out.println("카카오 로그인 2서비스 5");
	            Long id = element.getAsJsonObject().get("id").getAsLong();
				System.out.println("카카오 로그인 2서비스 6");
	            
	            //파싱된 json데이터를 string에 담기
	            //properties
	            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
				System.out.println("카카오 로그인 서비스 7");
	          
	            //kakao_account
	            String email = kakao_account.getAsJsonObject().get("email").getAsString();
	            String gender = kakao_account.getAsJsonObject().get("gender").getAsString();
	            String birthday = kakao_account.getAsJsonObject().get("birthday").getAsString();
	            String age_range = kakao_account.getAsJsonObject().get("age_range").getAsString();
	            
	            System.out.println("id: "+ id);
	            System.out.println("nickname: "+nickname);
	            
	            //setter이용하여 KakaoVO에 담기 
	            userInfo.setKakaoId(id);
	            userInfo.setNickName(nickname);
	            userInfo.setAccount_email(email);
	            userInfo.setAge_range(age_range);
	            userInfo.setGender(gender);
	            userInfo.setBirthday(birthday);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		
	 		
	 		
	 		return userInfo;
	 	}

	public void kakaoLogin(String string,HttpSession session) {
		System.out.println("카카오 로그인 서비스 9"+string);
		UserDTO data = dao.kakaoLogin(string);
		System.out.println("카카오 로그인 서비스 14"+data);
		FileUploadDTO image = new FileUploadDTO();

		image = fileDao.getProfile(string);
		session.setAttribute("loginData", data);
		session.setAttribute("loginId", string);	
		session.setAttribute("image", image);						
		session.setAttribute("joinSocial",userDao.joinSocial(data.getEmail()));
		session.setAttribute("joinClub",userDao.joinClub(data.getEmail()));
		
	}


	public int nickSave(String nickName, HttpSession session, String email) throws Exception {

		UserDTO data = new UserDTO();
		data.setNickName(nickName);
		data.setEmail(email);
		System.out.println("서비스(nickSave) : data "+data);
		int res= dao.nickSave(data);
		System.out.println("서비스(nickSave) : res "+res);
		
		session.removeAttribute("loginData");
		session.setAttribute("loginData", dao.getLogin(email));
		
		return res;
	}


	public int nickChk(String nickName, HttpSession session, String email) {

		UserDTO data = new UserDTO();
		data.setNickName(nickName);
		data.setEmail(email);
		System.out.println("서비스(nickChk) : data "+data);

		int res= dao.nickChk(data);
		System.out.println("서비스(nickChk) : res "+res);
		
		return res;
	}


	public int userCheck(UserDTO dto) {
		boolean result = dao.userCheck(dto);
		int num;
		if(result) {
			System.out.println("서비스(userCheck) : 받은값 "+dto);
			num = 1;
		} else {
			num=0;
		}
		return num;
	}
	
}
