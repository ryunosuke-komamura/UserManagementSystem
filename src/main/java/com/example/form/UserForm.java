
package com.example.form;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserForm {
	
	/**
	 * 編集モード
	 * 登録(0)：変更(1)
	 */
	private int editMode;

	/**
	 * ユーザーID
	 */
	@Length(min = 0, max = 6)
	private String userId;
	
	/**
	 * ユーザー名
	 */
	@Length(min = 0, max = 10)
	private String userName;

	/**
	 * 資格リスト
	 */
	private List<String> qualificationIds;
	
}
