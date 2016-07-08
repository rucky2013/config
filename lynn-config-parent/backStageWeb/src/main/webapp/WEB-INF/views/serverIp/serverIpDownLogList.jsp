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
                <input type="hidden" name="systemName" value="${systemName}" id="systemName"> 模块:
                <span class="input-icon">
                    <select type="text" placeholder="系统" class="nav-search-input" id="aSystemName" name="aSystemName" autocomplete="off">
                        <option value="">全部</option>
                    </select>
                </span> &nbsp;&nbsp;
                <span class="input-icon">
                    <input type="text" placeholder="IP地址" class="nav-search-input" id="aIp" name="aIp" autocomplete="off" value='' />
                    <i class="icon-search nav-search-icon"></i>
                </span>
                <a href="#" onclick="initData('-');">
                    <span class="label label-lg label-pink arrowed-right">查询</span>
                </a>
            </form>

        </div>
        <div class="table-header">
            生产服务器IP列表
        </div>
        <div>
            <table id="table1" class="table table-striped table-bordered table-hover" width="100%">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>系统名称</th>
                    <th>服务器IP</th>
                    <th>记录时间</th>
                    <th>记录宕机的服务IP</th>
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
            $('#phone').val('');
            url = '${ctx}/down/&/getSystemServerIpDownLog';
            InitASystem();
        }else{
            url = '${ctx}/down/'+$("#aSystemName").val()+'&'+$("#aIp").val()+'/getSystemServerIpDownLog';
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
                { "sWidth": "20%", "aTargets": [ 2 ] },
                { "sWidth": "30%", "aTargets": [ 3 ] }
            ],
            "columns":[
                {"data": "id"},
                { "data": "systemName"},
                {"data": "ip"},
                {"data": "createTime"},
                {"data":"serverIp"}
            ]
        });
    }

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
                    })
                }
            }
        })
    }

</script>