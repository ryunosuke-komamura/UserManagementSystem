package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.QualificationModel;

@Mapper
public interface QualificationMapper {

	/** ユーザーテーブル検索 */
	public List<QualificationModel> findQualification(QualificationModel qualificationModel);
	
}
