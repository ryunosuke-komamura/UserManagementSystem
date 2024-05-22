package com.example.service;

import java.util.List;

import com.example.model.UserModel;

public interface UserSearchService {

	/** ユーザーテーブル検索 */
	public List<UserModel> getUser(UserModel userModel);

	/** ユーザー削除 */
	public int deleteUser(UserModel userModel);
}
