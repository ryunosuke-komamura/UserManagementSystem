package com.example.controller.user;

import java.util.ArrayList;
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
import com.example.service.UserDeleteService;
import com.example.service.UserSearchService;
import com.example.util.UtilConst;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_USER)
public class UserSearchController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserSearchService userSearchService;
	
	@Autowired
	private UserDeleteService userDeleteService;
	
	 // HttpSession型のフィールドを定義する
    private HttpSession session;

    // コンストラクタを作成し、@Autowiredアノテーションを付与する
    @Autowired
    public UserSearchController(HttpSession session) {
        // フィールドに代入する
        this.session = session;
    }

	@GetMapping(UtilConst.MAPPING_PATH_SEARCH)
	/** 画面遷移：ユーザー検索画面 */
	public String getUserSearch(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
		form.setUserId("");
		form.setUserName("");
		//userSearch.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}

	@PostMapping(UtilConst.MAPPING_PATH_SEARCH)
	/** 機能：ユーザー検索ボタン */
	public String postUserSearch(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
				
		if(bindingResult.hasErrors()) {

			// バリデーションチェックエラー後の遷移先
			return UtilConst.RESPONSE_PATH_USER_SEARCH;
		}
		
		// formを個別modelに変換
		UserModel serchCondition = modelMapper.map(form, UserModel.class);
		
		// SearchServiceの実行
		getUserList(model, serchCondition);
		
		session.setAttribute("serchCondition", serchCondition);

		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}

	@PostMapping(UtilConst.MAPPING_PATH_DELETE)
	/** 機能：ユーザー削除ボタン */
	public String postUserDelete(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
				
		// formを個別modelに変換
		UserModel userModel = modelMapper.map(form, UserModel.class);

		List<String> errorMsg = new ArrayList<String>();
		// バリデーションチェック
		// 必須チェック
		if(userModel.getUserId() == null) {
			errorMsg.add("ユーザーを選択してください。");
		}
		
		// エラー確認
		if(!errorMsg.isEmpty()) {
			getUserList(model, (UserModel)session.getAttribute("serchCondition"));
			model.addAttribute("message",errorMsg);
			return UtilConst.RESPONSE_PATH_USER_SEARCH;
		}
		
		// ユーザー削除の実行
		userDeleteService.deleteUser(userModel);

		UserModel serchCondition = (UserModel)session.getAttribute("serchCondition");
		// 検索結果の再実行
		getUserList(model, serchCondition);
		form.setUserId(serchCondition.getUserId());
		form.setUserName(serchCondition.getUserName());

		model.addAttribute("message","削除完了しました");
		return UtilConst.RESPONSE_PATH_USER_SEARCH;
	}
	
	/** ユーザー一覧の検索 */
	private void getUserList(Model model, UserModel serchCondition) {
		List<UserModel> userList = userSearchService.getUser(serchCondition);
		model.addAttribute("userList",userList);
	}
	
	
}
