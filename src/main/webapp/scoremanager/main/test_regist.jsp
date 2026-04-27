<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="mainContent">
    <h2 class="mb-4">成績管理</h2>

    <c:if test="${not empty errors}">
        <div class="alert alert-danger">${errors}</div>
    </c:if>

    <form action="TestRegist.action" method="get"
        class="card card-body bg-light mb-4">
        <div class="row g-3">

            <div class="col-md-2">
                <label class="form-label">入学年度</label>
                <select name="f1" class="form-select" required>
                    <option value="" disabled ${empty f1 ? 'selected' : ''}>--------</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" ${year == f1 ? 'selected' : ''}>${year}</option>
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
                <select name="f4" class="form-select" required>
                    <option value="" disabled ${empty f4 ? 'selected' : ''}>--------</option>
                    <c:forEach var="n" begin="1" end="10">
                        <option value="${n}" ${n == f4 ? 'selected' : ''}>${n}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-2 d-flex align-items-end">
                <button type="submit" class="btn btn-secondary w-100">検索</button>
            </div>
        </div>
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
                    <thead>
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${tests}">
                            <tr>
                                <td>${item.student.entYear}</td>
                                <td>${item.classNum}</td>
                                <td>
                                    ${item.student.no}
                                    <input type="hidden" name="student_no_set" value="${item.student.no}">
                                </td>
                                <td>${item.student.name}</td>
                                <td>
                                    <!-- ★ 初回は空、エラー時は値保持 -->
                                    <input type="number"
                                        name="point_${item.student.no}"
                                        value="${param['point_' += item.student.no]}"
                                        min="0" max="100"
                                        
                                        class="form-control"
                                        style="width: 100px;">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <button type="submit" class="btn btn-primary mt-3">登録して終了</button>
            </form>
        </c:when>

        <c:when test="${not empty f1 and (tests == null or tests.size() == 0)}">
            <div class="alert alert-info">該当するデータが見つかりませんでした。</div>
        </c:when>

    </c:choose>

    <div class="mt-3">
        <a href="menu.jsp">戻る</a>
    </div>
</c:set>

<jsp:forward page="../../common/base.jsp">
    <jsp:param name="title" value="成績登録" />
    <jsp:param name="content" value="${mainContent}" />
</jsp:forward>