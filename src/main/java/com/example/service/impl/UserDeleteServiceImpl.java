package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserModel;
import com.example.repository.UserMapper;
import com.example.service.UserDeleteService;

@Service
public class UserDeleteServiceImpl implements UserDeleteService{

	@Autowired
	private UserMapper mapper;
	
	public int deleteUser(UserModel userModel) {
		return mapper.deleteUser(userModel);
	}
}
