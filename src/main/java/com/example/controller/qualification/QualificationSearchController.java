package com.example.controller.qualification;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.QualificationForm;
import com.example.model.QualificationModel;
import com.example.service.QualificationSearchService;
import com.example.util.UtilConst;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_QUALIFICATION)
public class QualificationSearchController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private QualificationSearchService qualificationSearchService;

	@GetMapping(UtilConst.MAPPING_PATH_SEARCH)
	/** 画面遷移：資格検索画面 */
	public String getQualificationSearch(Model model, @ModelAttribute @Validated QualificationForm form, BindingResult bindingResult) {

		form.setQualificationId(null);
		form.setQualificationName(null);
		//qualificationSearch.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}

	@PostMapping(UtilConst.MAPPING_PATH_SEARCH)
	/** 機能：ユーザー検索ボタン */
	public String postQualificationSearch(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			// バリデーションチェックエラー後の遷移先
			return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
		}

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);

		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);

		model.addAttribute("qualificationList", qualificationList);

		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}

	@PostMapping(UtilConst.MAPPING_PATH_DELETE)
	/** 機能：削除ボタン */
	public String postQualificationDelete(Model model ,@ModelAttribute @Validated QualificationForm form, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {

			// バリデーションチェックエラー後の遷移先
			return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
		}
		
		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);
		
		// SearchServiceの実行
		qualificationSearchService.deleteQualification(qualificationModel);
		
		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);

		model.addAttribute("qualificationList",qualificationList);

		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}

}
