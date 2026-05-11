<!-- 先生情報の変更JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム 先生更新</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
    		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">先生情報変更</h2>
        <form action="TeacherUpdateExecute.action" method="post" class="container mt-4" style="max-width: 500px;">

            <!-- ID(書き換え不可) -->
            <div class="mb-3">
                <label class="form-label fw-bold">ID</label>
                <input type="text" name="id" value="${teacher.id}" class="form-control" readonly>
            </div>

            <!-- 氏名 -->
            <div class="mb-3">
                <label class="form-label fw-bold">氏名</label>
                <input type="text" name="name" value="${teacher.name}" class="form-control">
                <div class="text-danger small">${errors.name}</div>
            </div>

            <!-- 配属校 -->
            <div class="mb-3">
                <label class="form-label fw-bold">配属校</label>
                <select name="school" class="form-select">
                    <c:forEach var="school" items="${school_set}">
                        <option value="${school.cd}">${school.name}</option>
                    </c:forEach>
                </select>
                <div class="text-danger small">${errors.school}</div>
            </div>

            <!-- 権限 -->
            <div class="mb-3">
                <label class="form-label fw-bold">権限</label>
                <select name="permission" class="form-select">
                    <c:forEach var="permission" items="${permission_set}">
                        <option value="${permission.cd}">${permission.name}</option>
                    </c:forEach>
                </select>
            </div>
            

            <!-- ボタン -->
            <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-primary w-100">登録して終了</button>
                <a href="TeacherList.action" class="btn btn-secondary w-100">戻る</a>
            </div>

        </form>
    </c:param>
</c:import>
