package com.example.service;

import java.util.List;

import com.example.model.QualificationModel;

public interface QualificationSearchService {

	/** 資格マスタ検索 */
	public List<QualificationModel> getQualification(QualificationModel qualificationModel);

	/** 資格マスタ登録 */
	public int insertQualification(QualificationModel qualificationModel);

	/** 資格マスタ更新 */
	public int updateQualification(QualificationModel qualificationModel);
	
	/** 資格マスタ削除 */
	public int deleteQualification(QualificationModel qualificationModel);

	/** ユーザーー資格連関検索 */
	public int getUserQualification(QualificationModel qualificationModel);
}
