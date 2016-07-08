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
	<div class="col-xs-12" >
        <span class="label label-lg label-success arrowed-in-right">任务开关</span>
        <input name="quartzStatus" class="ace ace-switch ace-switch-6" title="任务开关" type="checkbox" id="quartzStatus" onclick="setQuartz();"  />
        <span class="lbl"></span>
    </div>
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue"></h3>
        <div class="nav-search" id="nav-search">
            <form class="form-search">
            	组名:
                <span class="input-icon">
                    <select type="text" placeholder="组名 ..." class="nav-search-input" id="jobGroup0" name="jobGroup0" autocomplete="off">
                           <option value="">全部</option>
                 	</select>
                </span>&nbsp;&nbsp;
             	状态:
                <span class="input-icon">
                    <select type="text" placeholder="状态 ..." class="nav-search-input" id="status0" name="status0" autocomplete="off" >
                           <option value="">全部</option>
                 	</select>
                </span>&nbsp;&nbsp;
            	任务名:
                <span class="input-icon">
                    <input type="text" placeholder="任务名 ..." class="nav-search-input" id="jobName0" name="jobName" autocomplete="off" />
                           <i class="icon-search nav-search-icon"></i>
                </span>&nbsp;&nbsp;
            	<span class="input-icon">
                	<i class="icon-calendar" ></i>
                    <input type="text" placeholder="开始日期..."  data-date-format="yyyy-mm-dd" class="nav-search-input"
                          id="startDate" name="startDate" autocomplete="off" />
				</span>&nbsp;&nbsp;&nbsp;&nbsp;
            	<span class="input-icon">
                			<i class="icon-calendar" ></i>
                    		<input type="text" placeholder="结束日期..." data-date-format="yyyy-mm-dd" class="nav-search-input"
                           		id="endDate" name="endDate" autocomplete="off" />
				</span>
                <a href="#" onclick="initData('-');">
                    <span class="label label-lg label-pink arrowed-right">查询</span>
                </a>
            </form>
		</div>
        <div class="table-header">
                                     定时任务列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div style="float:right;padding-right: 10px;height: 38px;font-size: 14px;">
				<button class="btn btn-xs btn-success" data-toggle="modal" onclick="showAddJob();">
	                    <i class="icon-pencil bigger-80"></i>
	                                                            新增定时任务
	            </button>
        	</div>
        </div>
        <div>
            <table id="table1" class="table table-striped table-bordered table-hover"
                width="100%">
                <thead>
                <tr>
                    <th>任务组</th>
	                <th>名称</th>
	                <th>任务描述</th>
	                <th>表达式</th>
	                <th>访问链接</th>                      
	                <th>状态</th>                  
	                <th>任务更新时间</th>
	                <th>任务开始时间</th>
	                <th>远程IP</th>
	                <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>


