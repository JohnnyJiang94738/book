<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>強尼書城註冊頁面</title>

		<%-- 靜態包含 base標籤、css樣式、jQuery文件 --%>
		<%@ include file="/pages/common/head.jsp"%>

		<script type="text/javascript">
			// 頁面加載完成之後
			$(function () {
				$("#username").blur(function () {
					//1 獲取用戶名
					var username = this.value;
					$.getJSON("http://localhost:8080/book/userServlet","action=ajaxExistsUsername&username=" + username,function (data) {
						if (data.existsUsername) {
							$("span.errorMsg").text("帳號名稱已存在!");
						} else {
							$("span.errorMsg").text("帳號名稱可使用!");
						}
					});
				});

				// 給驗證碼的圖片，綁定單擊事件
				$("#code_img").click(function () {
					// 在事件響應的function函數中有一個this物件。這個this物件，是目前正在響應事件的dom物件
					// src屬性表示驗證碼img標籤的圖片路徑。它可讀也可寫
					// alert(this.src);
					this.src = "${basePath}kaptcha.jpg?d=" + new Date();
				});

				// 給註冊綁定單擊事件
				$("#sub_btn").click(function () {
					// 驗證用戶名：必須由字母、數字及底線组成，並且長度為5到12
					//1 取得用戶名輸入框裡的内容
					var usernameText = $("#username").val();
					//2 創建正則表達式物件
					var usernamePatt = /^\w{5,12}$/;
					//3 使用test方法驗證
					if (!usernamePatt.test(usernameText)) {
						//4 提示用戶結果
						$("span.errorMsg").text("帳號名稱不符合規則!");
						return false;
					}
					// 驗證密碼：必須由字母、數字及底線组成，並且長度為5到12
					//1 取得用戶名輸入框裡的内容
					var passwordText = $("#password").val();
					//2 創建正則表達式物件
					var passwordPatt = /^\w{5,12}$/;
					//3 使用test方法驗證
					if (!passwordPatt.test(passwordText)) {
						//4 提示用戶結果
						$("span.errorMsg").text("密碼不符合規則!");
						return false;
					}

					// 驗證確認密碼：和密碼相同
					//1 取得確認密碼内容
					var repwdText = $("#repwd").val();
					//2 和密碼相比较
					if (repwdText != passwordText) {
						//3 提示用戶
						$("span.errorMsg").text("確認密碼和密碼不一致!");
						return false;
					}

					// 信箱驗證：xxxxx@xxx.com
					//1 取得信箱裡的内容
					var emailText = $("#email").val();
					//2 創建正則表達式物件
					var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					//3 使用test方法驗證是否合法
					if (!emailPatt.test(emailText)) {
						//4 提示用戶
						$("span.errorMsg").text("e-mail格式不符合規則!");
						return false;
					}

					// 驗證碼
					var codeText = $("#code").val();
					//去掉驗證碼前後空格
					codeText = $.trim(codeText);
					if (codeText == null || codeText == "") {
						//4 提示用戶
						$("span.errorMsg").text("請輸入驗證碼!");
						return false;
					}

					// 去掉錯誤訊息
					$("span.errorMsg").text("");

				});
			});

		</script>
	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>
	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">歡迎註冊</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>註冊強尼書城會員</h1>
								<span class="errorMsg">
									${ requestScope.msg }
								</span>
							</div>
							<div class="form">
								<form action="userServlet" method="post">
									<input type="hidden" name="action" value="regist">
									<label>帳號名稱:</label>
									<input class="itxt" type="text" placeholder="請輸入帳號名稱"
										   value="${requestScope.username}"
										   autocomplete="off" tabindex="1" name="username" id="username" />
									<br />
									<br />
									<label>密碼:</label>
									<input class="itxt" type="password" placeholder="請輸入密碼"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>確認密碼:</label>
									<input class="itxt" type="password" placeholder="確認密碼"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>e-mail:</label>
									<input class="itxt" type="text" placeholder="請輸入e-mail"
										   value="${requestScope.email}"
										   autocomplete="off" tabindex="1" name="email" id="email" />
									<br />
									<br />
									<label>驗證碼</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code" />
									<img id="code_img" alt="" src="kaptcha.jpg" style="float: right; margin-right: 40px; width: 110px; height: 30px;">
									<br />
									<br />
									<input type="submit" value="註冊" id="sub_btn" />
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>

		<%--靜態包含頁腳内容--%>
		<%@include file="/pages/common/footer.jsp"%>


	</body>
</html>