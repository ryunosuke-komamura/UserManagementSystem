package com.example.controller.user;

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

import com.example.form.UserForm;
import com.example.model.UserModel;
import com.example.service.UserSearchService;
import com.example.util.UtilConst;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_USER)
public class UserRegisterController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserSearchService userSearchService;

	@GetMapping(UtilConst.MAPPING_PATH_REGISTER)
	/** 画面遷移：ユーザー登録変更画面 */
	public String getUserRegister(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
		
		// formを個別modelに変換
		UserModel userModel = modelMapper.map(form, UserModel.class);
		
		// SearchServiceの実行
		List<UserModel> userList = userSearchService.getUser(userModel);
		
		model.addAttribute("userList",userList);
		
		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_REGISTER;
	}
	
	@PostMapping(UtilConst.MAPPING_PATH_UPDATE)
	/** 画面遷移：ユーザー登録変更画面 */
	public String postUserUpdate(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
		
		// formを個別modelに変換
		UserModel userModel = modelMapper.map(form, UserModel.class);
		
		// SearchServiceの実行
		List<UserModel> userList = userSearchService.getUser(userModel);
		
		if(!userList.isEmpty()) {
			form.setUserId(userList.get(0).getUserId());
			form.setUserName(userList.get(0).getUserName());
			model.addAttribute("userList",userList);
		}
		
		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_REGISTER;
	}

}
