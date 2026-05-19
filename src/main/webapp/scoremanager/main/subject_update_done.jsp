<%-- 科目情報の変更完了JSP --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
        得点管理システム 科目情報変更
    </c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
		<div class="alert alert-success text-center">変更が完了しました</div>
		<br>

		<div class="mt-3">
			<a href="SubjectList.action">科目一覧</a>
		</div>
	</c:param>
</c:import>
