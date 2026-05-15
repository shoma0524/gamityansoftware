<!-- 教員情報登録JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts">
		function togglePassword() {
			let pass1 = document.getElementById("password1");
			if (pass1.type === "password") {
				pass1.type = "text";
			} else {
				pass1.type = "password";
			}
			let pass2 = document.getElementById("password2");
			if (pass2.type === "password") {
				pass2.type = "text";
			} else {
				pass2.type = "password";
			}
		}
	</c:param>

	<c:param name="content">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教員情報登録</h2>
		<form action="TeacherCreateExecute.action" method="post" class="container mt-4" style="max-width: 500px;">

			<!-- 教員ID -->
			<div class="mb-3">
				<label class="form-label fw-bold">教員ID</label>
				<input type="text" name="id" class="form-control" value="<c:out value="${id}"></c:out>" placeholder="教員IDを入力してください" maxlength="10" required>
				<div class="text-danger small">${errors.id}</div>
			</div>

			<!-- 氏名 -->
			<div class="mb-3">
				<label class="form-label fw-bold">氏名</label>
				<input type="text" name="name" class="form-control" value="<c:out value="${name}"></c:out>" placeholder="氏名を入力してください" maxlength="10" required>
				<div class="text-danger small">${errors.name}</div>
			</div>

			<!-- パスワード -->
			<div class="mb-3">
				<label class="form-label fw-bold">パスワード</label>
				<input type="password" name="password1" id="password1" class="form-control" value="<c:out value="${password1}"></c:out>" placeholder="8文字以上30文字以内の半角英数字で入力してください" maxlength="30" required>
				<div class="text-danger small">${errors.password_1}</div>
				<div class="text-danger small">${errors.password_2}</div>
			</div>

			<!-- パスワード(確認用) -->
			<div class="mb-3">
				<label class="form-label fw-bold">パスワード(確認用)</label>
				<input type="password" name="password2" id="password2" class="form-control" value="<c:out value="${password2}"></c:out>" placeholder="パスワードを入力してください(確認用)" maxlength="30" required>
				<div class="text-danger small">${errors.password_2}</div>
			</div>

			<!-- 表示チェック -->
			<div class="form-check mb-3">
				<input type="checkbox" class="form-check-input" onclick="togglePassword()">
				<label class="form-check-label">パスワードを表示</label>
			</div>

			<!-- ボタン -->
			<div class="d-flex gap-2 mt-4">
				<button type="submit" class="btn btn-primary w-100">登録して終了</button>
				<a href="TeacherList.action" class="btn btn-secondary w-100">戻る</a>
			</div>

		</form>
	</c:param>
</c:import>
