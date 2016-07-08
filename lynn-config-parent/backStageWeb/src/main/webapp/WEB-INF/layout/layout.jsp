<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<link href="${ctx}/static/lynn/img/favicon.ico" rel="icon" type="image/x-icon" />
    <link href="${ctx}/static/lynn/img/favicon.ico" rel="shortcut icon" />
   <title>前隆金融用户后台管理系统</title>
    <script type="text/javascript">
    	var basePath = "${ctx}";
    </script>
    <tiles:insertAttribute name="import" />
    <script type="text/javascript" src="${ctx}/<tiles:insertAttribute name="customerJavascript" />"></script>
    
    <style type="text/css">
	   	.form-control-feedback {
	    	right: 15px;
		}
		.selectContainer .form-control-feedback {
	    	right: 25px;
		}
		.ui-autocomplete{
	        display:block;
	        z-index:99999;
    	}
    </style>
</head>
<body>


	<tiles:insertAttribute name="top" />
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
	
		<div class="main-container-inner">
		<tiles:insertAttribute name="left" />	
	
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>
	
					<%-- <ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="${ctx}/index">首页</a>
						</li>
						<li class="active">控制台</li>
					</ul> --%><!-- .breadcrumb -->
	
				</div>
	
				<div class="page-content">
					<tiles:insertAttribute name="center" />
				</div><!-- /.page-content -->
			</div><!-- /.main-content -->
	
		</div><!-- /.main-container-inner -->
	
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div><!-- /.main-container -->



<%--     <div class="container-fluid">
		<tiles:insertAttribute name="top" />
		<div class="row">
			<div id="left" class="col-md-2">
	    		<tiles:insertAttribute name="left" />
			</div>
			<div id="center" class="col-md-12">
				<tiles:insertAttribute name="center" />
			</div>
		</div>	    
	</div> --%>
	
	<iframe name="falseAjaxTarget" id="falseAjaxTarget" style="display:none;" id="falseAjaxTarget"></iframe>
</body>
</html>
