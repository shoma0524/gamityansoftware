<!-- 学生CSV登録JSP -->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム 新規登録</c:param>
    <c:param name="scripts"></c:param>
	
    <c:param name="content">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">CSVアップロード</h2>
		<c:if test="${not empty errors}">
		    <div class="alert alert-danger">
		        <strong>エラーがあります：</strong>
		        <ul class="mb-0">
		            <c:forEach var="err" items="${errors}">
		                <li>${err}</li>
		            </c:forEach>
		        </ul>
		    </div>
		</c:if>
		<div class="row justify-content-center">
	        <div class="col-md-6">
	
	            <form action="UploadCsv.action" method="post" enctype="multipart/form-data">
	
	                <!-- ファイル選択 -->
	                <div class="mb-5">
	                    <label class="form-label fw-bold">CSVファイル</label>
	                    <input type="file"
	                           name="csvFile"
	                           class="form-control form-control-lg"
	                           required>
	                </div>
	
	                <!-- ボタン -->
	                <div class="d-flex justify-content-between">
	                    <button type="submit" class="btn btn-primary px-5">
	                        登録して終了
	                    </button>
	
	                    <a href="StudentList.action" class="btn btn-secondary px-5">
	                        戻る
	                    </a>
	                </div>
	
	            </form>
	
	        </div>
    	</div>
    </c:param>
</c:import>
