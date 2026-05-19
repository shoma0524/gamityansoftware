<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="content">
			<%-- タイトルエリア --%>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<%-- 検索条件エリア --%>
			<div class="border p-3 mb-4" style="background-color: #fff;">
				<form method="get" action="TestListSubjectExecute.action">
					<%-- 科目情報での検索 --%>
					<div class="row align-items-center mb-3">
						<div class="col-2">
							<label class="small text-secondary">科目情報</label>
						</div>
						<div class="col-auto">
							<label class="small d-block">入学年度</label>
							<select name="f1" class="form-select form-select-sm" style="width: 120px;">
								<option value="">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="<c:out value="${year}"></c:out>" <c:if test="${year == f1}">selected</c:if>><c:out value="${year}"></c:out></option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto">
							<label class="small d-block">クラス</label>
							<select name="f2" class="form-select form-select-sm" style="width: 120px;">
								<option value="">--------</option>
								<c:forEach var="c" items="${class_num_set}">
									<option value="<c:out value="${c}"></c:out>" <c:if test="${c == f2}">selected</c:if>><c:out value="${c}"></c:out></option>
								</c:forEach>
							</select>
						</div>
						<div class="col-3">
							<label class="small d-block">科目</label>
							<select name="f3" class="form-select form-select-sm" style="width: 200px;">
								<option value="">--------</option>
								<c:forEach var="subject" items="${subjects}">
									<option value="<c:out value="${subject.cd}"></c:out>" <c:if test="${subject.cd == f3}">selected</c:if>><c:out value="${subject.name}"></c:out></option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto pt-4">
							<input type="hidden" name="f" value="sj">
							<button type="submit" class="btn btn-secondary btn-sm px-4">検索</button>
						</div>
					</div>
					<div class="text-danger small">${errors.subject}</div>
				</form>

				<hr>

				<%-- 学生番号での検索 --%>
				<form method="get" action="TestListStudentExecute.action">
					<div class="row align-items-center">
						<div class="col-2">
							<label class="small text-secondary">学生情報</label>
						</div>
						<div class="col-4">
							<label class="small d-block">学生番号</label>
							<input type="text" name="studentNo" class="form-control form-control-sm"
								inputmode="numeric" maxlength="10"
								value="<c:out value="${studentNo}"></c:out>"
								placeholder="学生番号を入力してください" style="width: 250px;"  required>
						</div>
						<div class="col-auto pt-4">
							<input type="hidden" name="f" value="st">
							<button type="submit" class="btn btn-secondary btn-sm px-4">検索</button>
						</div>
					</div>
					<div class="text-danger small">${errors.student}</div>
				</form>
			</div>

			<div class="alert alert-info mt-3" role="alert">
				科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</div>
	</c:param>
</c:import>