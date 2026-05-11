
<%--先生管理一覧JSP --%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
        得点管理システム 科目管理
    </c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">先生管理</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>


			<c:choose>
				<c:when test="${teachers.size()>0}">
					<table class="table table-hover">
						<tr>
							<th>ID</th>
							<th>名前</th>
							<th>配属校</th>
							<th>権限</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="teacher" items="${teachers}">
							<tr>
								<td>${teacher.id}</td>
								<td>${teacher.name}</td>
								<td>${teacher.school.name}</td>
								<td>${teacher.permission.name}</td>
								<td><a href="TeacherUpdate.action?id=${teacher.id}">変更</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>

				<c:otherwise>
					<div>先生情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>

