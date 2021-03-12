package com.company.temp.user.service.impl;

import java.util.List;

import com.company.temp.user.service.UserVO;



public interface UserMapper {

	public int insert(UserVO vo);
	
	public int update(UserVO vo);
	
	public int delete(UserVO vo);
	
	public UserVO getUser(UserVO vo);
	
	public List<UserVO> getSearchUser(UserVO vo);
}
