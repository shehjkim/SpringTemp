package com.company.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;

public class GsonTest {
	public static void main(String[] args) throws JsonProcessingException{
		Map<String, Object> map = new HashMap<>();
		map.put("name", "홍길동");
		map.put("age", 20);
		
		//gson 이용
		//자바 객체 -> String(json)
		Gson gson = new Gson();
		String str = gson.toJson(map);
		System.out.println(str);	//{"name":"홍길동","age":20}
		
		System.out.println("-------------");
		
		//String(json) -> 자바객체
		str = "{\"name\":\"홍길동\",\"age\":20, \"dept\":\"개발\"}";
		map = gson.fromJson(str, Map.class); //2번째: 변경하고자하는 class값 넣어줌
		System.out.println(map.get("dept"));
		
		
		//jackson : spring json libray @ResponseBody @RequestBody
		//자바 객체 - > String (json)
		JsonMapper mapper = new JsonMapper();
		String str2 = mapper.writeValueAsString(map);
	
	
		//String(json) -> 자바 객체
		map = mapper.readValue(str2, Map.class);
		System.out.println("jackson : " + map);
	}
}
