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
</style>

    <div class="row col-xs-12">
            <h3 class="header smaller lighter blue"></h3>
            <div class="nav-search" id="nav-search">

                <form class="form-search">
                    <span class="input-icon">
                    系统:&nbsp;&nbsp;<select type="text" placeholder="系统名..." class="nav-search-input"
                           id="systemName" name="systemName" autocomplete="off" onchange="changeSystem()">
                           
                 	</select>
                </span>&nbsp;&nbsp;
       <span class="input-icon">
                           动静态标识:&nbsp;&nbsp;<select type="text" placeholder="动静态标识..."
                               class="nav-search-input" id="flag0" name="flag0" autocomplete="off" onchange="changeFlag()"/>
                       	</select>
                       	
                    </span>&nbsp;&nbsp;
                <span class="input-icon">
                           状态:&nbsp;&nbsp;<select type="text" placeholder="状态 ..."
                               class="nav-search-input" id="status0" name="status0" autocomplete="off" onchange="changeStatus()"/>
                        	<option value="">全部</option>
                       	</select>
                       	
                    </span>
                    <a href="#" onclick="initData('-');">
                        <span class="label label-lg label-pink arrowed-right">搜索</span>
                    </a>
                </form>

            </div>
            <div class="table-header">
                系统模块列表
                <div style="float:right;padding-right: 10px;height: 38px;font-size: 14px;">
                <button class="btn btn-xs btn-success" data-toggle="modal" onclick="showAddSystem();">
                        <i class="icon-pencil bigger-80"></i>
                        新增系统
                    </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                    <%--<button class="btn btn-danger"onclick="${ctx}}/system/refreshCache" >刷新服务器缓存</button>--%>
                    <button class="btn btn-xs btn-success" data-toggle="modal" onclick="showAddSystemModule();">
                        <i class="icon-pencil bigger-80"></i>
                        新增模块
                    </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                    
                </div>
            </div>

            <table id="table1" class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>系统名称</th>
                        <th>模块名称</th>
                        <th>模块备注</th>
                        <th>动静态标识</th>
                        <th>状态</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
            </table>
    </div><!-- /.row -->


<!-- 添加 div 点击添加按钮显示     添加模块-->
<div class="modal fade" id="addSysMod" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="table-header">
                <font id="myModalLabel">添加模块</font>
                <span onclick="closeDiv(this);" class=" icon-ban-circle" style="float: right;margin-right: 10px;margin-top: 10px;cursor: pointer">
                </span>
            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <form class="form-horizontal" id="form" role="form" action="${ctx}/systemModule/addSystemModule" method="POST">
                <div >
                	<div class="form-group" style="display: none">
                        <label class="col-sm-3 control-label no-padding-right" for="id"> 模块ID </label>

                        <div class="col-sm-9">
                            <input type="text" name="id" id="id"   placeholder="id" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="parentModuleName"> 系统名称 </label>

                        <div class="col-sm-9" >
                            <select type="text" placeholder="" class="col-xs-10 col-sm-10"
                           id="parentModuleName" name="parentModuleName" autocomplete="off" >
                  			</select>
                            <font color="red">*</font>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="moduleName"> 模块名称 </label>

                        <div class="col-sm-9" >
                            <input type="text" name="moduleName" id="moduleName" placeholder="moduleName" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="moduleRemark">模块备注</label>

                        <div class="col-sm-9">
                            <textarea class="form-control limited width-90" name="moduleRemark" id="moduleRemark" placeholder="moduleRemark"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="flag">动静态标识</label>

                        <div class="col-sm-9" >
                            <select type="text" placeholder="" class="col-xs-10 col-sm-10"
                           id="flag" name="flag" autocomplete="off" >
                           <option value="">ALL</option>
                  			</select>
                        </div>
                    </div>
                   
					
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9" id="oprate" name="oprate">
                        
                            <button class="btn btn-info" type="button" onclick="save();" id="submitForm">
                                <i class="icon-ok bigger-110"></i>
                                提交
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="reset" id="reset">
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






