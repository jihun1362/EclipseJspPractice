<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script type="text/javascript">

function show() {
	$('#signupid').show();
}

function hide() {
	$('#signupid').hide();
}

</script>
</head>

<body>

	<h1>로그인</h1>
	<form action="<%=request.getContextPath()%>/login" method="post">
		<table border="1" width="400px">
			<tr>
				<td>아이디</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="button" onclick="show()">회원가입</button>
					<button type="submit">로그인</button>
			</tr>
		</table>
	</form>

	<div style="display: none;" id="signupid">
		<h3>회원가입</h3>
		<form action="<%=request.getContextPath()%>/signup" method="post">
			<table border="1" width="400px">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="button" onclick="hide()">닫기</button>
						<button type="submit">회원가입</button>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>