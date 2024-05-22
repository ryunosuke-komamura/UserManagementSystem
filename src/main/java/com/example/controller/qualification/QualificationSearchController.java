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

	@PostMapping(UtilConst.MAPPING_PATH_REGISTER)
	/** 機能：新規作成ボタン */
	public String postQualificationRegister(Model model, @ModelAttribute @Validated QualificationForm form, BindingResult bindingResult) {
		// 実行モードに登録を設定
		model.addAttribute("runMode", "1");
		//qualificationSearch.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
	}

	/** 機能：削除ボタン */
	
	@PostMapping(UtilConst.MAPPING_PATH_UPDATE)
	/** 機能：変更ボタン */
	public String postQualificationUpdate(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			// バリデーションチェックエラー後の遷移先
			return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
		}

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);

		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);

		model.addAttribute("qualificationList", qualificationList);

		return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
	}
}
