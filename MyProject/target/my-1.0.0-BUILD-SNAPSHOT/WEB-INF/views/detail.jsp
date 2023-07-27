<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home</title>
</head>
<body>
	<h1>${data.title}</h1>

	<img src="${data.route}" alt="이미지 없음" />
	<br>
	<a href="http://localhost:8080/my/download/${data.id}"> ${data.img}</a>
	<form action="<%=request.getContextPath()%>/download/${data.id}"
		method="get">
		${data.img}<br>
		<button type="submit">이미지 다운로드</button>
	</form>


	<P>내용 : ${data.content}</P>
	<div>작성자 : ${data.writer}, ${data.wdate} 게시</div>

	<a href=/my>뒤로</a><br>
	<a href=/my/logout>로그아웃</a>
</body>
</html>
