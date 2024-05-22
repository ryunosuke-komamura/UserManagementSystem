package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.UserModel;

@Mapper
public interface UserMapper {

	/** ユーザーテーブル検索 */
	public List<UserModel> findUser(UserModel userModel);
	
	/** ユーザーテーブル登録 */
	public int insertUser(UserModel userModel);
	
	/** ユーザーテーブル更新 */
	public int updateUser(UserModel userModel);
	
	/** ユーザーテーブル削除 */
	public int deleteUser(UserModel userModel);
}
