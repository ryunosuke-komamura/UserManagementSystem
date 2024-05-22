package com.example.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.UserModel;

@Mapper
public interface UserQualMapper {
	
	/** ユーザー資格連関テーブル削除 */
	public int deleteUserQual(UserModel userModel);
}
