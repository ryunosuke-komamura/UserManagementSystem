package com.example.model;

import java.util.List;

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
	private List<String> qualificationIds;
}
