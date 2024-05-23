package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.UserModel;
import com.example.repository.UserMapper;
import com.example.service.UserExecutionService;

@Service
public class UserExecutionServiceImpl implements UserExecutionService {

	@Autowired
	private UserMapper userMapper;
	
	/** ユーザーテーブル登録 */
	@Transactional
	public int insertUser(UserModel userModel) {
		return userMapper.insertUser(userModel);
	};
	
	/** ユーザーテーブル更新 
	 * @throws Exception */
	@Transactional
	public int updateUser(UserModel userModel) throws Exception {
		
		List<UserModel> userList = userMapper.findUser(userModel);
		if(!userList.isEmpty()) {
			throw new Exception();
		}
		
		return userMapper.updateUser(userModel);
	};
}
