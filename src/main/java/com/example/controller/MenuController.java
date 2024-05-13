package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.util.UtilConst;

@Controller
public class MenuController {

	@GetMapping(UtilConst.MAPPING_PATH_MENU)
	public String getMenu() {
		// menu.htmlに画面遷移
		return UtilConst.RESPONSE_PATH_MENU;
	}
}
