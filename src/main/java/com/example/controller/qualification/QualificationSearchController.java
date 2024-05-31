package com.example.controller.qualification;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(UtilConst.MAPPING_PATH_QUALIFICATION)
public class QualificationSearchController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private QualificationSearchService qualificationSearchService;

	// HttpSession型のフィールドを定義する
	private HttpSession session;

	// コンストラクタを作成し、@Autowiredアノテーションを付与する
	@Autowired
	public QualificationSearchController(HttpSession session) {
		// フィールドに代入する
		this.session = session;
	}

	@GetMapping(UtilConst.MAPPING_PATH_SEARCH)
	/** 画面遷移：資格検索画面 */
	public String getQualificationSearch(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		form.setQualificationId(null);
		form.setQualificationName(null);
		//qualificationSearch.htmlに遷移
		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}

	@PostMapping(UtilConst.MAPPING_PATH_SEARCH)
	/** 機能：資格検索ボタン */
	public String postQualificationSearch(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			// バリデーションチェックエラー後の遷移先
			return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
		}

		// formを個別modelに変換
		QualificationModel serchCondition = modelMapper.map(form, QualificationModel.class);

		// SearchServiceの実行
		getQualificationList(model, serchCondition);

		// 資格0件チェック
		if (ObjectUtils.isEmpty(model.getAttribute("qualificationList"))) {
			model.addAttribute("message","該当の資格はありませんでした");
		}
		session.setAttribute("serchCondition", serchCondition);

		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}

	@PostMapping(UtilConst.MAPPING_PATH_DELETE)
	/** 機能：削除ボタン */
	public String postQualificationDelete(Model model, @ModelAttribute @Validated QualificationForm form,
			BindingResult bindingResult) {

		// formを個別modelに変換
		QualificationModel qualificationModel = modelMapper.map(form, QualificationModel.class);

		List<String> errorMsg = new ArrayList<String>();
		// バリデーションチェック
		// 必須チェック
		if(qualificationModel.getQualificationId() == null) {
			errorMsg.add("資格を選択してください。");
		} else {
			// ユーザーー資格連関テーブル存在確認
			int usreQualCount = qualificationSearchService.getUserQualification(qualificationModel);
			
			if(usreQualCount > 0) {
				errorMsg.add("資格がユーザーに紐づいているため削除出来ません。");
			}
		}
		
		// エラー確認
		if(!errorMsg.isEmpty()) {
			getQualificationList(model, (QualificationModel)session.getAttribute("serchCondition"));
			form.setQualificationId("");
			model.addAttribute("message",errorMsg);
			return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
		}

		// 資格削除の実行
		qualificationSearchService.deleteQualification(qualificationModel);

		QualificationModel serchCondition = (QualificationModel)session.getAttribute("serchCondition");

		// 検索結果の再実行
		getQualificationList(model, serchCondition);
		form.setQualificationId(serchCondition.getQualificationId());
		form.setQualificationName(serchCondition.getQualificationName());

		model.addAttribute("message","削除完了しました");

		return UtilConst.RESPONSE_PATH_QUALIFICATION_SEARCH;
	}

	/** ユーザー一覧の検索 */
	private void getQualificationList(Model model, QualificationModel serchCondition) {
		List<QualificationModel> qualificationList = qualificationSearchService.getQualification(serchCondition);
		model.addAttribute("qualificationList", qualificationList);
	}
}
