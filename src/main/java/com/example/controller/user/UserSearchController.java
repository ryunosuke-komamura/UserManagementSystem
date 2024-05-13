package com.example.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.UserFrom;
import com.example.model.UserEntity;
import com.example.util.UtilConst;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_USER)
public class UserSearchController {

	@GetMapping(UtilConst.MAPPING_PATH_SEARCH)
	public String getUserSearch() {
		//userSearch.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}
	
	@PostMapping(UtilConst.MAPPING_PATH_SEARCH)
	public String postUserSearch(Model model ,@ModelAttribute UserFrom form) {
		
		List<UserEntity> userList = new ArrayList<UserEntity>();
		UserEntity user = new UserEntity();
		user.setUserId("0001");
		user.setUserName("田中太郎");
		userList.add(user);
		
		model.addAttribute("userList",userList);

		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}
	
}
