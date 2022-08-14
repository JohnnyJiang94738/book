<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物車</title>

	<%-- 靜態包含 base標籤、css樣式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>


</head>
<body>
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo1.png" >
			<span class="wel_word">購物車</span>

		<%--靜態包含，登入成功之後的菜單 --%>
		<%@ include file="/pages/common/login_success_menu.jsp"%>
		<script type="text/javascript">
			$(function () {
				// 給【删除】绑定單擊事件
				$("a.deleteItem").click(function () {
					return confirm("你確定要删除【" + $(this).parent().parent().find("td:first").text() +"】嗎?")
				});
				// 給清空購物車綁定單擊事件
				$("#clearCart").click(function () {
					return confirm("你確定要清空購物車嗎?");
				})
				// 給輸入框绑定 onchange内容發生改變事件
				$(".updateCount").change(function () {
					// 獲取商品名稱
					var name = $(this).parent().parent().find("td:first").text();
					var id = $(this).attr('bookId');
					// 獲取商品數量
					var count = this.value;
					if ( confirm("你確定要將【" + name + "】的數量修改為：" + count + " 嗎?") ) {
						//發起請求，給伺服器保存修改
						location.href = "http://localhost:8080/book/cartServlet?action=updateCount&count="+count+"&id="+id;
					} else {
						// defaultValue屬性是表單項Dom物件的屬性。它表示默認的value屬性值。
						this.value = this.defaultValue;
					}
				});

			});
		</script>

	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名稱</td>
				<td>數量</td>
				<td>單價</td>
				<td>金額</td>
				<td>操作</td>
			</tr>		
			<c:if test="${empty sessionScope.cart.items}">
				<%--如果購物車為空的情况--%>
				<tr>
					<td colspan="5"><a href="index.jsp">目前購物車為空!快去瀏覽商品吧!!!</a> </td>
				</tr>
			</c:if>
			<c:if test="${not empty sessionScope.cart.items}">
				<%--如果購物車非空的情况--%>
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<tr>
						<td>${entry.value.name}</td>
						<td>
							<input class="updateCount" style="width: 80px;"
								   bookId="${entry.value.id}"
								   type="text" value="${entry.value.count}">
						</td>
						<td>${entry.value.price}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${entry.value.id}">刪除</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<%--如果購物車非空才輸出頁面的内容--%>
		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">購物車中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
				<span class="cart_span">總金額<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
				<span class="cart_span"><a id="clearCart" href="cartServlet?action=clear">清空購物車</a></span>
				<span class="cart_span"><a href="orderServlet?action=createOrder">去結帳</a></span>
			</div>
		</c:if>
	
	</div>


	<%--靜態包含頁腳内容--%>
	<%@include file="/pages/common/footer.jsp"%>


</body>
</html>