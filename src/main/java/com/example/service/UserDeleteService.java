package com.example.service;

import com.example.model.UserModel;

public interface UserDeleteService {

	/** ユーザー削除 */
	public int deleteUser(UserModel userModel);
}
