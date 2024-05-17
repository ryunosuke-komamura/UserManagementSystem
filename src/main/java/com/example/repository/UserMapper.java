package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.UserModel;

@Mapper
public interface UserMapper {

	/** ユーザー検索 */
	public List<UserModel> findUser();
	
}
