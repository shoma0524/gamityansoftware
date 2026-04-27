<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="content" scope="request">
    <div class="p-1">
        <div class="p-2 mb-3" style="background-color: #f8f9fa; border: 1px solid #dee2e6;">
            <h2 class="h5 m-0">成績一覧（科目）</h2>
        </div>

        <%-- 検索フォームエリア --%>
        <div class="border p-3 mb-4" style="background-color: #fff;">
            <form method="get" action="TestListSubjectExecute.action">
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
                            <c:forEach var="sub" items="${subjects}">
                                <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto pt-4">
                        <button type="submit" class="btn btn-secondary btn-sm px-4">検索</button>
                    </div>
                </div>
            </form>
        </div>

        <%-- 結果表示エリア --%>
        <c:choose>
            <c:when test="${not empty test_list_subject}">
                <div class="mb-2 fw-bold">科目：${subject.name}</div>
                <table class="table table-sm table-hover border-top">
                    <thead>
                        <tr>
                            <th class="fw-normal small">入学年度</th>
                            <th class="fw-normal small">クラス</th>
                            <th class="fw-normal small">学生番号</th>
                            <th class="fw-normal small">氏名</th>
                            <th class="fw-normal small">1回目</th>
                            <th class="fw-normal small">2回目</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="test" items="${test_list_subject}">
                            <tr>
                                <%-- 【重要】 .student を削除しました --%>
                                <td>${test.entYear}</td>
                                <td>${test.classNum}</td>
                                <td>${test.no}</td>
                                <td>${test.name}</td>
                                <td>${test.point[0]}</td>
                                <td>${test.point[1]}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="text-danger small">${errors}</div>
            </c:otherwise>
        </c:choose>
    </div>
</c:set>

<jsp:forward page="../../common/base.jsp">
    <jsp:param name="title" value="成績管理" />
</jsp:forward>