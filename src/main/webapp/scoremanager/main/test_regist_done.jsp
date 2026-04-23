<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:forward page="../../common/base.jsp">
    
    <
    <jsp:param name="title" value="成績登録完了" />

    
    <jsp:param name="content" value='
        <h2 class="mb-4">成績管理</h2>

        <div class="alert alert-success">
            登録が完了しました
        </div>

        <div class="mt-3">
            <a href="TestList.action">戻る</a>
            &nbsp;&nbsp;
            <a href="TestSearch.action">成績参照</a>
        </div>
    ' />

</jsp:forward>