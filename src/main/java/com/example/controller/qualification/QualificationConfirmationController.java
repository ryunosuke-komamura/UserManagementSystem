package com.example.controller.qualification;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.QualificationForm;
import com.example.model.QualificationModel;
import com.example.service.QualificationSearchService;
import com.example.util.UtilConst;

import io.micrometer.common.util.StringUtils;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_QUALIFICATION)
public class QualificationConfirmationController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private QualificationSearchService qualificationSearchService;
	
	@PostMapping(UtilConst.MAPPING_PATH_CONFIRMATION)
	/** 画面遷移：資格登録変更画面 */
	public String postQualificationConfirmation(Model model ,@ModelAttribute @Validated QualificationForm form, BindingResult bindingResult) {
		
		List<String> errorMsg = new ArrayList<String>();
		// バリデーションチェック
		// 必須チェック
		if(StringUtils.isBlank(form.getQualificationId())) {
			errorMsg.add("資格IDは必須です。");
		}

		// 必須チェック
		if(form.getQualificationName().isEmpty()) {
			errorMsg.add("資格名は必須です。");
		}
		
		// エラー確認
		if(!errorMsg.isEmpty()) {
			model.addAttribute("message",errorMsg);
			return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
		}

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);
		
		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);
		
		if(!qualificationList.isEmpty()) {
			form.setQualificationId(qualificationList.get(0).getQualificationId());
			form.setQualificationName(qualificationList.get(0).getQualificationName());
			model.addAttribute("qualificationList", qualificationList);
		}

		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_CONFIRMATION;
	}

	@PostMapping(UtilConst.MAPPING_PATH_EXECUTION)
	/** 資格登録・更新 */
	public String postQualificationRegister(Model model ,@ModelAttribute @Validated QualificationForm form, BindingResult bindingResult) {
		
		QualificationModel qualificationModel = new QualificationModel();

		// 資格IDを格納
		qualificationModel.setQualificationId(modelMapper.map(form, QualificationModel.class).getQualificationId());
		
		// 存在チェック
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);
		
		// 資格名を格納
		qualificationModel.setQualificationName(modelMapper.map(form, QualificationModel.class).getQualificationName());
		
		// データ存在確認。
		if(!qualificationList.isEmpty()) {
			qualificationSearchService.updateQualification(qualificationModel);
		} else {
			qualificationSearchService.insertQualification(qualificationModel);
		}

		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}
}
