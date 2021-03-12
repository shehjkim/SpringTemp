package com.company.temp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.common.BankAPI;
import com.company.common.BankVO;

@Controller
public class BankController {
	
	@Autowired
	BankAPI bankAPI;
	
	String reqURL = "https://testapi.openbanking.or.kr/oauth/2.0/authorize_account";
	String response_type = "code";
	String client_id = "a24b461b-27b2-43ab-b744-4d0084be4664";
	String redirect_uri = "http://localhost:85/temp/callback";
	String scope = "login inquiry transfer";
	String state = "12345678901234567890123456789012";
	String auth_type = "0";
	String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxNDExMjksImp0aSI6ImRlMzZhMzgwLWYwZTYtNDE3My05YjJjLTdiMTJjZjU3YzE5MSJ9.enXf7NIOSSNFhidKMSHtSzK36wRGBc9S-RS6Ajw7XM8";
	

	@RequestMapping("auth")
	public String auth() throws Exception {
		StringBuilder qstr = new StringBuilder();
		qstr.append("response_type=" + response_type).append("&client_id=" + client_id)
				.append("&redirect_uri=" + redirect_uri).append("&scope=" + scope).append("&state=" + state)
				.append("&auth_type=" + auth_type);

		return "redirect:" + reqURL + "?" + qstr.toString();
	}

	@RequestMapping("/callback")
	public String callback(@RequestParam Map<String, Object> map, HttpSession session) {
		System.out.println("인증코드" + map.get("code"));
		String code = (String) map.get("code");

		// access_token 받기
		String access_token = bankAPI.getAccessToken(code);
		System.out.println("access_token" + access_token);

		// session
		session.setAttribute("access_token", access_token);
		return "home";
	}

	@RequestMapping("/getAccountList")
	public String userinfo(HttpSession session, HttpServletRequest request, Model model) {
//		String access_token = (String)session.getAttribute("access_token")
//		String access_token  = request.getParameter("access_token");
		String use_num = "1100770529";
		Map<String, Object> map = bankAPI.getAccountList(access_token, use_num);
		System.out.println("userinfo=" + map);
		model.addAttribute("list", map);

		return "bank/getAccountList";
	}

	@RequestMapping("/getBalance")
	public String getBalance(BankVO vo,Model model) {
		vo.setAccess_token(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		System.out.println("잔액 =" + map);
		model.addAttribute("balance", map);
		return "bank/getBalance";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxGetBalance")
	public Map ajaxGetBalance(BankVO vo) {
		vo.setAccess_token(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		System.out.println("잔액 =" + map);

		
		return map;
		
	}
	@RequestMapping("/getOrgAuthorize")
	public String getOrgAuthorize() {
		Map<String, Object> map = bankAPI.getOrgAccessToken();
		System.out.println("access_token: " + map.get("access_token"));
		return "home";
	}
	
	//페이지 이동
	@RequestMapping("getBiz")
	public String getBizForm() {
		return "bank/getBiz";
	}
	
	
	//크롤링결과 조회
//	@PostMapping("/getBiz")
//		public String getBiz(@RequestParam String bizno,Model model) { 	//VO,map,String 
//		//크로링
//		String company = "";
//		String url="https://bizno.net/?query=" + company;
//		Document doc = Jsou.connect(url).get();
//		Elements element = doc.select(".details");
//		String bizName = element.text();
//		System.out.println(bizName);
//		//모델에 저장하여 뷰페이지로 전달
//		model.addAttribute("bizname"m bizname);
//			return "bank/getBiz";
//	}
}
