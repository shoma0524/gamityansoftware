<!-- 科目登録JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム 新規登録</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目登録</h2>
        <form action="SubjectCreateExecute.action" method="post" class="container mt-4" style="max-width: 500px;">

            <!--科目コード-->
            <div class="mb-3">
                <label class="form-label fw-bold">科目コード</label>
                <input type="text" name="cd" value="${cd}" class="form-control" placeholder="科目コードを入力してください" required>
                <div class="text-danger small">${errors.cd}</div>
            </div>

            <!-- 科目名 -->
             <div class="mb-3">
                <label class="form-label fw-bold">科目名</label>
                <input type="text" name="name" value="${name}" class="form-control" placeholder="科目名を入力してください" required>
                <div class="text-danger small">${errors.name}</div>
             </div>

             <!--ボタン-->
             <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-primary w-100">登録</button>
                <a href="SubjectList.action" class="btn btn-secondary w-100">戻る</a>
             </div>
             
        </form>
    </c:param>
</c:import>