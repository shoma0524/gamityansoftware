<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="content" scope="request">
    <div class="p-1">
        <%-- タイトルエリア --%>
        <div class="p-2 mb-3" style="background-color: #f8f9fa; border: 1px solid #dee2e6;">
            <h2 class="h5 m-0">成績参照</h2>
        </div>

        <%-- 検索条件エリア --%>
        <div class="border p-3 mb-4" style="background-color: #fff;">
            <form method="get" action="TestListSubject.action">
                <%-- 科目情報での検索 --%>
                <div class="row align-items-center g-3 mb-3">
                    <div class="col-auto">
                        <label class="small text-secondary">科目情報</label>
                    </div>
                    <div class="col-auto">
                        <label class="small d-block">入学年度</label>
                        <select name="f1" class="form-select form-select-sm" style="width: 120px;">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <label class="small d-block">クラス</label>
                        <select name="f2" class="form-select form-select-sm" style="width: 120px;">
                            <option value="0">--------</option>
                            <c:forEach var="c" items="${class_num_set}">
                                <option value="${c}" <c:if test="${c == f2}">selected</c:if>>${c}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <label class="small d-block">科目</label>
                        <select name="f3" class="form-select form-select-sm" style="width: 200px;">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto pt-4">
                        <button type="submit" class="btn btn-secondary btn-sm px-4">検索</button>
                    </div>
                </div>
            </form>

            <hr>

            <%-- 学生番号での検索 --%>
            <form method="get" action="TestListStudent.action">
                <div class="row align-items-center g-3">
                    <div class="col-auto">
                        <label class="small text-secondary">学生情報</label>
                    </div>
                    <div class="col-auto">
                        <label class="small d-block">学生番号</label>
                        <input type="text" name="f4" class="form-control form-control-sm" placeholder="学生番号を入力してください" style="width: 250px;">
                    </div>
                    <div class="col-auto pt-4">
                        <button type="submit" class="btn btn-secondary btn-sm px-4">検索</button>
                    </div>
                </div>
            </form>
        </div>

        <div class="alert alert-info mt-3" role="alert">
  			科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
		</div>
    </div>
</c:set>

<jsp:forward page="../../common/base.jsp">
    <jsp:param name="title" value="成績管理" />
</jsp:forward>