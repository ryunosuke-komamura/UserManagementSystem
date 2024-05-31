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
import com.example.form.UserQualForm;
import com.example.model.QualificationModel;
import com.example.model.UserModel;
import com.example.service.QualificationSearchService;
import com.example.service.UserSearchService;
import com.example.util.UtilConst;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_USER)
public class UserRegisterController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private QualificationSearchService qualificationSearchService;
	
	 // HttpSession型のフィールドを定義する
   private HttpSession session;

   // コンストラクタを作成し、@Autowiredアノテーションを付与する
   @Autowired
   public UserRegisterController(HttpSession session) {
       // フィールドに代入する
       this.session = session;
   }

	@GetMapping(UtilConst.MAPPING_PATH_REGISTER)
	/** 画面遷移：ユーザー登録変更画面 */
	public String getUserRegister(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
		
		// 内容確認画面から戻るボタンで戻ってきた時、変更モードだった場合はpostUserの方を実行
		if(form.getEditMode() == UtilConst.EDIT_MODE_UPDATE) {
			return postUserUpdate(model, form, bindingResult);
		}
		
		//　編集モード登録(0)に設定
		form.setEditMode(UtilConst.EDIT_MODE_INSERT);

		// 資格一覧の検索実行
		setUserQualFormList(model, form.getQualificationIds());
		
		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_REGISTER;
	}
	
	@PostMapping(UtilConst.MAPPING_PATH_UPDATE)
	/** 画面遷移：ユーザー登録変更画面 */
	public String postUserUpdate(Model model ,@ModelAttribute @Validated UserForm form, BindingResult bindingResult) {

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
			UserModel serchCondition = (UserModel)session.getAttribute("serchCondition");
			List<UserModel> userList = userSearchService.getUser(serchCondition);
			model.addAttribute("userList",userList);
			model.addAttribute("message",errorMsg);
			return UtilConst.RESPONSE_PATH_USER_SEARCH;
		}

		//　編集モード変更(1)に設定
		form.setEditMode(UtilConst.EDIT_MODE_UPDATE);
		
		// SearchServiceの実行
		List<UserModel> userList = userSearchService.getUser(userModel);
		
		if(!userList.isEmpty()) {
			form.setUserId(userList.get(0).getUserId());
			form.setUserName(userList.get(0).getUserName());
			form.setQualificationIds(userList.get(0).getQualificationIds());

			setUserQualFormList(model, userList.get(0).getQualificationIds());
		}
		
		//userRegister.htmlに遷移
		return UtilConst.RESPONSE_PATH_USER_REGISTER;
	}
	
//	/**
//	 * 資格一覧の検索実行
//	 * @param model 
//	 */
//	private List<UserQualForm> getUserQualFormList(Model model) {
//		// 資格一覧の検索実行
//		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(new QualificationModel());
//
//		List<UserQualForm> userQualFormList = new ArrayList<UserQualForm>();
//		
//		for(QualificationModel qualification: qualificationList) {
//			UserQualForm userQualForm = new UserQualForm();
//			userQualForm.setSelectQualification(false);
//			userQualForm.setNo(qualification.getNo());
//			userQualForm.setQualificationId(qualification.getQualificationId());
//			userQualForm.setQualificationName(qualification.getQualificationName());
//			userQualFormList.add(userQualForm);
//		}
//		
//		return userQualFormList;
//	}
	
	/**
	 * 資格一覧の検索実行、チェックボックスセット
	 * @param model 
	 * @param userQualFormList 資格ID一覧
	 * @param qualificationIds ユーザー資格連関一覧
	 */
	private void setUserQualFormList(Model model,  List<String> qualificationIds) {
		// 資格一覧の検索実行
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(new QualificationModel());

		List<UserQualForm> userQualFormList = new ArrayList<UserQualForm>();
		
		for(QualificationModel qualification: qualificationList) {
			UserQualForm userQualForm = new UserQualForm();
			userQualForm.setSelectQualification(false);
			userQualForm.setNo(qualification.getNo());
			userQualForm.setQualificationId(qualification.getQualificationId());
			userQualForm.setQualificationName(qualification.getQualificationName());
			userQualFormList.add(userQualForm);
		}
		
		if(qualificationIds == null) {
			// 選択の資格一覧が無ければ、そのままセット
			model.addAttribute("userQualFormList",userQualFormList);
			return;
		}
		
		for(String searchQualification :qualificationIds) {
			for(UserQualForm userQualForm: userQualFormList) {
				if(userQualForm.getQualificationId().equals(searchQualification)) {
					// DBから取得したユーザー資格連関一覧が、資格一覧IDと一致したら、選択状態をセットする。
					userQualForm.setSelectQualification(true);
					break;
				}
			}
		}
		model.addAttribute("userQualFormList",userQualFormList);
		
	}

}
