<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>Index Page</title>

<style type="text/css">
#tableColor td:hover {
	color: blue;
}

.big-box {
	width: 100%;
	background-color: gray;
	height: 100vh;
	border-top: 1px solid black;
}
</style>

<script type="text/javascript">
	window.onload = function () {
		tempPage(1);
	}
	
	var pageCount=1;
	var pageLimit=${page};
	
	window.onscroll = function(ev) {
	    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
	    	if(pageCount <= pageLimit){
	    		tempPage();
	    	}
	    }
	};
	
	function tempPage() {
		//alert("you're at the bottom of the page");
		console.log(${page});
		console.log(pageCount);
		console.log(pageLimit);

		if(pageCount <= pageLimit){
			$.ajax({
		        //type: "POST",
		        type: "GET",
		        //url: "http://localhost:8080/my/infinitScroll?page="+pageCount,
		        url: "http://localhost:8080/my/infinitScroll",
		        data: {"page":pageCount},
		        success: function (response) {
	                console.log(response);
	                console.log(response[0]);
	                console.log(response[0]["title"]);

		        	for (let i = 0; i < response.length; i++){
		        		var dataId = response[i]["id"];
		        		var dataTitle = response[i]["title"];
		        		var dataWriter = response[i]["writer"];
		        		var dataWdate = response[i]["wdate"];

		        		var temp_html = "";
		        		
		        		temp_html +=		'<tr id="tableColor" style="height: 50px;">';
		        		temp_html +=			'<td><a href="detail?postId='+dataId+'" >'+dataId+'</a></td>';
		        		temp_html +=			'<td><a href="detail?postId='+dataId+'" >'+dataTitle+'</button></td>';
		        		temp_html +=			'<td>'+dataWriter+'</td>';
		        		temp_html +=			'<td>'+dataWdate+'</td>';
		        		temp_html +=		'</tr>';
		        		
		        		// 아이디 가져오기
	        			var appendPageElement = document.getElementById('appendPage');

		        		// 아이디에 붙히기
	        		    if (appendPageElement) {
	        		    	appendPageElement.innerHTML += temp_html;
	        		    }
		        	}
		        }
		    })
		}
	    pageCount += 1;
	}
</script>
</head>
<body>
	
	<a href="/my/calculator">계산기 페이지</a>
	<a href="/my/test">테스트 페이지</a>
	
	<div>
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
	</div>

	<div>
		<h1>게시글 입력</h1>
		<form action="<%=request.getContextPath()%>/insert"
			enctype="multipart/form-data" method="post">
			제목: <input type="text" name="title"><br /> 내용: <br />
			<textarea name="content" style="width: 212px; height: 60px"></textarea>
			<br /> <br /> 사진: <input type="file" name="file" />
			<button type="submit">저장</button>
		</form>
	</div>

	<%-- <h1>게시글 수정</h1>
	<form action="<%=request.getContextPath()%>/update" method="post">
		게시글 번호: <input type="number" name="postId"><br /> <br />
		제목: <input type="text" name="title"><br /> <br />
		<button type="submit">수정</button>
	</form> --%>

	<div>
		<h1>게시글 삭제</h1>
		<form action="<%=request.getContextPath()%>/del" method="post">
			삭제할 게시글 번호: <input type="number" name="postId"><br /> <br />
			<button type="submit">삭제</button>
		</form>
		<a href=/my/logout>로그아웃</a>
	</div>

	<!-- <button onclick="tempPage()">페이지 추가</button> -->

	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody id="appendPage">

		</tbody>
	</table>
</body>
</html>