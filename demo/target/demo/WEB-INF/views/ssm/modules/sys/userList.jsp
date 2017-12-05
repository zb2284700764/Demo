<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html lang="en">
<head>
	<title>用户列表</title>
	<meta name="decorator" content="default"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
	<div class="container-fluid">

		<h4>用户列表</h4>
	
		<table class="table table-bordered">
			<tr>
				<td>ID</td>
				<td>登录名</td>
				<td>密码</td>
				<td>姓名</td>
				<td>是否禁止登录</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${userList }" var="user" varStatus="index">
				<tr>
					<td>${user.id }</td>
					<td>${user.loginName }</td>
					<td>${user.password }</td>
					<td>${user.name }</td>
					<td>${user.loginFlag eq 0 ? "否" : "是" }</td>
					<td>
						<button type="button" class="btn btn-primary btn-xs">修改</button>
						<button type="button" class="btn btn-danger btn-xs">删除</button>
						
						<c:choose>
							<c:when test="${user.loginFlag eq 0 }">
								<button type="button" class="btn btn-link btn-xs">禁用</button>
							</c:when>
							<c:when test="${user.loginFlag eq 1 }">
								<button type="button" class="btn btn-link btn-xs">启用</button>
							</c:when>
						</c:choose>
						
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<div>
			<form action="${ctx }/sys/user/gotoUserForm" method="post">
				<button id="addUserBtn" type="submit" class="btn btn-default">增加用户</button>
			</form>
		</div>
		
		<div>
			<button type="button" class="btn btn-default">Default</button>
			<button type="button" class="btn btn-primary btn-xs">Primary</button>
			<button type="button" class="btn btn-success btn-sm">Success</button>
			<button type="button" class="btn btn-info btn-lg">Info</button>
			<button type="button" class="btn btn-warning">Warning</button>
			<button type="button" class="btn btn-danger">Danger</button>
			<button type="button" class="btn btn-link">Link</button>
			<button type="button" class="btn btn-primary" disabled="disabled">Primary button</button>
			<button type="button" class="btn btn-default" disabled="disabled">Button</button>
		</div>
	</div>
	
</body>
<script type="text/javascript">
	$(function(){
		

		
	});
</script>
</html>