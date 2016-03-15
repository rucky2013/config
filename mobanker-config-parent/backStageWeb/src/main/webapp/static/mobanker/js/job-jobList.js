
function editJob(id, jobGroup, jobName, url, expression, description, startTime) {
	//重置显示
 	$("#selectSpan").removeAttr("hidden");
 	$("#selectJobGroup").attr("name","jobGroup");
 	$("#inputSpan").attr("hidden","hidden");
 	$("#inputJobGroup").removeAttr("name");
 	//隐藏新建组
 	$("#changeButton").attr("hidden","hidden");
 	//数据填充
	$('#myModalLabel').text('修改定时任务');
	$('#aId').val(unescape(id));
	$('#selectJobGroup').val(unescape(jobGroup)).attr('disabled', true);
	$('#jobName').val(unescape(jobName)).attr('disabled', true);
	$('#url').val(unescape(url));
	$('#expression').val(unescape(expression));
	$('#description').val(unescape(description));
	$('#startTime').val(unescape(startTime));
	$("#addJob").modal("show");
}

function showAddJob() {
	//页面重置
	$('#myModalLabel').text('新增定时任务');
 	$("#selectSpan").removeAttr("hidden");
 	$("#selectJobGroup").attr("name","jobGroup");
 	$("#inputSpan").attr("hidden","hidden");
 	$("#inputJobGroup").removeAttr("name");
 	//还原新建组
 	$("#changeButton").removeAttr("hidden");
 	//数据清除
	$('#aId').val("");
	$('#selectJobGroup').val($("#jobGroup0").val()).attr('disabled', false);
	$('#jobName').val("").attr('disabled', false);
	$('#url').val("");
	$('#expression').val("");
	$('#description').val("");
	$('#startTime').val("");
	$("#addJob").modal("show");
}

function clear() {
 	//还原新建组
 	$("#changeButton").removeAttr("hidden");
	$('#aid').val("");
	$('#jobGroup').val("").attr('disabled', false);
	$('#jobName').val("").attr('disabled', false);
	$('#url').val("");
	$('#expression').val("");
	$('#description').val("");
	$('#startTime').val("");
}

function save() {
	 $('#submitForm').html("<i class='icon-spinner icon-spin orange bigger-120'></i>等待");
	 $("#submitForm").attr("disabled", true);
	var url = "../webService/post/quartz_url?path="+encodeURIComponent("/quartz/addOrUpdateJob");
	var id = $('#aId');
	var jobGroup = $('[name="jobGroup"]');
	var jobName = $('#jobName');
	var url0 = $('#url');
	var expression = $('#expression');
	var description = $('#description');
	var startTime = $('#startTime');

	if ($.trim(jobGroup.val()) == '') {
		bootbox.alert('组名不能为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(jobName.val()) == '') {
		bootbox.alert('任务名不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(url0.val()) == '') {
		url0.focus();
		bootbox.alert('访问地址不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	if ($.trim(expression.val()) == '') {
		expression.focus();
		bootbox.alert('任务表达式不得为空！');
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		return;
	}

	var msg = "修改定时任务成功";
	if ($.trim(id.val()) == '') {
		msg = "新增定时任务成功";
	}

	$.post(url, {
		'id' : id.val(),
		'jobGroup' : jobGroup.val(),
		'jobName' : jobName.val(),
		'url' : url0.val(),
		'expression' : expression.val(),
		'description' : description.val(),
		'startTime' : startTime.val()
	}, function(data) {
		$('#submitForm').html("<i class='icon-ok bigger-110'></i>提交");
		$("#submitForm").attr("disabled", false);
		if (data.status == 1&&data.error==00000000) {
			bootbox.alert(msg, function() {
				//新加组,需要显示到下拉框上
				InitSelectJobGroup();
				var tab = $("#table1").DataTable();
				var currentPage = tab.page.info().page;
				tab.page(currentPage).draw(false);
				$('#addJob').modal('hide');
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

			$.post("../webService/post/quartz_url", {
				'path':'/quartz/updateStatus',
				'id' : id,
				'status' : status,
			}, function(data) {
				if (data.status == 1&&data.error==00000000) {
					bootbox.alert(flag+"成功", function() {
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
