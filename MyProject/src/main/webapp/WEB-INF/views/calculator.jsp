<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
button {
	height: 30px;
	width: 30px;
}
#result{
	font-size: 2em;
}

</style>
<script type="text/javascript">
	/* value */
	var calView = "";
	var input = "0";
	var operator = "";
	var cal = "";
	var result = "0";

	//사칙 연산자를 썼는가
	var calculation = false;
	//"="을 썼는가
	var resultCheck = false;
	//숫자를 눌렀는가
	var numCheck = false;

	/* number */
	function num(number) {
		// 계산 뷰에 "="가 있을때 번호 누르면 뷰 제거
		if (calView.indexOf("=") !== -1) {
			calView = "";
		}

		//숫자 누르고 결과 누르고 다시 돌아오면 값 초기화
		if (resultCheck === true) {
			input = "";
			result = "";
			$('#cal').empty();
		}

		// 시작이 "0" 상태일때 번호 입력시 "0" 제거 후 적용 
		if (input === "0") {
			input = "";
		}
		if (cal === "0") {
			cal = "";
		}
		if (result === "0") {
			result = "";
		}

		// 연산자 사용했는지 체크하여 정해진 데이터에 값넣기
		if (calculation === true) {
			if (cal === "") {
				input = "";
			}
			cal += number;
			input += number;
			calView += number;
		} else {
			result += number;
			input += number;
			calView += number;
		}

		resultAppend();
		resultCheck = false;
		numCheck = true;
	}

	/* calculation */
	// "=" 연산
	function result1() {
		calView = "";
		calView += result;

		//숫자 > 연산 > "=" 눌렀을 경우
		if (calculation === true) {
			if (cal === "") {
				cal = result;
			}
		}
		//계산
		operatorSwitch(operator)

		calView += cal;
		calView += "=";

		input = result;

		append();
		resultAppend();
		calculation = false;
		resultCheck = true;
		numCheck = false;
	}
	// 사칙연산자 적용
	function calculation4(calculation_value) {

		// =결과 안찍고 다시 사칙 연산자로 오면 실행 계산
		if (calculation === true && numCheck === true) {
			//계산
			operatorSwitch(operator)
			input = result;
			resultAppend();
		}
		
		calView = "";
		calView += result;

		cal = "";
		if (resultCheck === true) {
			calView = result;
		}

		switch (calculation_value) {
		case "plus":
			calView += "+";
			break;
		case "minus":
			calView += "-";
			break;
		case "multiplication":
			calView += "x";
			break;
		case "divide":
			calView += "÷";
			break;
		}

		append();

		operator = calculation_value;

		calculation = true;
		resultCheck = false;
		numCheck = false;
	}

	/* cancel */
	// 입력시 개별 취소
	function cancel() {
		//계산값 입력시
		if (calculation === true) {
			if (numCheck === true) {
				cal = cal.slice(0, -1);
				if (cal.length === 0) {
					cal = "0";
				}
				input = input.slice(0, -1);
				if (input.length === 0) {
					input = "0";
				}
			}
		//처음 입력값 입력시
		} else {
			if (resultCheck === true) {
				calView = "";
				$('#cal').empty();
			} else {
				result = result.slice(0, -1);
				if (result.length === 0) {
					result = "0";
				}
				input = input.slice(0, -1);
				if (input.length === 0) {
					input = "0";
				}
			}
		}
		calView = calView.slice(0, -1);
		if (calView.length === 0) {
			calView = "0";
		}
		resultAppend();
	}
	// 전체 초기화
	function allCancel() {
		calView = "";
		input = "0";
		operator = "";
		cal = "";
		result = "0";
		calculation = false;
		resultCheck = false;
		append()
		resultAppend();
	}

	/* method */
	// 새로고침이나 시작시 0으로 설정
	window.onload = function() {
		input = "0";
		resultAppend();
	}
	// 연산된 data 보이기
	function append() {
		$('#cal').empty();

		var str = calView.toString()
		  .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
		
		var appendCal = '<span>' + str + '</span>';

		// 아이디 가져오기
		var appendPageElement = document.getElementById('cal');

		// 아이디에 붙히기
		if (appendPageElement) {
			appendPageElement.innerHTML += appendCal;
		}
	}
	// 결과 data 보이기
	function resultAppend() {
		$('#result').empty();

		// 숫자 3개마다 "," 적용하기 ex)1,000,000
		//var str = input.toLocaleString('ko-KR');
		// 정규식 방법
		var str = input.toString()
		  .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

		var appendCal = '<span>' + str + '</span>';

		// 아이디 가져오기
		var appendPageElement = document.getElementById('result');

		// 아이디에 붙히기
		if (appendPageElement) {
			appendPageElement.innerHTML += appendCal;
		}
	}
	// 사칙연산 계산 적용
	function operatorSwitch(oper) {
		switch (oper) {
		case "":
			break;
		case "plus":
			result = Number(result) + Number(cal);
			calView += "+";
			break;
		case "minus":
			result = Number(result) - Number(cal);
			calView += "-";
			break;
		case "multiplication":
			result = Number(result) * Number(cal);
			calView += "x";
			break;
		case "divide":
			result = Number(result) / Number(cal);
			calView += "÷";
			break;
		default:
			break;
		}
	}
</script>
</head>
<body>
	<h1>계산기</h1>
	<div>
		<div id="cal"></div>
		<div id="result"></div>
	</div>
	<hr>

	<button onclick="percent()">%</button>
	<button onclick="allCancel()">C</button>
	<button onclick="cancel()">←</button>
	<button onclick="calculation4('divide')">÷</button>
	<br>
	<button onclick="num(7)">7</button>
	<button onclick="num(8)">8</button>
	<button onclick="num(9)">9</button>
	<button onclick="calculation4('multiplication')">x</button>
	<br>
	<button onclick="num(4)">4</button>
	<button onclick="num(5)">5</button>
	<button onclick="num(6)">6</button>
	<button onclick="calculation4('minus')">-</button>
	<br>
	<button onclick="num(1)">1</button>
	<button onclick="num(2)">2</button>
	<button onclick="num(3)">3</button>
	<button onclick="calculation4('plus')">+</button>
	<br>
	<button onclick="">※</button>
	<button onclick="num(0)">0</button>
	<button onclick="point()">.</button>
	<button onclick="result1()">=</button>
	
	<p>"%", ".", "※"는 현재 장식</p>
</body>
</html>