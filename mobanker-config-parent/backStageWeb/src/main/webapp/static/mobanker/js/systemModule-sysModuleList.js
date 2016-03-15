var editFlag = false;
var addFlag = null;
function editSys(id,parentModuleName,moduleName,moduleRemark,flag,status){
    editFlag = true;
    $('#myModalLabel').text('修改模块');
    $('#id').val(id).attr('disabled',true);
    $('#parentModuleName').val(parentModuleName).attr('disabled',true);
    $('#moduleName').val(moduleName).attr('disabled',true);
    $('#moduleRemark').val(moduleRemark);
    $('#flag').val(flag).attr('disabled',true);
    $("#addSysMod").modal("show");
    $("#reset").attr("disabled",true);
}

function showAddSystemModule(){
    editFlag = false;
    addFlag = "addSysModule";
    $('#myModalLabel').text('新增模块');
    $('#id').val("").attr('disabled',false);   
    $('#parentModuleName').val("").attr('disabled',false);
    $('#moduleName').val("").attr('disabled',false);
    $('#moduleRemark').val("").attr('disabled',false);
    $('#flag').val("").attr('disabled',false);
    $('#status').val("NORMAL").attr('disabled',true);
    $("#addSysMod").modal("show");
}

function showAddSystem(){
    editFlag = false;
    addFlag = "addSys";
    $('#myModalLabel').text('新增系统');
    $('#id1').val("").attr('disabled',false);
    $('#moduleName1').val("").attr('disabled',false);
    $('#moduleRemark1').val("").attr('disabled',false);
    $('#flag1').val("").attr('disabled',false);
    $('#status1').val("NORMAL").attr('disabled',true);
    $("#addSys").modal("show");
}

function refreshCache(){
    $.get('refreshCache',function(data){
        bootbox.alert(data.msg);
    },'json');
}

function clear(){
    $('#id').val("").attr('disabled',false);
    $('#moduleName').val("");
    $('#moduleRemark').val("");
    $('#flag').val("");
    $('#status').val("");
    editFlag = false;
}

function save(){
	$('#submitForm').html("<i class='icon-spinner icon-spin orange bigger-120'></i>等待");
	 $("#submitForm").attr("disabled", true);
    var url = "addOrUpdateSystemModule";
    if(editFlag){
        url = "addOrUpdateSystemModule";
    }
    var id;
    var parentModuleName= $('#parentModuleName');
    var moduleName;
    var moduleRemark;
    var flag;
    var status;
    if(addFlag == "addSys"){
    	id = $('#id1');
    	moduleName = $('#moduleName1');
    	moduleRemark = $('#moduleRemark1');
    	flag = $('#flag1');
    	status = $('#status1');
    	
        
        if($.trim(moduleName.val()) == ''  ){
            moduleName.focus();
            bootbox.alert('系统名称不得为空！');
            $('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
            return;
        } 
        if( $.trim(moduleRemark.val()) == ''  ){
            moduleRemark.focus();
            bootbox.alert('系统备注不得为空！');
            $('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
            return;
        }
        if( $.trim(flag.val()) == ''  ){
            flag.focus();
            bootbox.alert('系统动静态标识不得为空！');
            $('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
            return;
        }
        $.post(
                url,
                {
                	'id':id.val(),
                	'moduleName':moduleName.val(),
                    'moduleRemark':moduleRemark.val(),
                    'flag':flag.val(),
                    'status':status.val()
                },
                function(data){
                	$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
            		$("#submitForm").attr("disabled", false);
            		InitSelectSystemName();
                	if (data.data.result == "1") {
            			bootbox.alert(data.msg, function() {
            				//window.location.reload();
                            var tab = $("#table1").DataTable();
                            var currentPage = tab.page.info().page;
                            tab.page(currentPage).draw(false);
                            $('#addSys').modal('hide');
            			});
            		} else {
            			bootbox.alert(data.msg);
            		}
                },'json'
            );
    
    }else{
    	id = $('#id');    	
    	moduleName = $('#moduleName');
    	moduleRemark = $('#moduleRemark');
    	flag = $('#flag');
    	status = $('#status');

    	if( $.trim(parentModuleName.val()) == ''  ){
    		parentModuleName.focus();
    		bootbox.alert('系统名不得为空！');
    		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
    		return;
    	}
        
        if($.trim(moduleName.val()) == ''  ){
            moduleName.focus();
            bootbox.alert('模块名称不得为空！');
            $('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
            return;
        } 
        if( $.trim(moduleRemark.val()) == ''  ){
            moduleRemark.focus();
            bootbox.alert('模块备注不得为空！');
            $('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
            return;
        }
        if( $.trim(flag.val()) == ''  ){
            flag.focus();
            bootbox.alert('模块动静态标识不得为空！');
            $('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
    		$("#submitForm").attr("disabled", false);
            return;
        }
        $.post(
                url,
                {
                	'id':id.val(),
                	'parentModuleName':parentModuleName.val(),
                	'moduleName':moduleName.val(),
                    'moduleRemark':moduleRemark.val(),
                    'flag':flag.val(),
                    'status':status.val()
                },
                function(data){
                	$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
            		$("#submitForm").attr("disabled", false);
                	if (data.data.result == "1") {
            			bootbox.alert(data.msg, function() {
            				//window.location.reload();
                            var tab = $("#table1").DataTable();
                            var currentPage = tab.page.info().page;
                            tab.page(currentPage).draw(false);
                            $('#addSysMod').modal('hide');
            			});
            		} else {
            			bootbox.alert(data.msg);
            		}
                },'json'
            );
    }
  
    

    
}

function updateStatus(id, status,parentModuleName,moduleName) {
	var flag;
	var cl;
	if (status == 'DELETED') {
		flag = '删除';
		cl = "icon-trash bigger-120";
	} else if (status == 'NORMAL') {
		flag = '还原';
		cl = "icon-undo bigger-120";
	}
	bootbox.confirm("您确定要" + flag + "这个模块吗?", function(data) {
		if (data) {
			$("#" + id).html("<i class='icon-spinner icon-spin orange bigger-120'></i>");
			$("#" + id).attr("onclick", "null");
			$("#" + id).attr("title", "等待中..");

			$.post("addOrUpdateSystemModule", {
				'id' : id,
				'status' : status,
				'parentModuleName' : parentModuleName,
				'moduleName' : moduleName,
			}, function(data) {
				if (data.data.result == "1") {
					bootbox.alert(data.msg, function() {
						//window.location.reload();
                        var tab = $("#table1").DataTable();
                        var currentPage = tab.page.info().page;
                        tab.page(currentPage).draw(false);
                        $('#addSysMod').modal('hide');
					});
				} else {
					$("#" + id).html("<i class='"+cl+"'></i>");
					$("#" + id).attr("onclick", "updateStatus('" + id + "','" + status + "','"+parentModuleName+"','"+moduleName+"');");
					$("#" + id).attr("title", flag);
					bootbox.alert(data.msg);
				}
			}, 'json');
		}
	});
}