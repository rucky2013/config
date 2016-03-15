/**
 * 
 */
function InitAModule() {
	var aSystemName = $("#aSystemName").val();
	$("#aModuleName").html("<option value=''>全部</option>").attr('disabled', true);
	if (aSystemName != "") {
		$.ajax({
			url : "../systemModule/allSonModuleName/" + aSystemName + "/ACTIVE",
			type : "get",
			dataType : "json",
			success : function(result) {
				if (result == null || result.status != 1 || result.status == null || result.data.resultStatus != 1) {
					alert(result.msg);
				} else {
					$("#aModuleName").attr('disabled', false);
					var option = result.data.value;
					$.each(option, function(i) {
						$("#aModuleName").append("<option value='" + option[i] + "'>" + option[i] + "</option>");
					});
				}
			}
		});
	}

}

function InitModule(moduleName, operation) {
	var systemName = $("#systemName").val();
	$("#moduleName").html("<option value=''>全部</option>").attr('disabled', true);
	if (systemName != "") {
		$.ajax({
			url : "../systemModule/allSonModuleName/" + systemName + "/ACTIVE",
			type : "get",
			dataType : "json",
			success : function(result) {
				if (result == null || result.status != 1 || result.status == null || result.data.resultStatus != 1) {
					alert(result.msg);
				} else {
					var option = result.data.value;
					$.each(option, function(i) {
						$("#moduleName").append("<option value='" + option[i] + "'>" + option[i] + "</option>");
					});
					$('#moduleName').val(moduleName);
					if (operation == 'modify') {
						$('#moduleName').attr('disabled', true);
					} else {
						$('#moduleName').attr('disabled', false);
					}
				}
			}
		});
	}
}

function editSys(id, systemName, moduleName, status, nid, name, value, remark) {
	$('#myModalLabel').text('修改动态系统变量');
	$('#aId').val(unescape(id));
	$('#systemName').val(unescape(systemName)).attr('disabled', true);
	InitModule(unescape(moduleName), 'modify');
	$('#Nid').val(unescape(nid)).attr('disabled', true);
	$('#name').val(unescape(name));
	$('#value').val(unescape(value));
	$('#remark').val(unescape(remark));
	$("#addSys").modal("show");
}

function showAddSys() {
	var aSystemName = $("#aSystemName").val();
	var aModuleName = $("#aModuleName").val();

	$('#myModalLabel').text('新增动态系统变量');
	$('#aId').val("");
	$('#systemName').val(aSystemName).attr('disabled', false);
	InitModule(aModuleName, 'other');
	$('#Nid').val("").attr('disabled', false);
	$('#name').val("");
	$('#value').val("");
	$('#reamrk').val("");
	$("#addSys").modal("show");
}

function clear() {
	$('#aId').val("");
	$('#systemName').val("").attr('disabled', false);
	InitModule('', 'other');
	$('#Nid').val("").attr('disabled', false);
	$('#status').val("NORMAL");
	$('#name').val("");
	$('#value').val("");
	$('#reamrk').val("");
}

function save() {
	 $('#submitForm').html("<i class='icon-spinner icon-spin orange bigger-120'></i>等待");
	 $("#submitForm").attr("disabled", true);
	var url = "addOrUpdateSystemVariable/active";
	var id = $('#aId');
	var systemName = $('#systemName');
	var moduleName = $('#moduleName');
	var name = $('#name');
	var nid = $('#Nid');
	var value = $('#value');
	var remark = $('#remark');

	if ($.trim(systemName.val()) == '') {
		bootbox.alert('系统不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(moduleName.val()) == '') {
		bootbox.alert('模块不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(name.val()) == '') {
		name.focus();
		bootbox.alert('变量名不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(nid.val()) == '') {
		nid.focus();
		bootbox.alert('变量NID不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(value.val()) == '') {
		value.focus();
		bootbox.alert('变量值不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}
	$.post(url, {
		'id' : id.val(),
		'systemName' : systemName.val(),
		'moduleName' : moduleName.val(),
		'name' : name.val(),
		'nid' : nid.val(),
		'value' : value.val(),
		'remark' : remark.val()
	}, function(data) {
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		if (data.data.resultStatus == "1") {
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
	}, 'json');
}

function updateStatus(id, status) {
	var flag;
	var cl;
	if (status == 'DELETED') {
		flag = '删除';
		cl = "icon-trash bigger-120";
	} else if (status == 'NORMAL') {
		flag = '还原'
		cl = "icon-undo bigger-120";
	}
	bootbox.confirm("您确定要" + flag + "这个变量吗?", function(data) {
		if (data) {
			$("#" + id).html("<i class='icon-spinner icon-spin orange bigger-120'></i>");
			$("#" + id).attr("onclick", "null");
			$("#" + id).attr("title", "等待中..");
			$.post("addOrUpdateSystemVariable/active", {
				'id' : id,
				'status' : status,
			}, function(data) {
				if (data.data.resultStatus == "1") {
					bootbox.alert(data.msg, function() {
						//window.location.reload();
						var tab = $("#table1").DataTable();
						var currentPage = tab.page.info().page;
						tab.page(currentPage).draw(false);
						$('#addSys').modal('hide');
					});
				} else {
					$("#" + id).html("<i class='"+cl+"'></i>");
					$("#" + id).attr("onclick", "updateStatus('" + id + "','" + status + "');");
					$("#" + id).attr("title", flag);
					bootbox.alert(data.msg);
				}
			}, 'json');
		}
	});
}

function resetForm() {

}
