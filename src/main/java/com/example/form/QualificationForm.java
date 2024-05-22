
package com.example.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class QualificationForm {

	/**
	 * 資格ID
	 */
	@Length(min = 0, max = 6)
	private String qualificationId;
	
	/**
	 * 資格名
	 */
	private String qualificationName;
	
	/**
	 * 資格リスト
	 */
	//private List<> qualificationList;
	
}
