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
		
		// true일 경우
		//  	- 세션이 있으면 기존 세션을 반환한다.
		//  	- 세션이 없으면 새로운 세션을 생성해 반환한다.
		// false일 경우
		//  	- 세션이 있으면 기존 세션을 반환한다.
		//  	- 세션이 없으면 새로운 세션을 생성하지 않고 null을 반환한다.
		// 추가적으로 인수를 전달하지 않을 경우 기본 값으로 true이다. 
		HttpSession session = request.getSession();
//		HttpSession session = request.getSession(false);

		
		if (session == null || session.getAttribute("loginMember") == null) {
			System.out.println("미인증 사용자 요청");
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
