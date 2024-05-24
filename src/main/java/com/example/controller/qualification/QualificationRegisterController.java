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
public class QualificationRegisterController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private QualificationSearchService qualificationSearchService;

	@GetMapping(UtilConst.MAPPING_PATH_REGISTER)
	/** 画面遷移：資格登録変更画面 */
	public String getQualificationRegister(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		// 内容確認画面から戻るボタンで戻ってきた時、変更モードだった場合はpostUserの方を実行
		if (form.getEditMode() == UtilConst.EDIT_MODE_UPDATE) {
			return postQualificationUpdate(model, form, bindingResult);
		}

		//　編集モード登録(0)に設定
		form.setEditMode(UtilConst.EDIT_MODE_INSERT);

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);

		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);

		model.addAttribute("qualificationList", qualificationList);

		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
	}

	@PostMapping(UtilConst.MAPPING_PATH_REGISTER)
	/** 画面遷移：資格登録変更画面 */
	public String postQualificationRegister(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		// 内容確認画面から戻るボタンで戻ってきた時、変更モードだった場合はpostUserの方を実行
		if (form.getEditMode() == UtilConst.EDIT_MODE_UPDATE) {
			return postQualificationUpdate(model, form, bindingResult);
		}

		//　編集モード登録(0)に設定
		form.setEditMode(UtilConst.EDIT_MODE_INSERT);

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);

		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);

		model.addAttribute("qualificationList", qualificationList);

		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
	}
	
	@PostMapping(UtilConst.MAPPING_PATH_UPDATE)
	/** 画面遷移：ユーザー登録変更画面 */
	public String postQualificationUpdate(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		// 編集モード変更(1)に設定
		form.setEditMode(UtilConst.EDIT_MODE_UPDATE);

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);

		// SearchServiceの実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(qualificationModel);

		if (!qualificationList.isEmpty()) {
			form.setQualificationId(qualificationList.get(0).getQualificationId());
			form.setQualificationName(qualificationList.get(0).getQualificationName());
			model.addAttribute("qualificationList", qualificationList);
		}

		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_REGISTER;
	}

}