<!-- 添加 div 点击添加按钮显示-->
<div class="modal fade" id="addJob" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="table-header">
                <font id="myModalLabel"></font>
                <span onclick="closeDiv(this);" class=" icon-ban-circle" style="float: right;margin-right: 10px;margin-top: 10px;cursor: pointer">
                </span>
            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <form class="form-horizontal" id="form" role="form" action="#" method="POST">
            	<input type="hidden" id="aId" name="aId" value="">
                <div >
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="name">组名 </label>
                        <div class="col-sm-9" >
                        
	                        <span hidden="hidden" id="selectSpan">
	                            <select type="text" placeholder="组名..." class="nav-search-input " name="jobGroup"  id="selectJobGroup" autocomplete="off">
	                           		<option value="">全部</option>
	                 			</select>
	                            <font color="red">*</font>
	                            <span id="changeButton">
	                            	<button class="btn btn-xs btn-success" type="button" id="change" onclick="jobGroupSet('input','select');" >
		                         		<i class="icon-pencil bigger-80"></i>
		                         		新建组
		                         	</button>
		                         </span>
	                        </span>
                            
                            <span id="inputSpan">
	                            <input type="text" placeholder="组名..." class="input-xlarge"
	                                id="inputJobGroup" autocomplete="off" />
	                            <span> 
                           		 <font color="red">*</font>
                           		 
		                         	<button class="btn btn-xs btn-yellow" type="button" id="change" onclick="jobGroupSet('select','input');" >
		                         		<i class="icon-fire bigger-110"></i>
		                         		选择组
		                         	</button>
	                         	</span>
                         	</span>
                         </div>                        
                    </div>
                    <div class="space-4"></div>                    
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="name">任务名 </label>

                        <div class="col-sm-9" >
                            <input type="text" placeholder="任务名..." class="input-xlarge"
                           		id="jobName" name="jobName" autocomplete="off"/>                    
                            <font color="red">*</font>
                        </div>
                    </div>                    
                    <div class="space-4"></div>                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="url">访问地址  </label>
                        <div class="col-sm-9">
                            <input type="text" name="url" id="url"   placeholder="url" class="input-xlarge" />
                            <font color="red">*</font>
                        </div>
                    </div>
                    <div class="space-4"></div>                   
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="expression"> 表达式</label>
                        <div class="col-sm-9">
                            <input type="text" name="expression" id="expression"   placeholder="expression" class="input-xlarge" />
                            <font color="red">*</font>
                            <span id="changeButton">
                            	<a href = "${ctx}/expression" target="blank_">
	                            	<button class="btn btn-xs btn-danger" type="button">
		                         		<i class="icon-bolt bigger-110"></i>
		                         		查看说明
		                         	</button>
		                         	</a>
		                         </span>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="description">任务描述</label>
                        <div class="col-sm-9">
                            <textarea class="form-control limited width-90" name="description" id="description" placeholder="description"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="description">开始时间</label>
                        <div class="col-sm-9">
                           <input size="16" type="text"  class="form_datetime" id='startTime' data-date-format="yyyy-mm-dd hh:MM:ss" >
                           <font color="red">(为空表示立刻执行)</font>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info" type="button" onclick="save();" id="submitForm">
                                <i class="icon-ok bigger-110"></i>
                                提交
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="button"  onclick="resetForm();">
                                <i class="icon-undo bigger-110"></i>
                                重置
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

    <script type="text/javascript">
        $(initData());

        function initData(url){
        	 if(url == null || url == ''){
                 $('#id').val('');
                 url = '/job/JobIntroduction/&&&&';
                 InitSelectJobGroup();
                 getQuartzStatus();
                 InitStatus();
            
             }else{
            	var startDate = Date.parse($('#startDate').val());
             	var endDate = Date.parse($('#endDate').val());
             	if(startDate>endDate){
             		bootbox.alert('开始时间不得大于结束时间！');
             		return;
             	}
                url = '/job/JobIntroduction/'+$('#status0').val()+'&'+$('#jobGroup0').val()+'&'+$('#jobName0').val()+'&'+$('#startDate').val()+'&'+$('#endDate').val();
            }
        	 url = '${ctx}/webService/get/quartz_url?path='+encodeURIComponent(url);
            var table=$("#table1").dataTable({
                "height":"600px",
                //"scrollY":"600px",
                //"scrollCollapse":   true,
                'ajax': {  
		            'url': url,  
		            'dataType': 'JSON',  
		            'type': 'GET'
		        },
                "serverSide":true,
                "processing": true,
                "destroy":true,
                "lengthMenu": [[10, 25], [10,25]],
                "aoColumnDefs": [
                    { "sWidth": "8%", "aTargets": [ 0 ] },
                    { "sWidth": "8%", "aTargets": [ 1 ] },
                    { "sWidth": "8%", "aTargets": [ 3 ]},
                    { "sWidth": "15%", "aTargets": [ 4 ] },
                    { "sWidth": "4%", "aTargets": [ 5 ] },
                    { "sWidth": "9%", "aTargets": [ 6 ] },
                    { "sWidth": "9%", "aTargets": [ 7 ] },
                    { "sWidth": "5%", "aTargets": [ 8 ] },
                    { "sWidth": "5%", "aTargets": [ 9 ] }
                ],
                "columns":[                   
                    { "data": "jobGroup"},
                    { "data": "jobName"},
                    { "data": "description"},
                    { "data": "expression"},
                    { "data": "url"},
                    {"data":null,
                        render : function(data,type,now){
                            if(data.status == 'DELETED'){
                                return "<div style='color:orangered'>删除</div>";
                            }else if(data.status=='NORMAL'){
                                return "<div style='color:green'>正常</div>";
                            }else {
                            	return "已失效";
                            }
                        }
                    },
                    {"data":"updateTime"},
                    {"data":"startTime"},
                    {"data":"remoteIp"},
                    {"data":null,
                        render : function(data, type, row){
                            var redirectBtn = "<div class='align-center'>";
                            
                            if(data.status=='NORMAL'){
                            	redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='修改' " +
                                "onclick=editJob('"+escape(data.id)+"','"+escape(data.jobGroup)+"','"+escape(data.jobName)+"','"+escape(data.url)+"','"+escape(data.expression)+"','"+escape(data.description)+"','"+escape(data.startTime)+"');><i class='icon-edit bigger-120' > </i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                redirectBtn +="<a href='#' class='red tooltip-success' data-rel='tooltip' title='失效' onclick=updateStatus('"+data.id+"','DELETED'); id='"+data.id+"'>" +
                                        "<i class='icon-trash bigger-120'> </i></a>";
                            }else{
                                redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='恢复' onclick=updateStatus('"+data.id+"','NORMAL'); id='"+data.id+"'>" +
                                        "<i class='icon-undo bigger-120'></i></a>";
                            }
                            redirectBtn += "</div>";
                            
                            return redirectBtn;
                        }
                    },
                   
                ]
            });
        }
        
        function InitSelectJobGroup() {
        	document.getElementById('jobGroup0').innerHTML="";
        	document.getElementById('selectJobGroup').innerHTML="";
            $.ajax({
                url: "${ctx}/webService/get/quartz_url?path="+encodeURIComponent("/job/getGroups"),
                type: "get",
                dataType: "JSON",
                success: function (result) {
                	if(result==null||result.status!=1||result.error!=00000000){
                		bootbox.alert(result.msg);
                 	}else{
	                    $.each(result.data, function (i) {
	                        $("#jobGroup0").append("<option value='"+result.data[i]+"'>" + result.data[i] + "</option>");
	                        $("#selectJobGroup").append("<option value='"+result.data[i]+"'>" + result.data[i] + "</option>");
	                    });
                 	}
                }
            });
            $("#jobGroup0").prepend("<option value=''>全部</option>");
            $("#selectJobGroup").prepend("<option value=''>全部</option>");
        }
        
        function InitStatus() {
            $.ajax({
                url: "${ctx}/webService/get/quartz_url?path="+encodeURIComponent("/job/getDataStatus"),
                type: "get",
                dataType: "json",
                success: function (result) {
                	if(result==null||result.status!=1||result.error!=00000000){
                		bootbox.alert(result.msg);
                	}else{
                		var option = result.data;
	                    $.each(option, function (i) {
	                    	if(option[i] == 'DELETED'){
		                        $("#status0").append("<option value='"+option[i]+"'>删除</option>");
	                    	}else if(option[i] == 'NORMAL'){
		                        $("#status0").append("<option value='"+option[i]+"'>正常</option>");
	                    	}
	                    });
                	}
                }
            });
        }
        
      	function jobGroupSet(type1,type2){
      		var span1 = type1+"Span";
      		var span2 = type2+"Span";
      		var jobGroup1 = type1+"JobGroup";
      		var jobGroup2 = type2+"JobGroup";
    	 	$("#"+span1).removeAttr("hidden");
    	 	$("#"+jobGroup1).attr("name","jobGroup");
    	 	$("#"+span2).attr("hidden","hidden");
    	 	$("#"+jobGroup2).removeAttr("name");
      	}
      	
        $("#startDate").datepicker({autoclose:true}).next().on(ace.click_event, function(){
            $(this).prev().focus();
        });
        $("#endDate").datepicker({autoclose:true}).next().on(ace.click_event, function(){
            $(this).prev().focus();
        });
       
      	$('#startTime').datetimepicker({step:30},function(){
      		$(this).attr('data-date-format',"yyyy-mm-dd hh:MM"); 
      	});
      	
        function setQuartz(){
            var quartzStatusObject = $('#quartzStatus');
            var status ;
            var flag;
            if(quartzStatusObject.attr("checked")){
                status = 0;
                flag = true;
            }else{
                status = 1;
                flag = false;
            }
            var url ;
            var msg ;
            if(status == 1){
                url = '${ctx}/webService/get/quartz_url?path='+encodeURIComponent("/quartz/startJobs");
                msg = "是否确认启动？";
            }else{
                url = '${ctx}/webService/get/quartz_url?path='+encodeURIComponent("/quartz/standbyJobs");
                msg = "是否确认暂停？";
            }
            bootbox.confirm(msg,function(result){
                if(result){
                    $.get(url,function(data){
                    	if(result==null||result.status!=1||result.error!=00000000){
                    		bootbox.alert(result.msg);
                    	}else{
                    		getQuartzStatus();
                    	}
                    },'json');
                }else{
//             		$('#quartzStatus').attr("checked",flag);
                	window.location.reload();
                }
            });
        }
        
        function getQuartzStatus(){
            var url = '${ctx}/webService/get/quartz_url?path='+encodeURIComponent("/quartz/getJobStatus");
            $.get(url,function(result){
            	if(result==null||result.status!=1||result.error!=00000000){
            		bootbox.alert(result.msg);
            	}else{
            		if(result.data=="STARTED"){
            			$('#quartzStatus').attr("checked",true);
            		}else if(result.data=="STANDBY"){
            			$('#quartzStatus').attr("checked",false);
            		}else{
            			bootbox.alert("定时任务状态异常,请联系管理员,当前状态为:"+result.data);
            		}
            	}
            },'json')
        }
    </script>
