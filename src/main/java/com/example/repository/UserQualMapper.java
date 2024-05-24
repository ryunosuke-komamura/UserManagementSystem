package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.UserModel;

@Mapper
public interface UserQualMapper {

	/** ユーザーテーブル検索 */
	public List<String> findUserQual(UserModel userModel);
	
	/** ユーザー資格連関テーブル削除 */
	public int deleteUserQual(UserModel userModel);
	
	/** ユーザー資格連関テーブル削除 */
	public int deleteOneUserQual(String userId, String qualificationId);
	
	/** ユーザー資格連関テーブル登録 */
	public int insertUserQual(String userId, String qualificationId);
}
