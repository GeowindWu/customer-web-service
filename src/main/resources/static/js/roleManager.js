function getRealPath(){
       var localObj = window.location;
       var contextPath = localObj.pathname.split("/")[1];
       var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
       return basePath  ;
  }

function init() {
	var url = getRealPath() + '/accessauth/queryRoles.action';
	$("#dg").datagrid({
		url: url,
		method: 'post',
		checkOnSelect:false,
		loadMsg: '正在加载，请稍后...'
	});
	dataGridTraggle("#dg");
	$("#dg").datagrid({
			onClickRow : function(rowIndex, rowData) {
			$('#showRoleApis').window('open');
			$('#showRoleName').textbox("setValue",
					rowData.role_name);
 			var url = getRealPath() + '/accessauth/showRoleApis.action';
			$("#rolesApi").datagrid({
				url: url,
				queryParams:{
				"roleId": rowData.role_id
				}
			});
			}
	});
								
};

function dataGridTraggle(id){
	
	var selectIndex = -1;
	$(id).datagrid({
		onCheck: function(index,rowData){
			if (index == selectIndex) {
	       		 //第一次单击选中,第二次单击取消选中
					 $(this).datagrid('uncheckRow', index);
				selectIndex = -1;
			}else{
			   selectIndex = index;
			}
		
		}
	});
	
}
function openAddWindow(){
		dataGridTraggle("#selectAccess");
		$('#add_role').window('open');
		$("#selectAccess").datagrid({
			url:  getRealPath() + '/accessauth/queryApi.action',
		});
		
}

function changeRole(baseUrl) {

		var selectData = $("#dg").datagrid("getChecked");
		console.log(selectData);
		if (selectData == null || selectData.length == 0 ) {
			$.messager.show({
				title : "提示",
				msg : "请选择要变更的记录！",
				timeout : 3000,
				showType : 'slide'	
			});
			
		} else {
			$("#add_role").window("open");
			$("#selectAccess").datagrid({
				url:  getRealPath() + '/accessauth/queryApi.action',
			});
			$("#roleId").textbox('setValue',selectData[0].role_id);
			$("#roleName").textbox("setValue", selectData[0].role_name);
			$("#roleInfo").textbox("setValue", selectData[0].role_info);
			$("#priority").textbox("setValue", selectData[0].priority);

		}
	}

function deleteRole(baseUrl) {
	var checked = $("#dg").datagrid("getChecked");
	if (checked != null && checked.length > 0) {
		$.messager.confirm("警告", "确认删除吗？", function(sureTrage) {
			if (sureTrage) {
				var url = baseUrl + "accessauth/deleteRole.action";
				var params = {
					"role.roleId" : checked[0].role_id
				};
				ajaxCommon(url, params, delSuccess);
			}
		});
	} else {
		$.messager.show({
			title : "提示",
			msg : "请选择要删除的记录",
			timeout : 3000,
			showType : 'slide'
		});
	}

}
	///////////////////////////////////////////////////////////
	function delSuccess(data, response, status) {
		var resultInfo = data;
		if (resultInfo.serverStatus == "true") {
			$.messager.show({
				title : "提示",
				msg : "删除成功！",
				timeout : 5000,
				showType : 'slide'
			});
			$("#dg").datagrid("reload");
		} else {
			$.messager.alert("操作失败", "删除未成功", "warning");
		}
	}

	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();

		var h = date.getHours();
		var mi = date.getMinutes();
		var s = date.getSeconds();

		var dataBegin = y + "-" + (m < 10 ? ("0" + m) : m) + "-"
				+ (d < 10 ? ('0' + d) : d);
		var end = (h < 10 ? ('0' + h) : (h + '')) + ":"
				+ (mi < 10 ? ('0' + mi) : (mi + '')) + ":"
				+ (s < 10 ? ('0' + s) : (s + ''));

		return dataBegin + " " + end;
	}
	function myparser(date) {
		if (!date)
			return new Date();

		var dates = date.split(" ");
		var s = dates[0];
		var time = dates[1];

		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);

		var times = time.split(':');
		var h = parseInt(times[0], 10);
		var mi = parseInt(times[1], 10);
		var s = parseInt(times[2], 10);

		if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(mi)
				&& !isNaN(s)) {
			return new Date(y, m - 1, d, h, mi, s);
		} else {
			return new Date();
		}
	}
	function getChecked(id){
			var arr = [];
			var checkeds = $(id).datagrid('getChecked');
			for (var i = 0; i < checkeds.length; i++) {
	   		 arr.push(checkeds[i].api_id);
	    }
	  	return arr;
		}

	function addRole(baseUrl) {
		var roleId = $("#roleId").val();
		var roleName = $("#roleName").textbox('getValue');
		var roleInfo = $("#roleInfo").textbox('getValue');
		var priority = $("#priority").textbox('getValue');
		// 			var selectAccess = $("#selectAccess").datagrid('getSelections');\
		var selectIDs = getChecked("#selectAccess");
		if (selectIDs == null || selectIDs.length == 0) {
			$.messager.alert({
				title : '提示',
				msg : '请为角色分配权限',
				icon : 'info'
			});
			return;
		}
		var param;
		var url ;
		if(roleId > 0){
			// 修改
			url = baseUrl + "accessauth/changeRole.action";
			param = $.param({
				"role.roleName" : roleName,
				"role.roleInfo" : roleInfo,
				"role.priority" : priority,
				"role.roleId" : roleId,
				"apids" : selectIDs
				}, true);
		}else {
			// 新增
		   url = baseUrl + "accessauth/addRole.action";
		   param = $.param({
			"role.roleName" : roleName,
			"role.roleInfo" : roleInfo,
			"role.priority" : priority,
			"apids" : selectIDs
			}, true);
		   
		}

		$.ajax({
			url : url,
			type : 'post',
			cache : false,
			async: true,
			dataType : 'json',
			// data : $('#myform').serialize(),
			data : param,
			success : function(data, response, status) {
				// console.log(data); console.log(response); console.log(status);
				var resultInfo = data;
				if (resultInfo.serverStatus == "true") {
					$("#add_role").window("close");
					$.messager.show({
						title : "提示",
						msg : "操作成功",
						timeout : 5000,
						showType : 'slide'
					});
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("操作失败", "操作失败", "warning");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status
						+ " " + textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
		});
	};
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();

		var h = date.getHours();
		var mi = date.getMinutes();
		var s = date.getSeconds();

		var dataBegin = y + "-" + (m < 10 ? ("0" + m) : m) + "-"
				+ (d < 10 ? ('0' + d) : d);
		var end = (h < 10 ? ('0' + h) : (h + '')) + ":"
				+ (mi < 10 ? ('0' + mi) : (mi + '')) + ":"
				+ (s < 10 ? ('0' + s) : (s + ''));

		return dataBegin + " " + end;
	}
	function myparser(date) {
		if (!date)
			return new Date();

		var dates = date.split(" ");
		var s = dates[0];
		var time = dates[1];

		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);

		var times = time.split(':');
		var h = parseInt(times[0], 10);
		var mi = parseInt(times[1], 10);
		var s = parseInt(times[2], 10);

		if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(mi)
				&& !isNaN(s)) {
			return new Date(y, m - 1, d, h, mi, s);
		} else {
			return new Date();
		}
	}



