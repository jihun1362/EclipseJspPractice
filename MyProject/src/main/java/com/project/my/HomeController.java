package com.project.my;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.my.bean.TestBean;
import com.project.my.dao.TestDao;
import com.project.my.bean.MemberDto;
import com.project.my.service.TestService;

@Controller
public class HomeController {

	@Inject
	private TestService service;

	@Inject
	private TestDao dao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String select(@RequestParam(value = "page", defaultValue = "1") int page, Locale locale, Model model)
			throws Exception {

//		System.out.println(loginMember);
//
//		MemberDto loginMember = service.check(memberId);
//		if (loginMember == null) {
//			response.sendRedirect(request.getContextPath() + "/loginview");
//		}
		int pageSize = 10;
		List<TestBean> list = service.selectTest(pageSize * page - pageSize, pageSize);

		int totalRow = dao.count();
		int totalPage = totalRow / pageSize;
		if (totalRow % pageSize != 0) {
			totalPage++;
		}

		model.addAttribute("list", list);
		model.addAttribute("page", totalPage);

		return "index";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, HttpServletResponse response, Locale locale, Model model)
			throws Exception {

//		System.out.println(loginMember);
//
//		MemberDto loginMember = service.check(memberId);
//		if (loginMember == null) {
//			response.sendRedirect(request.getContextPath() + "/loginview");
//		}

		model.addAttribute("data", service.detailTest(Long.parseLong(request.getParameter("postId"))));

		return "detail";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam("title") String title, @RequestParam("content") String content) throws Exception {

		HttpSession session = request.getSession();
		MemberDto a = (MemberDto) session.getAttribute("loginMember");
		System.out.println(a.getEmail());
		System.out.println(a.getName());

		System.out.println("saveTest");
		System.out.println("저장 테스트");
		service.insertTest(request, title, content, (MemberDto) session.getAttribute("loginMember"));

		response.sendRedirect(request.getContextPath() + "/");
	}

	@RequestMapping(value = "/download/{postId}", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Long postId)
			throws Exception {

		service.downloadTest(response, postId);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		service.updateTest(Long.parseLong(request.getParameter("postId")), request.getParameter("title"));

		response.sendRedirect(request.getContextPath() + "/");
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		service.deleteTest(Long.parseLong(request.getParameter("postId")));

		response.sendRedirect(request.getContextPath() + "/");
	}

	@RequestMapping(value = "/loginview", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response,
			@SessionAttribute(name = "loginMember", required = false) MemberDto loginMember, Locale locale, Model model)
			throws Exception {

		System.out.println(loginMember);

		if (loginMember != null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		return "loginview";
	}

	@PostMapping("/signup")
	public void signup(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MemberDto memberDto = new MemberDto(0, request.getParameter("email"), request.getParameter("password"),
				request.getParameter("name"));

		service.signupTest(memberDto);

		response.sendRedirect(request.getContextPath() + "/loginview");
	}

	@PostMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MemberDto loginMember = service.loginTest(request.getParameter("id"), request.getParameter("password"));

		// 세션 매니저를 통해 세션 생성및 회원정보 보관
		// true일 경우
		// - 세션이 있으면 기존 세션을 반환한다.
		// - 세션이 없으면 새로운 세션을 생성해 반환한다.
		// false일 경우
		// - 세션이 있으면 기존 세션을 반환한다.
		// - 세션이 없으면 새로운 세션을 생성하지 않고 null을 반환한다.
		// 추가적으로 인수를 전달하지 않을 경우 기본 값으로 true이다.
//		HttpSession session = request.getSession(false);
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", loginMember);

		// 세션 유지시간(단위: 초) ※테스트 해보니 사용자가 아무런 행동을 하지 않아야 유효시간이 적용됨
		// 테스트 해보니 밑에 web.xml에 작성하는 것보다 우선권을 가짐
		session.setMaxInactiveInterval(1800);

		// web.xml에 사용하는 방법
		// <!-- 세션 유지시간 지정(단위: 분) -->
		// <session-config>
		// <session-timeout>1</session-timeout>
		// </session-config>

		response.sendRedirect(request.getContextPath() + "/");
	}

	@GetMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath() + "/loginview");
	}

//	@PostMapping("/infinitScroll")
	@GetMapping("/infinitScroll")
	@ResponseBody
	public List<TestBean> infinitScroll(@RequestParam(value = "page") int page) throws Exception {

		int pageSize = 10;
		List<TestBean> list = service.selectTest(pageSize * page - pageSize, pageSize);
		System.out.println(page);

		return list;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String login(Model model) throws Exception {

		int pageSize = 10;
		int totalRow = dao.count();
		int totalPage = totalRow / pageSize;
		if (totalRow % pageSize != 0) {
			totalPage++;
		}

		model.addAttribute("page", totalPage);

		return "test";
	}

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	public String calculator(Model model) throws Exception {

		return "calculator";
	}

//	@PostMapping("/calculation")
//	@ResponseBody
//	public int calculation(@RequestBody RequestCal requestCal) throws Exception {
//
//		int result = 0;
//		int value = Integer.parseInt(requestCal.getValue());
//		int cal = Integer.parseInt(requestCal.getCal());
//		
//		System.out.println(value);
//		System.out.println(requestCal.getOperator());
//		System.out.println(cal);
//		
//		switch (requestCal.getOperator()) {
//		case "plus":
//			result = value + cal;
//			break;
//		case "minus":
//			result = value - cal;
//			break;
//		case "multiplication":
//			result = value * cal;
//			break;
//		case "divide":
//			result = value / cal;
//			break;
//		default:
//			break;
//		}
//
//		return result;
//	}
}
