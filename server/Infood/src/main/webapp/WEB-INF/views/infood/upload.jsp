<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>

	function test(f){
		var subway = f.subway.value;
		var food = f.food.value;
		
		alert(food);
		
		f.action = "upload.do";
		f.submit();
	}

</script>
</head>
<body>

	<form method="post" enctype="multipart/form-data">
		<input type="file" name="photo">
		<input name="subway" placeholder="지하철역 입력">
		<input name="food" placeholder="음식이름 입력">
		<input name="content" placeholder="내용입력">
		<input type="hidden" name="user_idx" value="0">
		<input type="hidden" name="user_nikname" value="test">
		<button type="button" onclick="test(this.form)"></button>
	</form>

</body>
</html>