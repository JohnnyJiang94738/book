<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/5
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <span>歡迎<span class="um_span">${sessionScope.user.username}</span>光臨強尼書城</span>
    <a href="pages/order/order.jsp">我的訂單</a>
    <a href="userServlet?action=logout">登出</a>&nbsp;&nbsp;
    <a href="index.jsp">返回</a>
</div>