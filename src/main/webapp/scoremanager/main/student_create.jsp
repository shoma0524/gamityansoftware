<!-- 学生登録JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム 新規登録</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
    		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
        <form action="StudentCreateExecute.action" method="post" class="container mt-4" style="max-width: 500px;">

            <!-- 入学年度 -->
            <div class="mb-3">
                <label class="form-label fw-bold">入学年度</label>
                <select name="ent_year" class="form-select">
            		<option value="0">------</option>
				  	<c:forEach var="y" items="${ent_year_set}">
				    	<option value="${y}">${y}</option>
				  	</c:forEach>
				</select>
            </div>

            <!-- 学生番号 -->
            <div class="mb-3">
                <label class="form-label fw-bold">学生番号</label>
                <input type="text" name="no" class="form-control" placeholder="学生番号を入力してください">
                <div class="text-danger small">${errors.no}</div>
            </div>

            <!-- 氏名 -->
            <div class="mb-3">
                <label class="form-label fw-bold">氏名</label>
                <input type="text" name="name" class="form-control" placeholder="氏名を入力してください">
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

            <!-- ボタン -->
            <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-primary w-100">登録して終了</button>
                <a href="StudentList.action" class="btn btn-secondary w-100">戻る</a>
            </div>

        </form>
    </c:param>
</c:import>
