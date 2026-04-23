<!-- 科目登録完了JSP -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム 新規登録
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <div class="container mt-5">
            
            <div class="card">
                <div class="card-header">
                    科目情報登録
                </div>
                
                <div class="card-body">

                    <div class="alert alert-success text-center">
                        登録が完了しました
                    </div>
                    
                    <div class="text-center">
                        <a href="SubjectCreate.action">戻る</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="SubjectList.action">科目一覧</a>
                    </div>
                </div>
            </div>
        </div>
    </c:param>
</c:import>