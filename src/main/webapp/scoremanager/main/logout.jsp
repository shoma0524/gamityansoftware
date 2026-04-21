<!-- ログアウト完了JSP -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">	
	<c:param name="title">
		得点管理システム ログアウト
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
			<div class="container mt-5">
			
			    <div class="card">
			        <div class="card-header">
			            ログアウト
			        </div>
			
			        <div class="card-body">
			
			            <div class="alert alert-success text-center">
			                ログアウトしました
			            </div>
			
			            <div class="text-center">
			                <a href="../Login.action">ログイン</a>
			            </div>
			
			        </div>
			    </div>
			
			</div>
	</c:param>
</c:import>
