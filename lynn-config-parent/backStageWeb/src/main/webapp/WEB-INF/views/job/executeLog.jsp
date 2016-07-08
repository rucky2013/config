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
            <form class="form-search">
            	状态:
                <span class="input-icon">
                    <select type="text" placeholder="状态 ..." class="nav-search-input"
                           id="status" name="status" autocomplete="off" >
                           <option value="">全部</option>
                 	</select>
                </span>&nbsp;&nbsp;
                 任务名:
                <span class="input-icon">
                    <input type="text" placeholder="任务名 ..." class="nav-search-input"
                           id="jobName" name="jobName" autocomplete="off" />
                    <i class="icon-search nav-search-icon"></i>
                </span>
                &nbsp;&nbsp;
               <span class="input-icon">
                	<i class="icon-calendar" ></i>
                    <input type="text" placeholder="开始日期..."  data-date-format="yyyy-mm-dd" class="nav-search-input"
                           id="startDate" name="startDate" autocomplete="off" />
                    
                </span>&nbsp;&nbsp;
                <span class="input-icon">
                	<i class="icon-calendar" ></i>
                    <input type="text" placeholder="结束日期..." class="nav-search-input" data-date-format="yyyy-mm-dd"
                           id="endDate" name="endDate" autocomplete="off" />
               
                </span>
                <a href="#" onclick="initData('-');">
                    <span class="label label-lg label-pink arrowed-right">查询</span>
                </a>
            </form>

        </div>
        <div class="table-header">
            	执行记录列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <div >
            <table id="table1" class="table table-striped table-bordered table-hover"
                width="100%">
                <thead>
                <tr>
	                <th>任务名称</th>
	                <th>执行时间</th>                  
	                <th>结果</th>                  
	                <th>访问链接</th>
	                <th>返回报文</th>
	                <th>服务器IP</th>
                </tr>
               </thead>
            </table>
        </div>
    </div>
</div>

    <script type="text/javascript">
        $(initData());

        function initData(url){
            if(url == null || url == ''){
                $('#id').val('');
                url = '/job/executeLog/&&&';
                InitStatus();
           
            }else{
            	var startDate = Date.parse($('#startDate').val());
            	var endDate = Date.parse($('#endDate').val());
            	if(startDate>endDate){
            		bootbox.alert('开始时间不得大于结束时间！');
            		return;
            	}
                url = '/job/executeLog/'+$('#status').val()+'&'+$('#jobName').val()+'&'+$('#startDate').val()+'&'+$('#endDate').val();
            }
            url = '${ctx}/webService/get/quartz_url?path='+encodeURIComponent(url);
            var table=$("#table1").dataTable({
                "height":"600px",
                //"scrollY":"600px",
                //"scrollCollapse":   true,
                "sAjaxSource": url,
                "sServerMethod":"GET",
                "serverSide":true,
                "processing": true,
                "destroy":true,
                "lengthMenu": [[10, 25], [10,25]],
                "aoColumnDefs": [
                    { "sWidth": "10%", "aTargets": [ 0 ] },
                    { "sWidth": "15%", "aTargets": [ 1 ]},
                    { "sWidth": "5%", "aTargets": [ 2 ]},
                    { "sWidth": "10%", "aTargets": [ 5 ]}
                ],
                "columns":[                   
                    { "data": "jobName"},
                    { "data": "createTime"},
                    {"data":null,
                        render : function(data,type,now){
                            if(data.status == 'FAILURE'){
                                return "<div style='color:orangered'>失败</div>";
                            }else if(data.status=='SUCCESS'){
                                return "<div style='color:green'>成功</div>";
                            }else {
                            	return "已失效";
                            }
                        }
                    },
                    { "data": "url"},
                    { "data": "reponsePackets"},
                    {"data":"serverIp"}
                ]
            });
        }
        
        function InitStatus() {
            $.ajax({
                url: "${ctx}/webService/get/quartz_url?path="+encodeURIComponent("/job/getResultStatus"),
                type: "get",
                dataType: "json",
                success: function (result) {
                	if(result==null||result.status!=1||result.error!=00000000){
                		alert(result.msg);
                	}else{
                		var option = result.data;
	                    $.each(option, function (i) {
	                    	if(option[i] == 'FAILURE'){
		                        $("#status").append("<option value='"+option[i]+"'>失败</option>");
	                    	}else if(option[i] == 'SUCCESS'){
		                        $("#status").append("<option value='"+option[i]+"'>成功</option>");
	                    	}
	                    });
                	}
                }
            })
        }
        
        $("#startDate").datepicker({autoclose:true}).next().on(ace.click_event, function(){
            $(this).prev().focus();
        });
        $("#endDate").datepicker({autoclose:true}).next().on(ace.click_event, function(){
            $(this).prev().focus();
        });
       
    </script>