<!-- 点击添加系统 -->
<div class="modal fade" id="addSys" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="table-header">
                <font id="myModalLabel">新增系统</font>
                <span onclick="closeDiv(this);" class=" icon-ban-circle" style="float: right;margin-right: 10px;margin-top: 10px;cursor: pointer">
                </span>
            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <form class="form-horizontal" id="form" role="form" action="${ctx}/systemModule/addSystemModule" method="POST">
                <div >
                	<div class="form-group"style="display: none">
                        <label class="col-sm-3 control-label no-padding-right" for="id1" > 系统ID </label>

                        <div class="col-sm-9">
                            <input type="text" name="id1" id="id1"   placeholder="id" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>
                    
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="moduleName1"> 系统名称 </label>

                        <div class="col-sm-9" >
                            <input type="text" name="moduleName1" id="moduleName1" placeholder="moduleName" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    

                    <div class="space-4"></div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="moduleRemark1">系统备注</label>

                        <div class="col-sm-9">
                            <textarea class="form-control limited width-90" name="moduleRemark1" id="moduleRemark1" placeholder="moduleRemark"></textarea>
                        </div>
                    </div>

                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="flag1">动静态标识</label>

                        <div class="col-sm-9" >
                            <select type="text" placeholder="" class="col-xs-10 col-sm-10"
                           id="flag1" name="flag1" autocomplete="off" >
                  			</select>
                            <font color="red">*</font>
                        </div>
                    </div>
                    

                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info" type="button" onclick="save();" id="submitForm">
                                <i class="icon-ok bigger-110"></i>
                                提交
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="reset" id="reset">
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
                url = '${ctx}/systemModule/getSysModule';
                InitSelectSystemName();
                InitSelectFlag();
                InitSelectStatus();
           
            }else{
                url = '${ctx}/systemModule/getSysModule?parentModuleName='+$('#systemName').val()+'&status='+$('#status0').val()+'&flag='+$('#flag0').val();
            }
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
                    { "sWidth": "5%", "aTargets": [ 3 ]},
                    { "sWidth": "5%", "aTargets": [ 4 ] }
                ],
                "columns":[
                           
                    { "data": "parentModuleName"},
                    { "data": "moduleName"},
                    { "data": "moduleRemark"},
                    {"data":"flag"},
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
                    {"data":null,
                        render : function(data, type, row){
                            var redirectBtn = "<div class='align-center'>";
                            
                            //redirectBtn +="<a href='#' class='red tooltip-success' data-rel='tooltip' title='删除' onclick=deleteIp('"+data.id+"');><i class='icon-trash bigger-120' > </i></a>";
                            if(data.status=='NORMAL'){
                            	redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='修改' " +
                                "onclick=editSys('"+data.id+"','"+data.parentModuleName+"','"+data.moduleName+"','"+data.moduleRemark+"','"+data.flag+"','"+data.status+"');><i class='icon-edit bigger-120' > </i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                redirectBtn +="<a href='#' class='red tooltip-success' data-rel='tooltip' title='失效' onclick=updateStatus('"+data.id+"','DELETED','"+data.parentModuleName+"','"+data.moduleName+"'); id='"+data.id+"'>" +
                                        "<i class='icon-trash bigger-120'> </i></a>";
                            }else{
                                redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='恢复' onclick=updateStatus('"+data.id+"','NORMAL','"+data.parentModuleName+"','"+data.moduleName+"'); id='"+data.id+"'>" +
                                        "<i class='icon-undo bigger-120'></i></a>";
                            }
                            redirectBtn += "</div>";
                            
                            return redirectBtn;
                        }
                    },
                   
                ]
            });
        }
        
        function InitSelectSystemName() {
        	document.getElementById('systemName').innerHTML="";
        	document.getElementById('parentModuleName').innerHTML="";
          	$("#moduleName0").attr('disabled', true);
            $.ajax({
                url: "${ctx}/systemModule/getSystemName",
                type: "get",
                dataType: "json",
                success: function (result) {
                    $.each(result, function (i) {
                        $("#systemName").append("<option value='"+result[i]+"'>" + result[i] + "</option>");
                        $("#parentModuleName").append("<option value='"+result[i]+"'>" + result[i] + "</option>");
                    });
                }
            });
            $("#systemName").prepend("<option value=''>全部</option>");
        }
        
        function InitSelectFlag() {
        	document.getElementById('flag0').innerHTML="";
        	document.getElementById('flag').innerHTML="";
        	document.getElementById('flag1').innerHTML="";
        	$("#flag1").prepend("<option value='ALL'>ALL</option>");
        	$("#flag0").prepend("<option value=''>全部</option>");
            $.ajax({
                url: "${ctx}/systemModule/getFlagName",
                type: "get",
                dataType: "json",
                success: function (result) {
                    $.each(result, function (i) {
                        $("#flag0").append("<option value='"+result[i]+"'>" + result[i] + "</option>");
                        $("#flag").append("<option value='"+result[i]+"'>" + result[i] + "</option>");
                        $("#flag1").append("<option value='"+result[i]+"'>" + result[i] + "</option>");
                    });
                }
            });
            
        }
		
  
        
        function InitSelectStatus(){
        	$.ajax({
                url: "${ctx}/systemModule/getStatusName",
                type: "get",
                dataType: "json",
                success: function (result) {
                    $.each(result, function (i) {
                    	if(result[i] == 'NORMAL'){
                        	$("#status0").append("<option value='"+result[i]+"'>" + "正常</option>");
                    	}else{
                    		$("#status0").append("<option value='"+result[i]+"'>" + "删除</option>");
                    	}
                    });
                }
            });
        }
        
        function changeSystem(){
        	document.getElementById('moduleName0').innerHTML="";
        	InitSelectFlag();
        	InitSelectStatus();
        	//initData('-');
        }
      
        function changeStatus(){
        	//initData('-');
        }
    </script>