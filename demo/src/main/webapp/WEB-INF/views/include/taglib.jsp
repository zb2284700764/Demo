<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}/a"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<fmt:setLocale value="${sessionScope[nestframeworkLocale]}"/>
<fmt:setBundle basename="resource/messages"/>
