<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Pi-Injector</title>
		
		<!-- jQuery -->
		<script src="lib/jquery.js"></script>
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="lib/bootstrap/css/bootstrap-theme.min.css">
		<script src="lib/bootstrap/js/bootstrap.min.js"></script>
		<!-- pour les mobiles -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- Chart.js -->
		<script src="lib/chart.js/Chart.min.js"></script>
		
		<!-- JavaScript -->
		<script type="text/javascript" src="javascript/javascript.js" charset="UTF-8"></script>
		
		<!-- CSS -->
		<link rel="stylesheet" href="css/style.css">
	</head>
	<body>
		<%@ include file="component/menu-top.jsp" %>
		
		<div class="container">
			<div class="row">
				<div class="container">
					<img src="img/logo.png" height="100px">
				</div>
				
				<div class="col-sm-10" role="main">
					<% if("test-display-all".equals(request.getParameter("page"))) { %>
						<%@ include file="page/test-display-all.jsp" %>
					<% }
					else { %>
						<%@ include file="page/test-new.jsp" %>
					<% } %>
				</div>
			</div>
		</div>
	</body>
</html>