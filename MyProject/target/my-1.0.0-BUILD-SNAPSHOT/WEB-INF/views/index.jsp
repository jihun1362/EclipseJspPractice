<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index Page</title>

<style type="text/css">
#tableColor td:hover {
	color: blue;
}
</style>

</head>
<body>

	<h1>게시글 목록</h1>
	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="list">
				<form action="<%=request.getContextPath()%>/detail" method="get">
					<input type="number" name="postId" value="${list.id}"
						style="display: none;">
					<tr id="tableColor">
						<td><button type="submit" style="all: unset;">${list.id}</button></td>
						<td><button type="submit" style="all: unset;">${list.title}</button></td>
						<td>${list.writer}</td>
						<td>${list.wdate}</td>
					</tr>
				</form>
			</c:forEach>

			<c:forEach var="i" begin="1" end="${page}">
				<a href="/my/?page=${i}"> ${i} </a>
			</c:forEach>
		</tbody>
	</table>

	<h1>게시글 입력</h1>
	<form action="<%=request.getContextPath()%>/insert"
		enctype="multipart/form-data" method="post">
		제목: <input type="text" name="title"><br /> <br /> 내용: <input
			type="text" name="content"><br /> 작성자: <input type="text"
			name="writer"><br /> 사진: <input type="file" name="file" />
		<button type="submit">저장</button>
	</form>

	<%-- <h1>게시글 수정</h1>
	<form action="<%=request.getContextPath()%>/update" method="post">
		게시글 번호: <input type="number" name="postId"><br /> <br />
		제목: <input type="text" name="title"><br /> <br />
		<button type="submit">수정</button>
	</form> --%>

	<h1>게시글 삭제</h1>
	<form action="<%=request.getContextPath()%>/del" method="post">
		삭제할 게시글 번호: <input type="number" name="postId"><br /> <br />
		<button type="submit">삭제</button>
	</form>

	<a href=/my/logout>로그아웃</a>
</body>
</html>