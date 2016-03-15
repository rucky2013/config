<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="systemName" value="customer" />
<style type="text/css">
    .col-sm-12 {
        padding-left: 0px;
        padding-right: 0px;
    }

    .display-none {
        display: none;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue"></h3>
        <div class="nav-search" id="nav-search">
            <form class="form-search">
                系统:
                <span class="input-icon">
                    <select type="text" placeholder="系统" class="nav-search-input"
                            id="aSystemName" name="aSystemName" autocomplete="off">
                        <option value="">全部</option>
                    </select>
                </span>&nbsp;&nbsp;
                状态:
                <span class="input-icon">
                    <select type="text" placeholder="状态 ..." class="nav-search-input"
                            id="aStatus" name="aStatus" autocomplete="off">
                        <option value="">全部</option>
                    </select>
                </span>
                <span class="input-icon">
                    <input type="text" placeholder="IP地址" class="nav-search-input" id="aIp" name="aIp" autocomplete="off" value='' />
                    <i class="icon-search nav-search-icon"></i>
				</span> &nbsp;&nbsp;
                <a href="#" onclick="initData('-');"> <span class="label label-lg label-pink arrowed-right">搜索</span>
            </a>
            </form>

        </div>
        <div class="table-header">
            生产服务器IP列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div style="float: right; padding-right: 10px; height: 38px; font-size: 14px;">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <button class="btn btn-xs btn-success" data-toggle="modal" onclick="showAddServerIp();">
                    <i class="icon-pencil bigger-80"></i> 新增生产服务器IP
                </button>
            </div>
        </div>
        <div>
            <table id="table1" class="table table-striped table-bordered table-hover" width="100%">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>系统名称</th>
                    <th>服务器IP</th>
                    <th>添加时间</th>
                    <th>最近操作时间</th>
                    <th>状态</th>
                    <th>操作</th>
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
                <font id="myModalLabel"></font>
                <span onclick="closeDiv(this);" class=" icon-ban-circle" style="float: right;margin-right: 10px;margin-top: 10px;cursor: pointer">
                </span>
            </div>
            <div class="hr hr-18 dotted hr-double"></div>
            <form class="form-horizontal" id="form" role="form" >
                <input type="hidden" id="aId" name="aId" value="">
                <div >
                    <div class="form-group" >
                        <label class="col-sm-3 control-label no-padding-right" for="systemName"> 系统 </label>
                        <div class="col-sm-9" >
                            <select type="text" placeholder="系统..." class="nav-search-input"
                                    id="systemName" name="systemName" autocomplete="off" >
                                <option value="">全部</option>
                            </select>
                            <font color="red">*</font>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="ip"> 服務器IP </label>
                        <div class="col-sm-9">
                            <input type="text" name="ip" id="ip"   placeholder="ip" class="col-xs-10 col-sm-10" />
                            <font color="red">*</font>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info" type="button" onclick="save();">
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
            url = '${ctx}/serverIp/&&/serverIp';
            InitASystem();
            InitAStatus();
        }else{
            url = '${ctx}/serverIp/'+$("#aSystemName").val()+'&'+$("#aIp").val()+'&'+$("#aStatus").val()+'/serverIp';
        }
        var table=$("#table1").dataTable({
            "height":"600px",
            "sAjaxSource": url,
            "sServerMethod":"GET",
            "serverSide":true,
            "processing": true,
            "destroy":true,
            "lengthMenu": [[10, 25], [10,25]],
            "aoColumnDefs": [
//                { "sWidth": "20%", "aTargets": [ 2 ] },
//                { "sWidth": "30%", "aTargets": [ 3 ] }
            ],
            "columns":[
                {"data": "id"},
                {"data": "systemName"},
                {"data": "ip"},
                {"data": "createTime"},
                {"data": "updateTime"},
                {"data":null,
                    render : function(data, type, row) {
                        if(data.status == 'DELETED'){
                            return "<div style='color:orangered'>删除</div>";
                        }else if(data.status=='NORMAL'){
                            return "<div style='color:green'>正常</div>";
                        }else {
                            return "已失效";
                        }
                    }
                },
                {"data":null,
                        render : function(data, type, row){
                        var redirectBtn = "<div class='align-center'>";
                        if(data.status=='NORMAL'){
                            redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='修改' " +
                                    "onclick=editSystemIp('"+data.id+"','"+data.systemName+"','"+data.ip+"');><i class='icon-edit bigger-120' > </i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                            redirectBtn +="<a href='#' class='red tooltip-success' data-rel='tooltip' title='失效' onclick=updateStatus('"+data.id+"','"+data.systemName+"','"+data.ip+"','DELETED');>" +
                                    "<i class='icon-trash bigger-120' > </i></a>";
                        }else{
                            redirectBtn +="<a href='#' class='green tooltip-success' data-rel='tooltip' title='恢复' onclick=updateStatus('"+data.id+"','"+data.systemName+"','"+data.ip+"','NORMAL');>" +
                                    "<i class='icon-undo bigger-120' > </i></a>";
                        }
                        redirectBtn += "</div>";
                        return redirectBtn;
                    }
                }
            ]
        });
    };

    function InitAStatus() {
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
                        $("#aStatus").append("<option value='"+option[i]+"'>" + option[i] + "</option>");
                    })
                }
            }
        })
    };

    function InitASystem() {
        $.ajax({
            url: "${ctx}/serverIp/getSystemName",
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

</script>