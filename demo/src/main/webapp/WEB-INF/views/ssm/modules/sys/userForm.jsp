<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增</title>
	<meta name="decorator" content="default"/>
	
<script type="text/javascript">
	$(function(){
		$("#saveBtn").click(function(){
			$("#saveForm").submit();
		});
	});
</script>

</head>
<body>

	<form action="${ctx }/sys/user/save" method="post" id="saveForm">
		<input type="hidden" name="token" value="${token }" />
	
		<button id="saveBtn" type="button" class="btn btn-default">保存</button>
	</form>
</body>
</html>