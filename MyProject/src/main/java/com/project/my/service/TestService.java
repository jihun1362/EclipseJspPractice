package com.project.my.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.my.bean.MemberDto;
import com.project.my.bean.TestBean;


public interface TestService {
	public List<TestBean> selectTest(int page, int pageSize) throws Exception;

	public TestBean detailTest(Long id) throws Exception;

	public void insertTest(MultipartHttpServletRequest request, String title, String content, MemberDto memberDto)
			throws Exception;

	public void updateTest(Long id, String title) throws Exception;

	public void deleteTest(Long id) throws Exception;

	public void downloadTest(HttpServletResponse response, Long id) throws Exception;

	public void signupTest(MemberDto memberDto) throws Exception;

	public MemberDto loginTest(String id, String password) throws Exception;
}
