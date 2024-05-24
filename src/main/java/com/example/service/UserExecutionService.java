package com.example.service;

import com.example.model.UserModel;

public interface UserExecutionService {
	
	/** ユーザーテーブル登録 */
	public void insertUser(UserModel userModel);
	
	/** ユーザーテーブル更新 */
	public void updateUser(UserModel userModel) throws IllegalArgumentException;

}
