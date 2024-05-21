package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserModel;
import com.example.repository.UserMapper;
import com.example.service.UserSearchService;

@Service
public class UserSearchServiceImpl implements UserSearchService{

//	@Autowired
//	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	/** ユーザーテーブル検索 */
	public List<UserModel> getUser(UserModel userModel) {
//		Map<String, Object> map = repository.findByUserId(userId);
//		
//		UserModel user = new UserModel();
//		user.setUserId((String)map.get("USER_ID"));
//		user.setUserName((String)map.get("USER_NAME"));
//		
//		return user;
		return mapper.findUser(userModel);
	}
}
