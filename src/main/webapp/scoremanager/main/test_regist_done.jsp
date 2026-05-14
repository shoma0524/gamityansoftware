<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:forward page="../../common/base.jsp">

	<jsp:param name="title" value="得点管理システム" />

	<jsp:param name="content" value='
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

        <div class="alert alert-success text-center">
            登録が完了しました
        </div>
        <br>

        <div class="mt-3">
            <a href="TestRegist.action">戻る</a>
			 &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="TestList.action">成績参照</a>
        </div>
    ' />

</jsp:forward>