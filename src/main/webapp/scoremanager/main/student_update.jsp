<!-- 学生情報の変更JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム 学生更新</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
    		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
        <form action="StudentUpdateExecute.action" method="post" class="container mt-4" style="max-width: 500px;">

            <!-- 入学年度 -->
            <div class="mb-3">
                <label class="form-label fw-bold">入学年度</label>
                <input type="text" name="entYear" value="${student.entYear}" class="form-control" readonly>
            </div>

            <!-- 学生番号 -->
            <div class="mb-3">
                <label class="form-label fw-bold">学生番号</label>
                <input type="text" name="no" value="${student.no}" class="form-control" readonly>
                <div class="text-danger small">${errors.no}</div>
            </div>

            <!-- 氏名 -->
            <div class="mb-3">
                <label class="form-label fw-bold">氏名</label>
                <input type="text" name="name" class="form-control" value="${student.name}"　placeholder="氏名を入力してください">
                <div class="text-danger small">${errors.name}</div>
            </div>

            <!-- クラス -->
            <div class="mb-3">
                <label class="form-label fw-bold">クラス</label>
                <select name="classNum" class="form-select">
                    <c:forEach var="c" items="${class_num_set}">
                        <option value="${c}">${c}</option>
                    </c:forEach>
                </select>
            </div>
            
            <!-- 在学中 -->
            <div class="mb-3">
            	<label class="form-label fw-bold">在学中</label>
            	<input type="checkbox" name="isAttend"
   					 ${student.isAttend ? "checked" : ""}>
            </div>

            <!-- ボタン -->
            <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-primary w-100">登録して終了</button>
                <a href="StudentList.action" class="btn btn-secondary w-100">戻る</a>
            </div>

        </form>
    </c:param>
</c:import>
