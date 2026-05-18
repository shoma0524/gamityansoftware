<!-- クラス登録完了JSP -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="container mt-5">

			<div class="card">
				<div class="card-header">クラス情報登録</div>

				<div class="card-body">

					<div class="alert alert-success text-center">登録が完了しました</div>

					<div class="text-center">
						<a href="ClassCreate.action">戻る</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
							href="ClassList.action">クラス一覧</a>
					</div>
				</div>
			</div>
		</div>
	</c:param>
</c:import>
