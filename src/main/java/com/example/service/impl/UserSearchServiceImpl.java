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
		List<UserModel> userList = userMapper.findUser(userModel);
		
		if(userList.size() > 0) {
			// ユーザーに紐づく資格一覧の検索
			List<String> qualificationIds = userQualMapper.findUserQual(userModel);
			
			userList.get(0).setQualificationIds(qualificationIds);
		}
		return userList;
	}
}
