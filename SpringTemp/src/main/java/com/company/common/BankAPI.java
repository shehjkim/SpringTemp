package com.company.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
public class BankAPI {

	String reponse_type = "code";
//	String client_id="a24b461b-27b2-43ab-b744-4d0084be4664";
	String redirect_uri = "http://localhost:85/temp/callback";
	String scope = "login inquiry transfer";
	String state = "12345678901234567890123456789012";
	String auth_type = "0";
	String host = "https://testapi.openbanking.or.kr";
	String use_org_code = "M202111684";
//	String access_token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxNDExMjksImp0aSI6ImRlMzZhMzgwLWYwZTYtNDE3My05YjJjLTdiMTJjZjU3YzE5MSJ9.enXf7NIOSSNFhidKMSHtSzK36wRGBc9S-RS6Ajw7XM8";
//	String refresh_token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjQwMDUxMjksImp0aSI6ImE2NmNmZjlhLTU5YTItNGU2Yy05NDljLWFhMGIyZTQzYjU0YSJ9.Ox9fP4xQCvikUraWFvui_9sVXOMs2_MG8bHRa0Mgs7g\r\n"
//			+ "access_tokeneyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxNDExMjksImp0aSI6ImRlMzZhMzgwLWYwZTYtNDE3My05YjJjLTdiMTJjZjU3YzE5MSJ9.enXf7NIOSSNFhidKMSHtSzK36wRGBc9S-RS6Ajw7XM8";
//	

//사용자 인증	
	public String getAccessToken(String authorize_code) {

		String access_Token = "";
		String refresh_Token = "";
		String reqURL = host + "/oauth/2.0/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			sb.append("code=" + authorize_code);
			sb.append("&client_id=a24b461b-27b2-43ab-b744-4d0084be4664");
			sb.append("&client_secret=ae952161-4c8d-40df-83c1-16c7a5755899");
			sb.append("&redirect_uri=http://localhost:85/temp/callback");
			sb.append("&grant_type=authorization_code");
			bw.write(sb.toString());
			bw.flush(); // 이까지만 서브요청

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return access_Token;
	}

//전체계좌조회
	public HashMap<String, Object> getAccountList(String access_token, String use_num) {

		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		HashMap<String, Object> map = new HashMap<>();
		String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/list";

		StringBuilder qstr = new StringBuilder();
		qstr.append("user_seq_no=" + use_num);
		qstr.append("&include_cancel_yn=Y");
		qstr.append("&sort_order=D");
		try {
			URL url = new URL(reqURL + "?" + qstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
			// 출력되는 값이 200이면 정상작동
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

//	        //String 값을 map에 담아서 return 
			Gson gson = new Gson();
			map = gson.fromJson(result, HashMap.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	public String getDate() {
		String str = "";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		str = format.format(date);

		return str;
	}

	public String getRand() { // 9자리난수
		long time = System.currentTimeMillis();
		String str = Long.toString(time);

		return str.substring(str.length() - 9);
	}

//잔액조회	   
	public HashMap<String, Object> getBalance(BankVO vo) {

		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		HashMap<String, Object> map = new HashMap<>();
		String reqURL = host + "/v2.0/account/balance/fin_num";

		StringBuilder qstr = new StringBuilder();

		qstr.append("bank_tran_id=" + use_org_code + "U" + getRand())
				.append("&fintech_use_num=" + vo.getFintech_use_num()).append("&tran_dtime=" + getDate());
		try {
			URL url = new URL(reqURL + "?" + qstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + vo.getAccess_token());

			// 출력되는 값이 200이면 정상작동
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// //String 값을 map에 담아서 return
			Gson gson = new Gson();
			map = gson.fromJson(result, HashMap.class);

			System.out.println("aaaa:" + map);

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;

	}

//기관인증
	public Map<String, Object> getOrgAccessToken() {

		String access_Token = "";
		String refresh_Token = "";
		String reqURL = host + "/oauth/2.0/token";
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			StringBuilder param = new StringBuilder();
			param.append("client_id=a24b461b-27b2-43ab-b744-4d0084be4664")
					.append("&client_secret=ae952161-4c8d-40df-83c1-16c7a5755899")
					.append("&scope=oob")
					.append("&grant_type=client_credentials");


			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// header
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			OutputStream os = conn.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));//스크림값 담기
			bw.write(param.toString());
			bw.flush(); // 스크림값을 불러옴
			
			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			Gson gson = new Gson();
			map = gson.fromJson(result, HashMap.class);
			
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}
}