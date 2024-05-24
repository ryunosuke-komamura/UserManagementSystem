package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.UserModel;
import com.example.repository.UserMapper;
import com.example.repository.UserQualMapper;
import com.example.service.UserExecutionService;

@Service
public class UserExecutionServiceImpl implements UserExecutionService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserQualMapper userQualMapper;
	

	final int EXECUTE_FLAG_DEFAULT = 0;
	final int EXECUTE_FLAG_DELETE = 1;
	final int EXECUTE_FLAG_INSERT = 2;
	
	/** ユーザーテーブル登録 */
	@Transactional
	public int insertUser(UserModel userModel) {
		int result = userMapper.insertUser(userModel);
		
		for(String qualificationId: userModel.getQualificationIds()) {
			userQualMapper.insertUserQual(userModel.getUserId(), qualificationId);
		}
		
		return result;
	};
	
	/** ユーザーテーブル更新 
	 * @throws Exception */
	@Transactional
	public int updateUser(UserModel userModel) throws Exception {
		
		List<UserModel> userList = userMapper.findUser(userModel);
		if(userList.isEmpty()) {
			throw new Exception();
		}

		int result = userMapper.updateUser(userModel);
		
		List<String> userQualList = userQualMapper.findUserQual(userModel);

//		List<String> insertList = new ArrayList<String>();
		for(String insertQualificationId: userModel.getQualificationIds()) {
			boolean noInsertFlag = false;

			for(String selectQualificationId: userQualList){
				if(selectQualificationId.equals(insertQualificationId)) {
					noInsertFlag = true;
					break;
				}
			}
			
			if(!noInsertFlag) {
//				insertList.add(insertQualificationId);
				userQualMapper.insertUserQual(userModel.getUserId(), insertQualificationId);
			}
			
		}
		
//		List<String> deleteList = new ArrayList<String>();
		for(String selectQualificationId: userQualList){
			boolean noDeleteFlag = false;

			for(String insertQualificationId: userModel.getQualificationIds()) {
				if(selectQualificationId.equals(insertQualificationId)) {
					noDeleteFlag = true;
					break;
				}
			}
			
			if(!noDeleteFlag) {
//				deleteList.add(selectQualificationId);
				userQualMapper.deleteOneUserQual(userModel.getUserId(), selectQualificationId);
			}
			
		}
		
		return result;
	};
}
