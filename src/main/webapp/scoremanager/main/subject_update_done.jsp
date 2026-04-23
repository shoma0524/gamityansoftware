<%-- 科目情報の変更完了JSP --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム 科目情報変更
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <div class="container mt-5">

            <div class="card">
                <div class="card-header">
                    科目情報変更
                </div>

                <div class="card-body">

                    <div class="alert alert-success text-center">
                        変更が完了しました
                    </div>

                    <div class="text-center">
                        <a href="SubjectList.action">科目一覧へ戻る</a>
                    </div>

                </div>
            </div>

        </div>
    </c:param>
</c:import>
