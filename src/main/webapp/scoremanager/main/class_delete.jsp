<%-- クラス情報の削除確認JSP --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム クラス情報削除</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報削除</h2>

		<div class="container mt-4" style="max-width: 500px;">
			<div class="alert alert-danger">以下のクラスを削除します。よろしいですか？</div>

			<table class="table table-bordered">
				<tr>
					<th class="bg-light w-25">クラス番号</th>
					<td>${classNum.class_num}</td>
				</tr>
				<%-- 今後クラス名を実装する場合、以下の処理を追加
				<tr>
					<th class="bg-light">クラス名</th>
					<td>${classNum.name}</td>
				</tr>
				--%>
			</table>
			<div class="d-flex gap-2 mt-4">
				<a href="ClassDeleteExecute.action?class_num=${classNum.class_num}" class="btn btn-danger w-100">削除する</a>
				<a href="ClassList.action" class="btn btn-secondary w-100">キャンセル</a>
			</div>
		</div>
	</c:param>
</c:import>
