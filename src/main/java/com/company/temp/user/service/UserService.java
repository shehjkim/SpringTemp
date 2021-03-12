package com.company.temp.user.service;

import java.util.List;

public interface UserService {


	public int insertUser(UserVO vo);
	
	public int updateUser(UserVO vo);
	
	public int deleteUser(UserVO vo);
	
	public UserVO getUser(UserVO vo);
	
	public List<UserVO> getSearchUser(UserVO vo);
	
	//로그인
	//public int login(UserVO vo); 세분화 하려면 int
	public boolean logCheck(UserVO vo);
	
	
}
