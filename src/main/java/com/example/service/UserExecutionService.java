package com.example.service;

import com.example.model.UserModel;

public interface UserExecutionService {
	
	/** ユーザーテーブル登録 */
	public int insertUser(UserModel userModel);
	
	/** ユーザーテーブル更新 */
	public int updateUser(UserModel userModel) throws Exception;

}
