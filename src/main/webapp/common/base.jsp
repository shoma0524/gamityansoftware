<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
			rel="stylesheet">
		<title>${param.title}</title>
		<script>${param.scripts }</script>
	</head>

	<body class="d-flex flex-column min-vh-100 bg-light">

		<!-- ヘッダー -->
		<header>
			<div class=" p-3 d-flex justify-content-between border-bottom" style = "background-color:#e0ffff;">
  				<a href="menu.jsp" class="text-decoration-none text-reset">
				    <h1 class="h4 m-0">得点管理システム</h1>
				</a>
				<c:if test="${not empty sessionScope.user}">
  				<div>
    				<span>${sessionScope.user.name} 様</span>
    				<a href="Logout.action" class="ms-2">ログアウト</a>
  				</div>
  				</c:if>
			</div>
		</header>

		<!-- メインレイアウト -->

		<div class="flex-grow-1 d-flex">

				<!-- サイドバー（ログイン時のみ表示） -->
				<c:if test="${not empty sessionScope.user}">
					<nav class="bg-white border-end p-3" style="width: 220px;">
						<h5 class="mb-3">メニュー</h5>
						<ul class="nav flex-column">
							<li class="nav-item">
								<a class="nav-link" href="menu.jsp">メニュー</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="StudentList.action">学生管理</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="TestRegist.action">成績登録</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="TestList.action">成績参照</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="SubjectList.action">科目管理</a>
							</li>
						</ul>
					</nav>
				</c:if>

				<!-- コンテンツ -->
				<main class="flex-fill p-4">
					${param.content}
				</main>

		</div>
		<footer class="bg-light border-top text-center py-3 ">
    		<small>© 2023 TIC<br>大原学園</small>
		</footer>
	</body>
</html>