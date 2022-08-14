<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>強尼書城會員註冊頁面</title>

	<%-- 靜態包含 base標籤、css樣式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>


	<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
</style>
</head>
<body>
		<div id="header">
				<img class="logo_img" alt="" src="static/img/logo1.png" >
				<span class="wel_word"></span>

			<%--靜態包含，登入成功之後的菜單 --%>
			<%@ include file="/pages/common/login_success_menu.jsp"%>


		</div>
		
		<div id="main">
		
			<h1>註冊成功!<a href="../../index.jsp">回首頁</a></h1>
	
		</div>


		<%--靜態包含頁腳内容--%>
		<%@include file="/pages/common/footer.jsp"%>


</body>
</html>