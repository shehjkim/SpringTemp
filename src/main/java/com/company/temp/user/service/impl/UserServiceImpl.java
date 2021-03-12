package com.company.temp.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.temp.user.service.UserService;
import com.company.temp.user.service.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired UserMapper dao;

	@Override
	public int insertUser(UserVO vo) {
		return dao.insert(vo);
	}

	@Override
	public int updateUser(UserVO vo) {
		return dao.update(vo);
	}

	@Override
	public int deleteUser(UserVO vo) {
		return dao.delete(vo);
	}

	@Override
	public UserVO getUser(UserVO vo) {
		return dao.getUser(vo);
	}

	@Override
	public List<UserVO> getSearchUser(UserVO vo) {
		return dao.getSearchUser(vo);
	}

	@Override
	public boolean logCheck(UserVO vo) {
		
		//단건조회
			UserVO uservo = dao.getUser(vo);
		//id일치하는지 체크
			if(uservo == null) {
				return false;
			}
		//pw일치여부 체크
			if(uservo.getPassword().equals(vo.getPassword())) {
				return true;
			} else {
				return false;
			}
		
	} // end of logCheck

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("====================== userService 실행");
		UserVO uservo = new UserVO();
		uservo.setId(username);
		uservo = dao.getUser(uservo);
		
		if(uservo == null) {
			throw new UsernameNotFoundException("no user");
		}
		
		return uservo;
	}

}
