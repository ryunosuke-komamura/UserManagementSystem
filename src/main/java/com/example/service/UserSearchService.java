package com.example.service;

import java.util.List;

import com.example.model.UserModel;

public interface UserSearchService {

	/** ユーザー1件検索 */
	public List<UserModel> getUser();
}
