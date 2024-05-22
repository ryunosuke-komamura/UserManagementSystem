package com.example.service;

import java.util.List;

import com.example.model.QualificationModel;

public interface QualificationSearchService {

	/** ユーザーテーブル検索 */
	public List<QualificationModel> getQualification(QualificationModel qualificationModel);
}
