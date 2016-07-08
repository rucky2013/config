<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- css start -->
<!-- Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
<!-- Datatable css文件 -->
<link rel="stylesheet" href="${ctx}/static/datatable/media/css/jquery.dataTables.min.css">
<!-- datetimepicker css文件 -->
<link rel="stylesheet" href="${ctx}/static/datetimepicker/css/bootstrap-datetimepicker.min.css">


<link rel="stylesheet" href="${ctx}/static/assets/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${ctx}/static/assets/css/font-awesome-ie7.min.css" />
<![endif]-->

<!-- fonts -->
<link rel="stylesheet" href="${ctx}/static/assets/css/family.css" />

<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/static/assets/css/ace.min.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/ace-skins.min.css" />


<link rel="stylesheet" href="${ctx}/static/zTree/zTreeStyle/zTreeStyle.css"/>

<!--[if lte IE 8]>
<link rel="stylesheet" href="${ctx}/static/assets/css/ace-ie.min.css" />
<![endif]-->

<!-- ace settings handler -->

<script src="${ctx}/static/assets/js/ace-extra.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
<script src="${ctx}/static/assets/js/html5shiv.js"></script>
<script src="${ctx}/static/assets/js/respond.min.js"></script>
<![endif]-->

<!-- jquery -->
<!--[if !IE]> -->
<script src="${ctx}/static/jquery/jquery-2.1.1.min.js"></script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.11.1.min.js"></script>
<![endif]-->
<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
</script>
<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctx}/static/jquery/jquery-1.11.1.min.js'>"+"<"+"script>");
</script>
<![endif]-->

<script type="text/javascript">
	if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>

<!-- javascript start -->
<!-- Bootstrap 核心 JavaScript 文件 -->
<script  type="text/javascript"  src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/assets/js/typeahead-bs2.min.js"></script>

<!--[if lte IE 8]>
  <script src="${ctx}/static/jquery-flot/excanvas.min.js"></script>
<![endif]-->

<!-- Datatable 核心 JavaScript 文件 -->
<script  type="text/javascript"  src="${ctx}/static/datatable/media/js/jquery.dataTables.min.js"></script>
<script  type="text/javascript"  src="${ctx}/static/datatable/media/js/dataTables.bootstrap.js"></script>

<!-- datetimepicker 核心 JavaScript 文件 -->
<script  type="text/javascript"  src="${ctx}/static/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-ui-1.11.2.custom/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easy-pie-chart/jquery.easypiechart.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-sparkline/jquery.sparkline.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-flot-0.8.3/jquery.flot.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-flot-0.8.3/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-flot-0.8.3/jquery.flot.resize.min.js"></script>
<!-- ace scripts -->
<script type="text/javascript" src="${ctx}/static/assets/js/ace-elements.min.js"></script>
<script type="text/javascript" src="${ctx}/static/assets/js/ace.min.js"></script>

<script type="text/javascript" src="${ctx}/static/assets/js/bootbox.min.js"></script>

<script type="text/javascript" src="${ctx}/static/jquery/jquery.cookie.js"></script>

<script type="text/javascript" src="${ctx}/static/jquery/jquery.cookie.js"></script>

<script type="text/javascript"  src="${ctx}/static/assets/js/jquery.validate.min.js"></script>

<script  type="text/javascript"  src="${ctx}/static/lynn/js/lynn.js"></script>

<script  type="text/javascript"  src="${ctx}/static/zTree/jquery.ztree.core-3.5.js"></script>

<script  type="text/javascript"  src="${ctx}/static/zTree/jquery.ztree.excheck-3.5.js"></script>

<script type="application/javascript">
    function closeDiv(src){
        $(src).parent().parent().parent().parent().modal('hide');
    }
</script>

<script src="${ctx}/static/assets/js/zzsc.js"></script>


<!-- 开始时间,年-月-日 时:分 -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/datetimepicker/css/jquery.datetimepicker.css"/>
<script src="${ctx}/static/datetimepicker/js/jquery.datetimepicker.js"></script>


<script type="text/javascript" src="${ctx}/static/assets/js/date-time/bootstrap-datepicker.min.js" ></script>

<link rel="stylesheet" href="${ctx}/static/assets/css/chosen.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/bootstrap-timepicker.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/daterangepicker.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/colorpicker.css" />

<script type="text/javascript"  src="${ctx}/static/lynn/js/dateUtils.js"></script>
