package com.example.controller.user;

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

import com.example.form.UserForm;
import com.example.model.QualificationModel;
import com.example.model.UserModel;
import com.example.service.QualificationSearchService;
import com.example.service.UserExecutionService;
import com.example.util.UtilConst;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_USER)
public class UserConfirmationController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserExecutionService userExecutionService;

	@Autowired
	private QualificationSearchService qualificationSearchService;
	
	@PostMapping(UtilConst.MAPPING_PATH_CONFIRMATION)
	/** 画面遷移：ユーザー登録内容確認画面 */
	public String getUserConfirmation(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
		
		// 登録変更画面で選択した資格一覧の名称取得
		getQualificationList(model, form);
		
		//userConfirmation.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_CONFIRMATION;
	}
	
	@PostMapping(UtilConst.MAPPING_PATH_EXECUTION)
	public String postUserConfirmation(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {

		// formを個別modelに変換
		UserModel userModel = modelMapper.map(form, UserModel.class);
		try {
			// 編集モードによって実行Serviceの変更
			if(form.getEditMode() == UtilConst.EDIT_MODE_INSERT) {
				
				userExecutionService.insertUser(userModel);
				
			}else {
				userExecutionService.updateUser(userModel);
			}
		}catch(IllegalArgumentException err) {
			//userConfirmation.htmlに遷移
			return UtilConst.RESPONSE_PATH_USER_CONFIRMATION;
		}

		form.setUserId("");
		form.setUserName("");
		
		//userSearch.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}
	
	/** 資格一覧の検索実行 */
	private void getQualificationList(Model model, UserForm form) {
		
		// formを個別modelに変換
		UserModel userModel = modelMapper.map(form, UserModel.class);
		// 送信パラメータに資格ID一覧が無ければ終了
		if(userModel.getQualificationIds() == null) {
			return;
		}
		
		// 資格ID一覧から名称取得
		List<QualificationModel> qualificationList = new ArrayList<QualificationModel>();
		for(String qualificationId : userModel.getQualificationIds()) {
			QualificationModel searchQualificationModel =new QualificationModel();
			searchQualificationModel.setQualificationId(qualificationId);
			
			// 資格一覧の検索実行
			List<QualificationModel> resultQualification = qualificationSearchService.getQualification(searchQualificationModel);
			qualificationList.add(resultQualification.get(0));
		}
		
		model.addAttribute("qualificationList",qualificationList);
	}

}
