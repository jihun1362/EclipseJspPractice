<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>Home</title>
<script type="text/javascript">

function download1(postId) {
	
	console.log(postId);
	console.log("${data.id}");
	console.log("http://localhost:8080/my/download/${data.id}");
	console.log("http://localhost:8080/my/download/"+postId);
	
	$.ajax({
        type: "GET",
        url: "http://localhost:8080/my/download/${data.id}",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        xhr: function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 2) {
                    if (xhr.status == 200) {
                        xhr.responseType = "blob";
                    } else {
                        xhr.responseType = "text";
                    }
                }
            };
            return xhr;
        },
        data: {},
        success: function (data, message, xhr) {
        	console.log(xhr.getResponseHeader('Content-Disposition'));
        	console.log("${data.img}");
        	
        	var filename = "${data.img}";
        	
        	//Convert the Byte Data to BLOB object.
            var blob = new Blob([data], { type: "application/octetstream" });
            
            //Check the Browser type and download the File.
            var isIE = false || !!document.documentMode;
            if (isIE) {
                window.navigator.msSaveBlob(blob, filename);
            } else {
                var url = window.URL || window.webkitURL;
                link = url.createObjectURL(blob);
                var a = $("<a />");
                a.attr("download", filename);
                a.attr("href", link);
                $("body").append(a);
                a[0].click();
                $("body").remove(a);
            }
        }
    })
}

</script>
</head>
<body>
	<h1>${data.title}</h1>

	<img src="${data.route}" alt="이미지 없음" />
	<br>
	<a href="http://localhost:8080/my/download/${data.id}"> ${data.img}</a>
	<c:if test="${data.img ne null}">
		<form action="<%=request.getContextPath()%>/download/${data.id}"
			method="get">
			${data.img}<br>
			<button type="submit">이미지 다운로드</button>
		</form>
		<button onclick="download1(${data.id})">AJAX 이미지 다운로드</button>
	</c:if>


	<P>내용 : ${data.content}</P>
	<div>작성자 : ${data.writer}, ${data.wdate} 게시</div>

	<a href=/my>뒤로</a>
	<br>
	<a href=/my/logout>로그아웃</a>
</body>
</html>
