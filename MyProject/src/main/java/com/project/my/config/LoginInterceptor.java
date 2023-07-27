package com.project.my.config;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	public static final String LOG_ID = "logId";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// true�� ���
		//  	- ������ ������ ���� ������ ��ȯ�Ѵ�.
		//  	- ������ ������ ���ο� ������ ������ ��ȯ�Ѵ�.
		// false�� ���
		//  	- ������ ������ ���� ������ ��ȯ�Ѵ�.
		//  	- ������ ������ ���ο� ������ �������� �ʰ� null�� ��ȯ�Ѵ�.
		// �߰������� �μ��� �������� ���� ��� �⺻ ������ true�̴�. 
		HttpSession session = request.getSession();
//		HttpSession session = request.getSession(false);

		
		if (session == null || session.getAttribute("loginMember") == null) {
			System.out.println("������ ����� ��û");
			System.out.println("currentAccessedTime=" + new Date());
			response.sendRedirect(request.getContextPath() + "/loginview");
			return false;
		}

//		HttpSession session = request.getSession();
		System.out.println("sessionId=" + session.getId());
		System.out.println("getMaxInactiveInterval=" + session.getMaxInactiveInterval());
		System.out.println("creationTime=" + new Date(session.getCreationTime()));
		System.out.println("lastAccessedTime=" + new Date(session.getLastAccessedTime()));
		System.out.println("currentAccessedTime=" + new Date());
		System.out.println("isNew=" + session.isNew());
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
