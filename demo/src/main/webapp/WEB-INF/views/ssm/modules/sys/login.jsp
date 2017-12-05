<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<body>
<body>
	<h1>登录</h1>
	<form action="${ctx }/login" method="post">
		登录名:<input type="text" name="username" value="${loginName }"><br/>
		密&nbsp;码:<input type="text" name="password" value="${password }"><br/>
		<%-- password:<input type="password" name="password" value="${password }"><br/> --%>
		<input type="submit" value="submit"><br/>
		${msg }
	</form>

</body>
</body>
</html>
