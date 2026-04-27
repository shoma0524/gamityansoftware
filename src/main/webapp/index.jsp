<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:choose>
	<c:when test="${not empty sessionScope.user}">
		<%
    		response.sendRedirect("scoremanager/main/menu.jsp");
		%>
	</c:when>
	<c:otherwise>
		<%
    		response.sendRedirect("scoremanager/Login.action");
		%>
	</c:otherwise>
</c:choose>
