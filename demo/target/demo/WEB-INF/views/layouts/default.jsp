<%-- <%@ page contentType="text/html;charset=UTF-8"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title/></title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<sitemesh:head/>
</head>
<body>
	<h5>登录认证成功，<span style="color: red;">用户名-->${user.name}</span>，<a href="${ctx }/loginOut">退出登录</a></h5>

	<sitemesh:body/>
</body>
</html>