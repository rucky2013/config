<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed')}catch(e){}
	</script>

	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="#" class="navbar-brand">
				<small>
					<%--<i class="icon-leaf"></i>--%>
					<img src="${ctx}/static/lynn/img/logo.ico">
					前隆金融用户后台管理系统
				</small>
			</a><!-- /.brand -->
		</div><!-- /.navbar-header -->

		<div class="navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">

				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<%--<img class="nav-user-photo" src="${ctx}/static/assets/avatars/admin.jpg" />--%>
						<i class=" nav-user-photo icon-animated-vertical icon-leaf"></i>
								<span class="user-info">
									<small>Welcome,</small>
									Admin
								</span>

						<i class="icon-caret-down"></i>
					</a>

					<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li>
							<a href="#" onclick="logout();">
								<i class="icon-off"></i>
								Logout
							</a>
						</li>
					</ul>
				</li>
			</ul><!-- /.ace-nav -->
		</div>



	</div><!-- /.container -->
</div>




<script type="application/javascript">
	function logout(){
		var url = "${ctx}/logout";
		bootbox.confirm("是否确认退出？",function(result){
			if(result){
				window.location.href=url;
			}else{
				return ;
			}
		});
	}
</script>
<%--<input type="hidden" id="loginUserName" value="${userSessionKey.username}">--%>
<%--<input type="hidden" id="loginUserType" value="${userSessionKey.type}">--%>
<%--<input type="hidden" id="loginNetInfoId" value="${netInfoSessionKey.id}">--%>