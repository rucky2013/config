serverIp ={};
serverIp.ip;
serverIp.systemName;
function showAddServerIp() {
    $('#myModalLabel').text('新增应用服务器IP');
    $('#aId').val("");
    serverIp.systemName='';
    serverIp.ip='';
    $('#systemName').val(serverIp.systemName).attr('disabled', false);
    $('#ip').val(serverIp.ip);
    $("#addSys").modal("show");
}

function save(){
    var url = "addOrUpdateSystemIp";
    var systemName = $('#systemName');
    var ip = $('#ip');
    var id = $('#aId');
    if ($.trim(systemName.val()) == '') {
        bootbox.alert('请选择系统！');
        return;
    }
    if ($.trim(ip.val()) == '') {
        bootbox.alert('IP不得为空！');
        return;
    }
    $.post(url, {
        'id' : id.val(),
        'systemName' : systemName.val(),
        'ip' : ip.val()
    }, function(result) {
        var data = result.data;
        if (data.resultStatus == "1") {
            bootbox.alert(result.msg,function(){
                window.location.reload();
            });
        }else{
            bootbox.alert(result.msg);
        }
    }, 'json');
}
function editSystemIp(id, systemName, ip) {
    $('#myModalLabel').text('修改应用服务器IP');
    $('#aId').val(id);
    //$('#systemName').val(systemName);
    //$('#ip').val(ip);
    serverIp.systemName=systemName;
    serverIp.ip=ip;
    $('#systemName').val(serverIp.systemName).attr('disabled', true);
    $('#ip').val(serverIp.ip);
    $("#addSys").modal("show");
}
function updateStatus(id, systemName,ip,status) {
    var flag;
    if (status == 'DELETED') {
        flag = '删除';
    } else if (status == 'NORMAL') {
        flag = '还原'
    }
    bootbox.confirm("您确定要" + flag + systemName +"的服务IP:"+ip+"吗?", function(data) {
        if (data) {
            $("#"+id).attr("class","icon-spinner icon-spin orange bigger-130");
            $.post("addOrUpdateSystemIp", {
                'id' : id,
                'status' : status,
            }, function(data) {
                if (data.data.resultStatus == "1") {
                    bootbox.alert(data.msg,function(){
                        //window.location.reload();
                        var tab = $("#table1").DataTable();
                        var currentPage = tab.page.info().page;
                        tab.page(currentPage).draw(false);
                        $('#addSys').modal('hide');
                    });
                }else{
                    bootbox.alert(data.msg);
                }
            }, 'json');
        }
    });
}
function resetForm() {
    $('#systemName').val(serverIp.systemName);
    $('#ip').val(serverIp.ip);
}
