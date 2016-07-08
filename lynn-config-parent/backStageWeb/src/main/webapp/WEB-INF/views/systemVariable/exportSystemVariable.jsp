<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
        isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt"  prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
    .col-sm-12{
        padding-left:0px;
        padding-right:0px;
    }
    .display-none{
    	display:none;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue"></h3>
        <div class="nav-search" id="nav-search">
            <%--<form class="form-search">--%>
            	静态配置:
                <span class="input-icon">
                    <select type="text" placeholder="系统 ..." class="nav-search-input"
                           id="sSystemName" name="sSystemName" autocomplete="off" >
                           <option value="">全部</option>
                 	</select>
                </span>
                &nbsp;&nbsp;
                动态配置:
                <span class="input-icon">
                    <select type="text" placeholder="系统 ..." class="nav-search-input"
                            id="dSystemName" name="dSystemName" autocomplete="off" >
                        <option value="">全部</option>
                    </select>
                </span>
                &nbsp;&nbsp;
                <button class="btn btn-xs btn-yellow" onclick="exportZookeeper();">
                    <i class="icon-fire bigger-110"></i>
                    导出ZOOKEEPER配置
                    <i class="icon-arrow-right icon-on-right"></i>
                </button>
            <%--</form>--%>

        </div>
        <div class="table-header">
            	系统变量导出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div style="float:right;padding-right: 10px;height: 38px;font-size: 14px;">

            </div>
        </div>
        <%--<div >--%>
            <%--<table id="table1" class="table table-striped table-bordered table-hover"--%>
                <%--width="100%">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th>系统</th>--%>
                    <%--<th>模块</th>--%>
                    <%--<th>NID</th>--%>
                    <%--<th>变量名</th>--%>
                    <%--<th>变量值</th>--%>
                    <%--<th>说明</th>--%>
                    <%--<th>状态</th>--%>
                    <%--<th>更新时间</th>--%>
                    <%--<th>编辑</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
            <%--</table>--%>
        <%--</div>--%>
    </div>
</div>

<script type="text/javascript">

        $(initData());

        function initData(){
            InitStatus();
            InitSystem();
        }
        function InitStatus() {
            $.ajax({
                url: "${ctx}/systemVariable/allDataStatus",
                type: "get",
                dataType: "json",
                success: function (result) {
                	if(result==null||result.status!=1||result.status==null||result.data.resultStatus!=1){
                		alert(result.msg);
                	}else{
                		var option = result.data.value;
	                    $.each(option, function (i) {
	                    	if(option[i] == 'DELETED'){
		                        $("#aStatus").append("<option value='"+option[i]+"'>删除</option>");
	                    	}else if(option[i] == 'NORMAL'){
		                        $("#aStatus").append("<option value='"+option[i]+"'>正常</option>");
	                    	}
	                    });
                	}
                }
            })
        }

        function InitSystem() {
        	InitStaticSystem();
        	InitActiveSystem();
        }
        
        function InitStaticSystem() {
            $.ajax({
                url: "${ctx}/systemModule/allSystemName/ACTIVE",
                type: "get",
                dataType: "json",
                success: function (result) {
                	if(result==null||result.status!=1||result.status==null||result.data.resultStatus!=1){
                		alert(result.msg);
                	}else{
                		var option = result.data.value;
	                    $.each(option, function (i) {
	                        $("#sSystemName").append("<option value='"+option[i]+"'>" + option[i] + "</option>");
	                    })
                	}
                }
            })
        }
        
        function InitActiveSystem() {
            $.ajax({
                url: "${ctx}/systemModule/allSystemName/STATIC",
                type: "get",
                dataType: "json",
                success: function (result) {
                	if(result==null||result.status!=1||result.status==null||result.data.resultStatus!=1){
                		alert(result.msg);
                	}else{
                		var option = result.data.value;
	                    $.each(option, function (i) {
                            $("#dSystemName").append("<option value='"+option[i]+"'>" + option[i] + "</option>");
	                    })
                	}
                }
            })
        }
        


        function exportZookeeper(){
        	var sSystem = $("#sSystemName").val();
            var dSystem = $("#dSystemName").val();
            var url = "${ctx}/zookeeper/exportProps/"+sSystem+"&"+dSystem;
        	if (sSystem == null || $.trim(sSystem) == '' ) {
        		bootbox.alert('请选择您要导出的系统静态配置！');
        	} else if(dSystem == null || $.trim(dSystem) == ''){
                bootbox.alert('请选择您要导出的系统动态配置！');
            }else{
                window.location.href=url;
        	}
        }

    </script>