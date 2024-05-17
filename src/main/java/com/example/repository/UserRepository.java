package com.example.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> findByUserId(String userId){
		String query ="SELECT * FROM MST_USER WHERE USER_ID = ?";
		
		// 検索実行 
		return jdbcTemplate.queryForMap(query, userId);
	}
}
