<!-- ログインJSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">	
	<c:param name="title">
		得点管理システム ログイン
	</c:param>
	<c:param name="scripts">
		function togglePassword() {
			let pass = document.getElementById("password");
			if (pass.type === "password") {
				pass.type = "text";
			} else {
				pass.type = "password";
			}
		}
	</c:param>
	<c:param name="content">
		<div class="container mt-5">
    <div class="card mx-auto" style="width: 400px;">
        <div class="card-header text-center">
            ログイン
        </div>

        <div class="card-body">

            <!-- エラー表示 -->
            <c:if test="${error != null}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>

            <form action="LoginExecute.action" method="post">

                <!-- ID -->
                <div class="mb-3">
                    <input type="text" name="id" class="form-control"
                           placeholder="ID"
                           value="${id}"  style = "background-color:#e0ffff;" required>
                </div>

                <!-- パスワード -->
                <div class="mb-3">
                    <input type="password" name="password" id="password"
                           class="form-control"
                           placeholder="パスワード" style = "background-color:#e0ffff;" required>
                </div>

                <!-- 表示チェック -->
                <div class="form-check mb-3">
                    <input type="checkbox" class="form-check-input"
                           onclick="togglePassword()">
                    <label class="form-check-label">パスワードを表示</label>
                </div>

                <!-- ボタン -->
                <div class="text-center">
                    <button type="submit" class="btn btn-primary w-50">
                        ログイン
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>

	</c:param>
</c:import>