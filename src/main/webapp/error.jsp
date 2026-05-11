<!-- エラーページJSP -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">	
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
			<div class="container mt-5">
				<c:choose>

				<c:when test="${error == 'permission'}">
					<p>あなたの権限ではアクセスできません。</p>
					<br><br><br>
					<a href="menu.jsp">メニューへ</a>
				</c:when>

				<c:otherwise>
					<p>エラーが発生しました。</p>
				</c:otherwise>

				</c:choose>
			</div>
	</c:param>
</c:import>
