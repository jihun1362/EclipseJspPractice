package com.project.my.dao;

import java.util.List;
import java.util.Map;

import com.project.my.bean.MemberDto;
import com.project.my.bean.TestBean;

public interface TestDao {
	public int count() throws Exception;
	
	public List<TestBean> selectTest(Map<String, Integer> param) throws Exception;

	public TestBean detailTest(Long id) throws Exception;

	public void insertTest(Map<String, String> param) throws Exception;

	public void deleteTest(Long id) throws Exception;

	public void updateTest(Map<String, Object> param) throws Exception;

	public void signupTest(Map<String, Object> param) throws Exception;
	
	public MemberDto loginTest(Map<String, Object> param) throws Exception;

	public MemberDto checkTest(String email) throws Exception;
}
