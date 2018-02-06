function getRealPath() {
	var localObj = window.location;
	var contextPath = localObj.pathname.split("/")[1];
	var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
	return basePath;
}

function init() {
	var url = getRealPath() + '/accessauth/queryAdmin.action';
	$("#adminDg").datagrid({
		url : url,
		columns : [ [ {
			field : 'check',
			width : 100,
			checkbox : true,
			align : 'center'
		}, {
			field : 'admin_id',
			width : 100,
			hidden : true,
			align : 'center'
		}, {
			field : 'adminUUID',
			width : 100,
			hidden : true,
			align : 'center'
		}, {
			field : 'adminName',
			width : 100,
			title : '姓名',
			align : 'center'
		}, {
			field : 'department',
			width : 100,
			title : '部门 ',
			align : 'center'
		}, {
			field : 'phoneNo',
			width : 100,
			title : '电话号码',
			align : 'center'
		}, {
			field : 'status',
			width : 100,
			title : '状态',
			align : 'center'
		}, {
			field : 'gmt_create',
			width : 100,
			title : '创建时间',
			align : 'center'
		} ] ],
		idField : 'admin_id',
		singleSelect : true,
		toolbar : [ {
			iconCls : 'icon-add',
			handler : function() {
				openWin('#addAdmin_window', '新增管理员');
			},
			text : '新增系统管理员'
		}, '-', {
			iconCls : 'icon-remove',
			handler : function(){deleteAdmin();},
			text : '删除系统管理员'

		}, '-', {
			iconCls : 'icon-ok',
			handler : function() {
				doEnable();
			},
			text : '启用'
		}, '-', {
			iconCls : 'icon-no',
			handler : function() {
				doDisable();
			},
			text : '禁用'
		} ],
		method : 'post',
		loadMsg : '正在加载，请稍后...',
		pagination : true,
		checkOnSelect : false,
		fitColumns : true,
		striped : true
	// data:[
	// {'adminId':1,'adminName':
	// '张三','department':'技术部','phoneNo':'13558767632','status':'正常','createTime':'2017-11-23'},
	// {'adminId':2,'adminName':
	// '李四','department':'客服部','phoneNo':'13788767632','status':'正常','createTime':'2017-11-23'},
	// {'adminId':3,'adminName':
	// '王五','department':'客服部','phoneNo':'13788767621','status':'禁用','createTime':'2017-11-23'}
	// ]
	});
	dataGridTraggle("#adminDg");
}

function dataGridTraggle(id) {

	var selectIndex = -1;
	$(id).datagrid({
		onCheck : function(index, rowData) {
			if (index == selectIndex) {
				// 第一次单击选中,第二次单击取消选中
				$(this).datagrid('uncheckRow', index);
				selectIndex = -1;
			} else {
				selectIndex = index;
			}

		}
	});
}

/**
 * 打开窗口
 */
function openWin(id, title) {
	// 初始化
	$(id).window('center');
	$(id).window({
		title : title,
		closed : true,
		closable : true,
	});
	$(id).window('open');
}
function successCallback(data, response, status) {
	if (data.serverStatus == "true") {
		$.messager.show({
			title : '操作成功',
			msg : data.MSG,
			timeout : 5000,
			showType : 'slide'
		});
		// 刷新
		$('#adminDg').datagrid('reload');
	} else {
		$.messager.show({
			title : '操作失败',
			msg : data.MSG,
			timeout : 5000,
			showType : 'slide'
		});
	}
	$("#addAdmin_window").window('close');

}
function deleteAdmin() {
	var selected = $("#adminDg").datagrid('getChecked');
	if (selected == null || selected.length == 0) {
		$.messager.alert('提醒', '请选择需要删除的管理员', null, null);
		return;
	}
	var data = $.param({
			'admin.adminId' : selected[0].admin_id
	},true);
	var url = getRealPath() + '/accessauth/deleteAdmin.action';
	myAjax(data, url, successCallback);
	
}
	function addAdmin() {
		var url = getRealPath() + '/accessauth/addAdmin.action';
		var adminName = $("#add_adminName").val();
		var department = $("#add_department").val();
		var phoneNo = $("#add_phoneNo").val();
		var add_status = '禁用';
		var status = $("#add_status").switchbutton("options").checked;
		if (status) {
			add_status = '正常'; // 启用
		}
		var data = $.param({
			'admin.adminName' : adminName,
			'admin.department' : department,
			'admin.phoneNo' : phoneNo,
			'admin.status' : add_status,
			'admin.userType' : '系统维护员'
		}, true);
		myAjax(data, url, successCallback);
	}

	function doEnable() {
		var selected = $("#adminDg").datagrid('getChecked');
		if (selected == null || selected.length == 0) {
			$.messager.alert('提醒', '请选择需要启用的管理员', null, null);
			return;
		}
		if (selected[0].status == '正常') {
			$.messager.alert('提醒', '已经启用', null, null);
			return;
		}
		var data = $.param({
			'admin.adminId' : selected[0].admin_id
		});
		var url = getRealPath() + '/accessauth/enableAdmin.action';
		myAjax(data, url, successCallback);
	}

	function doDisable() {
		var selected = $("#adminDg").datagrid('getChecked');
		if (selected == null || selected.length == 0) {
			$.messager.alert('提醒', '请选择需要启用的管理员', null, null);
			return;
		}
		if (selected[0].status == '禁用') {
			$.messager.alert('提醒', '已经禁用', null, null);
			return;
		}
		var data = $.param({
			'admin.adminId' : selected[0].admin_id
		});
		var url = getRealPath() + '/accessauth/disableAdmin.action';
		myAjax(data, url, successCallback);

	}

	function myAjax(data, url, successCallback) {
		$.ajax({
			url : url,
			type : "post",
			data : data,
			dataType : "json",
			success : successCallback,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status + " "
						+ textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
		});
	}

