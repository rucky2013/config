<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html >
<head>
	<meta charset="utf-8" />
	<title>登录 - 前隆金融用户后台管理系统</title>
	<link href="${ctx}/static/lynn/img/favicon.ico" rel="icon" type="image/x-icon" />
	<link href="${ctx}/static/lynn/img/favicon.ico" rel="shortcut icon" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="${ctx}/static/assets/css/font-awesome.min.css" />


	<link rel="stylesheet" href="${ctx}/static/assets/css/ace.min.css" />
	<link rel="stylesheet" href="${ctx}/static/assets/css/ace-rtl.min.css" />
</head>

<body class="login-layout">
<div class="main-container">
	<div class="main-content">
		<div class="row">
			<div class="col-sm-10 col-sm-offset-1">
				<div class="login-container">
					<div class="center">
						<h1>
							<%--<i class="icon-leaf green"></i>--%>
							<img src="${ctx}/static/lynn/img/logo.ico">
							<span class="white">用户后台管理系统</span>
						</h1>
						<h4 class="blue">&copy; 前隆金融</h4>
					</div>

					<div class="space-6"></div>

					<div class="position-relative">
						<div id="login-box" class="login-box visible widget-box no-border">
							<div class="widget-body">
								<div class="widget-main">
									<h4 class="header blue lighter bigger">
										<i class="icon-coffee green"></i>

									</h4>

									<div class="space-6"></div>

									<form action="${ctx}/login" method="post">
										<fieldset>
											<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="userName" class="form-control" placeholder="用户名" />
															<i class="icon-user"></i>
														</span>
											</label>

											<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="password" class="form-control" placeholder="密码" />
															<i class="icon-lock"></i>
														</span>
											</label>

											<div class="space"></div>

											<div class="clearfix">

												<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
													<i class="icon-key"></i>
													Login
												</button>
											</div>

											<div class="space-4"></div>
										</fieldset>
									</form>

								</div><!-- /widget-main -->

							</div><!-- /widget-body -->
						</div><!-- /login-box -->
					</div><!-- /position-relative -->
				</div>
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div>
</div><!-- /.main-container -->



</body>
</html>
