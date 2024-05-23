package com.example.controller.user;

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
import com.example.model.UserModel;
import com.example.service.UserExecutionService;
import com.example.util.UtilConst;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_USER)
public class UserConfirmationController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserExecutionService userExecutionService;
	
	@PostMapping(UtilConst.MAPPING_PATH_CONFIRMATION)
	/** 画面遷移：ユーザー登録内容確認画面 */
	public String getUserConfirmation(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
		
		//userConfirmation.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_CONFIRMATION;
	}
	
	@PostMapping(UtilConst.MAPPING_PATH_EXECUTION)
	public String postUserConfirmation(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {

		
		// formを個別modelに変換
		UserModel userModel = modelMapper.map(form, UserModel.class);
		try {
			if(form.getEditMode() == UtilConst.EDIT_MODE_INSERT) {
				
				userExecutionService.insertUser(userModel);
				
			}else {
				userExecutionService.updateUser(userModel);
			}
		}catch(Exception err) {
			//userConfirmation.htmlに遷移
			return UtilConst.RESPONSE_PATH_USER_CONFIRMATION;
		}
		
		//userConfirmation.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}

}
