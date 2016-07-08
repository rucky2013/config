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
            	系统:
                <span class="input-icon">
                    <select type="text" placeholder="系统 ..." class="nav-search-input"
                           id="aSystemName" name="aSystemName" autocomplete="off" onchange="changeASystemName();">
                           <option value="">全部</option>
                 	</select>
                </span>
            	&nbsp;&nbsp;模块:
                <span class="input-icon">
                    <select type="text" placeholder="模块 ..." class="nav-search-input"
                           id="aModuleName" name="aModuleName" autocomplete="off"  disabled="true">
                           <option value="">全部</option>
                 	</select>
                </span>
                &nbsp;&nbsp;状态:
                <span class="input-icon">
                    <select type="text" placeholder="状态 ..." class="nav-search-input"
                           id="aStatus" name="aStatus" autocomplete="off" >
                           <option value="">全部</option>
                 	</select>
                </span>
                &nbsp;&nbsp;
                <span class="input-icon">
                    <input type="text" placeholder="NID ..." class="nav-search-input"
                           id="aNid" name="aNid" autocomplete="off" value='' />
                    <i class="icon-search nav-search-icon"></i>
                </span>
                &nbsp;&nbsp;
                <span class="input-icon">
                    <input type="text" placeholder="变量名..." class="nav-search-input"
                           id="aName" name="aName" autocomplete="off" value='' />
                    <i class="icon-search nav-search-icon"></i>
                </span>
                <a href="#" onclick="initData('-');">
                    <span class="label label-lg label-pink arrowed-right">查询</span>
                </a>
            </form>

        </div>
        <div class="table-header">
            	静态系统变量列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div style="float:right;padding-right: 10px;height: 38px;font-size: 14px;">

                <button class="btn btn-xs " onclick="pushDataToZk(this)">
                    <i class=" bigger-110"></i>
                    推送静态配置变量到ZOOKEEPER
                    <i class="icon-arrow-right icon-on-right"></i>
                </button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <%--<button class="btn btn-xs btn-yellow" onclick="exportZK()">--%>
                    <%--<i class="icon-fire bigger-110"></i>--%>
                    <%--导出ZOOKEEPER配置--%>
                    <%--<i class="icon-arrow-right icon-on-right"></i>--%>
                <%--</button>--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <button class="btn btn-xs btn-success" data-toggle="modal" onclick="showAddSys();">
                    <i class="icon-pencil bigger-80"></i>
                    新增静态系统变量
                </button>
            </div>
        </div>
        <div >
            <table id="table1" class="table table-striped table-bordered table-hover"
                width="100%">
                <thead>
                <tr>
                    <th>系统</th>
                    <th>模块</th>
                    <th>NID</th>
                    <th>变量名</th>
                    <th>变量值</th>
                    <th>说明</th>
                    <th>状态</th>
                    <th>更新时间</th>
                    <th>编辑</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<!-- 添加 div 点击添加按钮显示-->
