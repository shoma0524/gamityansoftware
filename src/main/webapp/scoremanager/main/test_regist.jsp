<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="mainContent">
	<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

	<form action="TestRegist.action" method="get"
		class="card card-body bg-light mb-4">
		<div class="row g-3">

			<div class="col-md-2">
				<label class="form-label">入学年度</label>
				<select name="f1" class="form-select">
					<option value="">--------</option>
					<c:forEach var="year" items="${ent_year_set}">
						<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-2">
				<label class="form-label">クラス</label>
				<select name="f2" class="form-select">
					<option value="">--------</option>
					<c:forEach var="num" items="${class_num_set}">
						<option value="${num}" ${num == f2 ? 'selected' : ''}>${num}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-4">
				<label class="form-label">科目</label>
				<select name="f3" class="form-select">
					<option value="">--------</option>
					<c:forEach var="sub" items="${subjects}">
						<option value="${sub.cd}" ${sub.cd == f3 ? 'selected' : ''}>${sub.name}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-2">
				<label class="form-label">回数</label>
				<select name="f4" class="form-select">
					<option value="">--------</option>
					<c:forEach var="n" begin="1" end="2">
						<option value="${n}" ${n == f4 ? 'selected' : ''}>${n}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-2 d-flex align-items-end">
				<button type="submit" class="btn btn-secondary">検索</button>
			</div>
		</div>
		<div class="text-danger small">${errors.search}</div>
	</form>

	<c:choose>

		<c:when test="${tests != null and tests.size() > 0}">
			<div class="mb-3">科目：${subject.name} （${f4}回）</div>

			<form action="TestRegistExecute.action" method="post">
				<input type="hidden" name="f1" value="${f1}">
				<input type="hidden" name="f2" value="${f2}">
				<input type="hidden" name="f3" value="${f3}">
				<input type="hidden" name="f4" value="${f4}">

				<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
						<c:forEach var="item" items="${tests}">
							<tr>
								<td>${item.student.entYear}</td>
								<td>${item.classNum}</td>
								<td>${item.student.no}
									<input type="hidden" name="student_no_set" value="${item.student.no}">
								</td>
								<td>${item.student.name}</td>
								<td>
									<!-- ★ 初回は空、エラー時は値保持 -->
									<input type="text" name="point_${item.student.no}" class="form-control"
									inputmode="numeric" maxlength="10"
									<c:if test="${item.point >= 0}">value="${item.point}"</c:if>
									 style="width: 180px;">
									<div class="text-danger small">${errors[item.student.no.toString()]}</div>
								</td>
							</tr>
						</c:forEach>
				</table>

				<button type="submit" class="btn btn-secondary">登録して終了</button>
			</form>
		</c:when>

		<c:when test="${(tests != null and tests.size() == 0)}">
			<div class="alert alert-info">該当するデータが見つかりませんでした。</div>
		</c:when>

	</c:choose>
</c:set>

<jsp:forward page="../../common/base.jsp">
	<jsp:param name="title" value="得点管理システム" />
	<jsp:param name="content" value="${mainContent}" />
</jsp:forward>