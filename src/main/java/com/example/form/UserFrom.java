
package com.example.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserFrom {

	/**
	 * ユーザーID
	 */
	@Length(min = 0, max = 6)
	private String userId;
	
	/**
	 * ユーザー名
	 */
	private String userName;
	
	/**
	 * 資格リスト
	 */
	//private List<> qualificationList;
	
}
