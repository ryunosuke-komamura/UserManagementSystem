package com.example.service;

import java.util.List;

import com.example.model.UserModel;

public interface UserSearchService {

	/** ユーザーテーブル検索 */
	public List<UserModel> getUser(UserModel userModel);
}
