package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserModel;
import com.example.repository.UserMapper;
import com.example.repository.UserQualMapper;
import com.example.service.UserSearchService;

@Service
public class UserSearchServiceImpl implements UserSearchService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserQualMapper userQualMapper;
	
	/** ユーザーテーブル検索 */
	public List<UserModel> getUser(UserModel userModel) {
		return userMapper.findUser(userModel);
	}
}
