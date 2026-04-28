<%-- クラス一覧JSP --%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム  クラス管理
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me=4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>
            <div class="my-2 text-end px-4">
                <a href="ClassCreate.action">新規登録</a>
            </div>

            <c:choose>
                <c:when test="${classNums.size()>0}">
                    <table class="table table-hover">
                        <c:forEach var="c" items="${classNums}">
                            <tr>
                                <td>${c}</td>
                                <td><a href="ClassUpdate.action?class_num=${c}">変更</a></td>
                                <td><a href="ClassDelete.action?class_num=${c}">削除</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <div>クラス情報が存在しませんでした。</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>