<div class="modal fade" id="addSys" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="table-header">
                <font id="myModalLabel">添加静态系统变量</font>
                <span onclick="closeDiv(this);" class=" icon-ban-circle" style="float: right;margin-right: 10px;margin-top: 10px;cursor: pointer">
                </span>
            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <form class="form-horizontal" id="form" role="form" action="${ctx}/system/addSysVar" method="POST">
            	<input type="hidden" id="aId" name="aId" value="">
                <div >

                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="name"> 系统 </label>

                        <div class="col-sm-9" >
                            <select type="text" placeholder="系统 ..." class="nav-search-input"
                           		id="systemName" name="systemName" autocomplete="off" onchange="InitModule('','other');">
                           <option value="">全部</option>
                 	</select>
                            <font color="red">*</font>
                        </div>
                    </div>
                    
                    <div class="space-4"></div>
                    
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="name"> 模块 </label>

                        <div class="col-sm-9" >
                            <select type="text" placeholder="模块 ..." class="nav-search-input"
                           		id="moduleName" name="moduleName" autocomplete="off">
                           <option value="">全部</option>
                 	</select>
                            <font color="red">*</font>
                        </div>
                    </div>
                    
                    <div class="space-4"></div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="Nid"> 变量NID </label>

                        <div class="col-sm-9">
                            <input type="text" name="Nid" id="Nid"   placeholder="nid" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="Nid"> 变量名</label>

                        <div class="col-sm-9">
                            <input type="text" name="name" id="name"   placeholder="name" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="value">变量值</label>

                        <div class="col-sm-9">
                            <textarea class="form-control limited width-90" name="value" id="value" placeholder="value"></textarea>
                            <font color="red">*</font>
                        </div>
                    </div>
                    
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="remark">说明</label>

                        <div class="col-sm-9">
                            <textarea class="form-control limited width-90" name="remark" id="remark" placeholder="remark"></textarea>
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
                url = '${ctx}/systemVariable/&&&&/static';
                InitStatus();
                InitSystem();
            }else{
                url = '${ctx}/systemVariable/'+$("#aSystemName").val()+'&'+$("#aModuleName").val()+'&'+
                $("#aNid").val()+'&'+$("#aName").val()+'&'+$("#aStatus").val()+'/static';
            }
            var table=$("#table1").dataTable({
                "height":"600px",
                //"scrollY":"600px",
                //"scrollCollapse":   true,
                "sAjaxSource": url,
//                "bStateSave": true,
                "sServerMethod":"GET",
                "serverSide":true,
                "processing": true,
                "destroy":true,
                "lengthMenu": [[10, 25], [10,25]],
                "aoColumnDefs": [
                    { "sWidth": "5%", "aTargets": [ 0 ] }
                ],
                "columns":[
                           {"data": "systemName"},
                           {"data": "moduleName"},
                           { "data": "nid"},
                           {"data":"name"},
                           {"data": "value"},
                           {"data": "remark"},
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
                                   if(data.status=='NORMAL'){
                                	   //正常状态允许修改
	                                   redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='修改' onclick=editSys('"+escape(data.id)+"','"+escape(data.systemName)+"','"+escape(data.moduleName)+"','"+escape(data.status)+"','"+escape(data.nid)+"','"+escape(data.name)+"','"+escape(data.value)+"','"+escape(data.remark)+"');>"+
	                                   				"<i class='icon-edit bigger-120' > </i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                       redirectBtn +="<a href='#' class='red tooltip-success' data-rel='tooltip' title='失效' onclick=updateStatus('"+data.id+"','DELETED'); id='"+data.id+"'>" +
                                               "<i class='icon-trash bigger-120'> </i></a>";
                                   }else{
                                	   //删除状态不允许修改
                                       redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='恢复' onclick=updateStatus('"+data.id+"','NORMAL'); id='"+data.id+"'>" +
                                               "<i class='icon-undo bigger-120'></i></a>";
                                   }
                                   redirectBtn += "</div>";
                                   return redirectBtn;
                               }
                           }
                  ]
            });
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
	                        $("#aSystemName").append("<option value='"+option[i]+"'>" + option[i] + "</option>");
	                        $("#systemName").append("<option value='"+option[i]+"'>" + option[i] + "</option>");
	                    })
                	}
                }
            })
        }
        
        function changeASystemName() {
        	 InitAModule();
//         	 initData('-');
        }
        
        <%--function exportZK(){--%>
        	<%--var aSystemName = $("#aSystemName").val();--%>
        	<%--if ($.trim(aSystemName) == '') {--%>
        		<%--bootbox.alert('请选择您要导出的系统');--%>
        	<%--}else{--%>
        		<%--location.href="${ctx}/zookeeper/exportProps/"+aSystemName;--%>
        	<%--}--%>
        <%--}--%>

        function pushDataToZk(src){
            $(src).attr('disabled',true);
            var aSystemName = $("#aSystemName").val();
            var aModuleName = $("#aModuleName").val();
            var url = "${ctx}/zookeeper/pushStaticToZk";
            var dataJson = {
                'systemName' : aSystemName,'moduleName' : aModuleName
            };
            $.get(
                url,
                dataJson,
                function(data){
                    $(src).removeAttr('disabled');
                    bootbox.alert(data.msg);
                },'json'
            )

        }

    </script>