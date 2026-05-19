<%-- 科目情報の削除確認JSP --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <h2 class="h3 mb-3 bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

        <div class="container mt-4" style="max-width: 500px;">
            <div class="alert alert-danger">
                以下の科目を削除します。よろしいですか？
            </div>

            <table class="table table-bordered">
                <tr>
                    <th class="bg-light w-25">科目コード</th>
                    <td><c:out value="${subject.cd}"></c:out></td>
                </tr>
                <tr>
                    <th class="bg-light">科目名</th>
                    <td><c:out value="${subject.name}"></c:out></td>
                </tr>
            </table>

            <div class="d-flex gap-2 mt-4">
                <a href="SubjectDeleteExecute.action?cd=<c:out value="${subject.cd}"></c:out>" class="btn btn-danger w-100">削除する</a>
                <a href="SubjectList.action" class="btn btn-secondary w-100">キャンセル</a>
            </div>
        </div>
    </c:param>
</c:import>
