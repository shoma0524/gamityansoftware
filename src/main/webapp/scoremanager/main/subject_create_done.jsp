<!-- 科目登録完了JSP -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
        得点管理システム
    </c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
		<div class="alert alert-success text-center">登録が完了しました</div>
		<br>

		<div class="mt-3">
			<a href="SubjectCreate.action">戻る</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="SubjectList.action">科目一覧</a>
		</div>
	</c:param>
</c:import>