package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserModel;
import com.example.repository.UserMapper;
import com.example.repository.UserQualMapper;
import com.example.service.UserDeleteService;

@Service
public class UserDeleteServiceImpl implements UserDeleteService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserQualMapper userQualMapper;

	/** ユーザー削除 */
	public int deleteUser(UserModel userModel) {
		userQualMapper.deleteUserQual(userModel);
		
		return userMapper.deleteUser(userModel);
	}
}
