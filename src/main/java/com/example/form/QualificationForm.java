
package com.example.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QualificationForm {

	/**
	 * 編集モード
	 * 登録(0)：変更(1)
	 */
	private int editMode;
	
	/**
	 * No
	 */
	private String no;

	/**
	 * 資格ID
	 */
	@Length(min = 0, max = 6)
	@Pattern(regexp="^[0-9a-zA-Z]{0,6}+$")
	private String qualificationId;
	
	/**
	 * 資格名
	 */
	@Length(min = 0, max = 20)
	private String qualificationName;
	
	/**
	 * 変更前資格ID
	 */
	private String qualificationIdBefore;

	/**
	 * 変更前資格名
	 */
	private String qualificationNameBefore;
	
}
