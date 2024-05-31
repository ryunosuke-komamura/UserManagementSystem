package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.QualificationModel;
import com.example.repository.QualificationMapper;
import com.example.repository.UserQualMapper;
import com.example.service.QualificationSearchService;

@Service
public class QualificationSearchServiceImpl implements QualificationSearchService{
	
	@Autowired
	private QualificationMapper mapper;

	@Autowired
	private UserQualMapper userQualMapper;

	/** ユーザーテーブル検索 */
	public List<QualificationModel> getQualification(QualificationModel qualificationModel) {
		return mapper.findQualification(qualificationModel);
	}

	@Override
	public int insertQualification(QualificationModel qualificationModel) {
		return mapper.insertQualification(qualificationModel);
	}

	@Override
	public int updateQualification(QualificationModel qualificationModel) {
		return mapper.updateQualification(qualificationModel);
	}

	/** ユーザー削除 */
	public int deleteQualification(QualificationModel qualificationModel) {
		return mapper.deleteQualification(qualificationModel);
	}

	@Override
	public int getUserQualification(QualificationModel qualificationModel) {
		List<String> qualificationIds = userQualMapper.findUserQualByQualId(qualificationModel);
		return qualificationIds.size();
	}

}
