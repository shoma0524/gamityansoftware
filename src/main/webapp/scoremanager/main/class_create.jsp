<!-- クラス登録JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム 新規登録</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス登録</h2>
        <form action="ClassCreateExecute.action" method="post" class="container mt-4" style="max-width: 500px;">

            <!--クラス番号-->
            <div class="mb-3">
                <label class="form-label fw-bold">クラス番号</label>
                <input type="text" name="class_num" value="${class_num}" class="form-control" placeholder="クラス番号を入力してください" required>
                <div class="text-danger small">${errors.class_num}</div>
            </div>

             <!--ボタン-->
             <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-primary w-100">登録</button>
                <a href="ClassList.action" class="btn btn-secondary w-100">戻る</a>
             </div>
             
        </form>
    </c:param>
</c:import>
