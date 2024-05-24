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
	public void insertUser(UserModel userModel) {
		List<UserModel> userList = userMapper.findUser(new UserModel());
		String createUserId = "U"+String.format("%05d", userList.size());
		userModel.setUserId(createUserId);
		// ユーザーの登録
		userMapper.insertUser(userModel);

		// 送信パラメータに資格ID一覧が無ければユーザー登録のみ。
		if(userModel.getQualificationIds() == null) {
			return;
		}
		
		// 資格ID一覧の登録
		for(String qualificationId: userModel.getQualificationIds()) {
			userQualMapper.insertUserQual(userModel.getUserId(), qualificationId);
		}
		
		return;
	};
	
	/** ユーザーテーブル更新 
	 * @throws Exception */
	@Transactional
	public void updateUser(UserModel userModel) throws IllegalArgumentException {
		
		UserModel findUserModel = new UserModel();
		findUserModel.setUserId(userModel.getUserId());
		List<UserModel> userList = userMapper.findUser(findUserModel);
		if(userList.isEmpty()) {
			throw new IllegalArgumentException();
		}

		// ユーザー情報の更新
		userMapper.updateUser(userModel);
		
		// DBに既に登録されているユーザー資格連関一覧
		List<String> userQualList = userQualMapper.findUserQual(userModel);

		// 入力データ と DBに既に登録されている一覧 を比較。入力データが主軸。
		for(String insertQualificationId: userModel.getQualificationIds()) {
			boolean noInsertFlag = false;

			for(String selectQualificationId: userQualList){
				if(selectQualificationId.equals(insertQualificationId)) {
					noInsertFlag = true;
					break;
				}
			}
			
			// 入力データと同じものがなかった場合、登録対象
			if(!noInsertFlag) {
				userQualMapper.insertUserQual(userModel.getUserId(), insertQualificationId);
			}
			
		}

		// 入力データ と DBに既に登録されている一覧 を比較。DBデータが主軸。
		for(String selectQualificationId: userQualList){
			boolean noDeleteFlag = false;

			// 入力データ と DBに既に登録されている一覧 を比較。
			for(String insertQualificationId: userModel.getQualificationIds()) {
				if(selectQualificationId.equals(insertQualificationId)) {
					noDeleteFlag = true;
					break;
				}
			}
			
			// DBデータと同じものがなかった場合、削除対象
			if(!noDeleteFlag) {
				userQualMapper.deleteOneUserQual(userModel.getUserId(), selectQualificationId);
			}
			
		}
		
		return;
	};
}
