package com.example.model;

import lombok.Data;

@Data
public class UserModel {

	/**
	 * ユーザーID
	 */
	private String userId;
	
	/**
	 * ユーザー名
	 */
	private String userName;
	
	/**
	 * パスワード
	 */
	private String password;

	/**
	 * 資格リスト
	 */
	//private List<> qualificationList;
}
