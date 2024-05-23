package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.QualificationModel;

@Mapper
public interface QualificationMapper {

	/** 資格マスタ検索 */
	public List<QualificationModel> findQualification(QualificationModel qualificationModel);

	/** 資格マスタ登録 */
	public int insertQualification(QualificationModel qualificationModel);

	/** 資格マスタ更新 */
	public int updateQualification(QualificationModel qualificationModel);

	/** 資格マスタ削除 */
	public int deleteQualification(QualificationModel qualificationModel);
}
