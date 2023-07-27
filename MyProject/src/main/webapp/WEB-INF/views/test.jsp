<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>

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
		/* console.log(${page});
		console.log(pageCount);
		console.log(pageLimit); */

		if(pageCount <= pageLimit){
			$.ajax({
		        //type: "POST",
		        type: "GET",
		        //url: "http://localhost:8080/my/infinitScroll?page="+pageCount,
		        url: "http://192.168.0.47:8080/my/infinitScroll",
		        data: {"page":pageCount},
		        success: function (response) {
	                /* console.log(response);
	                console.log(response[0]);
	                console.log(response[0]["title"]); */

		        	for (let i = 0; i < response.length; i++){
		        		var dataId = response[i]["id"];
		        		var dataTitle = response[i]["title"];
		        		var dataWriter = response[i]["writer"];
		        		var dataWdate = response[i]["wdate"];

		        		var temp_html = "";
		        		
		        		temp_html +=		'<tr id="tableColor" style="height: 10px;">';
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