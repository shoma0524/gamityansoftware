<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:forward page="../../common/base.jsp">

    <jsp:param name="title" value="成績登録" />

   
    <jsp:param name="content" value='
        <h2 class="mb-4">成績管理</h2>

        
        <div class="mb-3">
            科目：${subject.name} （${f4}回）
        </div>

        <%-- エラーメッセージ表示 --%>
        <c:if test="${not empty errors}">
            <div class="alert alert-danger">${errors}</div>
        </c:if>

        <%-- 登録フォーム --%>
        <form action="TestRegistExecute.action" method="post">
      
            <input type="hidden" name="ent_year" value="${f1}">
            <input type="hidden" name="class_num" value="${f2}">
            <input type="hidden" name="subject_cd" value="${f3}">
            <input type="hidden" name="num" value="${f4}">

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
                    <c:forEach var="item" items="${test_list_subject}">
                        <tr>
                            <td>${item.entYear}</td>
                            <td>${item.classNum}</td>
                            <td>
                                ${item.studentNo}
                                <input type="hidden" name="student_no_set[]" value="${item.studentNo}">
                            </td>
                            <td>${item.studentName}</td>
                            <td>

                                <input type="number" name="point_${item.studentNo}" 
                                       value="${item.point == -1 ? \"\" : item.point}" 
                                       min="0" max="100" class="form-control" style="width: 100px;">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="mt-4">
                <button type="submit" class="btn btn-primary">登録して終了</button>
            </div>
        </form>

        <div class="mt-3">
            <a href="TestList.action">戻る</a>
        </div>
    ' />
</jsp:forward>