package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.QualificationModel;
import com.example.repository.QualificationMapper;
import com.example.service.QualificationSearchService;

@Service
public class QualificationSearchServiceImpl implements QualificationSearchService{
	
	@Autowired
	private QualificationMapper mapper;
	
	/** ユーザーテーブル検索 */
	public List<QualificationModel> getQualification(QualificationModel qualificationModel) {
		return mapper.findQualification(qualificationModel);
	}
}
