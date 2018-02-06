$.extend($.fn.validatebox.defaults.rules, {           
        checkWSDL: {     
            validator: function(value,param){               
                 var reg = "^(http://|([0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}:[0-9]{1,4}))[/a-zA-Z0-9._%&:=(),?+]*[?]{1}wsdl$";  
                 return reg.test(value);  
            },     
            message: '请输入合法的WSDL地址'     
        },  
        checkIp : {// 验证IP地址  
            validator : function(value) {  
                var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/ ;  
                var ips = new Array(); //定义一数组 
                ips=value.split(","); //字符分割 
                for(var i = 0; i < ips.length; i ++){
                	var check = reg.test(ips[i]);
                	if(!check){
                		return false;
                	}
                }
                return true;  
            },  
            message : "IP地址格式不正确,请检查，且要求多个IP地址以逗号,分隔"  
    }  
}); 


function openChangeWindow() {
	var checked  = $("#dg").datagrid('getChecked');
	if(checked == null || checked.length == 0){
		$.messager.alert({
			title:'提示',
			msg: '请选择需要修改的用户',
			icon: 'info'
		});
		return ;
		
	}
	var targetRow = checked[0];
	$("#change_user").window('open');
	$("#change_accessString").textbox('setValue',targetRow.access_str);
	$("#change_accessIP").textbox('setValue',targetRow.access_ip);
	$("#change_userCode").textbox('setValue',targetRow.user_code);
	$("#change_channel").textbox('setValue',targetRow.channel);
	var url = getRealPath() + '/accessauth/queryRoles.action';
	$("#change_selectRoles").datagrid({
		url: url
	});
	

}

function changeUser(baseUrl){
	var accessString = $("#change_accessString").textbox('getValue');
	var accessIP = $("#change_accessIP").textbox('getValue');
	var userCode = $("#change_userCode").textbox('getValue');
	var channel = $("#change_channel").textbox('getValue');
	var selectRoles = getChecked("#change_selectRoles");
	// 获取分配的应用
	if (selectRoles == null || selectRoles.length == 0) {
		$.messager.alert({
			title : '提示',
			msg : '请为用户分配角色',
			icon : 'info'
		});
		return;
	}
	var checked  = $("#dg").datagrid('getChecked');
	var param = $.param({
		"user.id":checked[0].user_id,
		"user.accessString" : accessString,
		"user.accessIP" : accessIP,
		"user.userCode" : userCode,
		"user.channel" : channel,
		"roleIds" : selectRoles
	}, true);
	var url = baseUrl + "accessauth/changeUser.action";
	$.ajax({
		url : url,
		type : 'post',
		dataType : 'json',
		// data : $('#myform').serialize(),
		data : param,
		scriptCharset : 'utf-8',
		success : function(data, response, status) {
			// console.log(data); console.log(response);
			// console.log(status);
			var resultInfo = data;
			if (resultInfo.serverStatus == "true") {
				$("#change_user").window("close");
				$.messager.show({
					title : "提示",
					msg : "修改成功！",
					timeout : 5000,
					showType : 'slide'
				});
				$("#dg").datagrid("reload");
			} else {
				$.messager.alert("操作失败", "修改失败", "warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status + " "
					+ textStatus;
			$.messager.alert("异常", errorInof, "error");
			console.log(XMLHttpRequest.readyState);
		}
	});
}

// /////////////////////////////////////////////////////////
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

function deleteUser(baseUrl) {
	var checked = $('#dg').datagrid('getChecked');
	if(checked == null || checked.length == 0){
		$.messager.show({
			title : "提示",
			msg : "请选择要删除的记录",
			timeout : 3000,
			showType : 'slide'
		});
		
	}else{
		$.messager.confirm("警告", "确认删除吗？", function(sureTrage) {
			if (sureTrage) {
				var url = baseUrl + "accessauth/deleteUser.action";
				var params = {
					"user.id" : checked[0].user_id
				};
				ajaxCommon(url, params, delSuccess);
			}
		});
	}

};
function getRealPath(){
    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath  ;
}
function init() {
	var url = getRealPath() + '/accessauth/queryUser.action';
	$("#dg").datagrid({
		url: url,
		method: 'post',
		checkOnSelect: false,
	});
	$("#dg").datagrid({
		 
		onClickRow : function(rowIndex, rowData) {
			$('#showUserRoles').window('open');
			$('#showUserName').textbox("setValue", rowData.access_str);
			 var url = getRealPath() + '/accessauth/showUserRoles.action';
			 var getAccessUrl = getRealPath() + '/accessauth/showRoleApis.action';
			$("#rolesApi").datagrid({
				view: detailview, 
				url : url,
				queryParams : {
					"userId" : rowData.user_id
				},
		        detailFormatter:function(index,row){//严重注意喔  
		             return "<div><table id='ddv-" + index + "' style='width:85%;height:200' ></table></div>";  
		         },  
		         onExpandRow: function(index,row){//嵌套第一层，严重注意喔  
		             var ddv = $(this).datagrid('getRowDetail',index).find('#ddv-'+index);//严重注意喔  
		             ddv.datagrid({  
		                 url: getAccessUrl,  
		                 pagination: true,
		                 autoRowHeight:true,  
		                 fitColumns:true,//改变横向滚动条  
		                 singleSelect:false,//去掉选中效果  
		                 rownumbers:true,  
		                 loadMsg:'正在加载...',  
//		               height:'auto',  
		                 columns:[[  
		                     {field:'api_id',title:'ID',width:100,hidden:true},  
		                     {field:'api_name',title:'接口名称',width:180},
		                 ]],
		                 queryParams: {
			     				"roleId": row.role_id
			     			}
		                });
		            }
		                 
			})
		}
	});
	dataGridTraggle("#dg");
	
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
function openAddUserWindow() {
	dataGridTraggle("#selectRoles");
	$('#add_user').window('open');
	var url = getRealPath() + '/accessauth/queryRoles.action';
	$("#selectRoles").datagrid({
		url: url
	});
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

function getChecked(id) {
	var arr = [];
	var checkeds = $(id).datagrid('getChecked');
	for ( var i = 0; i < checkeds.length; i++) {
		arr.push(checkeds[i].role_id);
	}
	return arr;
}

function addUser(baseUrl) {
	var accessString = $("#accessString").textbox('getValue');
	var accessIP = $("#accessIP").textbox('getValue');
	var userCode = $("#userCode").textbox('getValue');
	var channel = $("#channel").textbox('getValue');
//	var add_begintime = $("#add_begintime").textbox('getValue');
//	var add_endtime = $("#add_endtime").textbox('getValue');
	var selectRoles = getChecked("#selectRoles");
	// 获取分配的应用
	if (selectRoles == null || selectRoles.length == 0) {
		$.messager.alert({
			title : '提示',
			msg : '请为用户分配角色',
			icon : 'info'
		});
		return;
	}
	var param = $.param({
		"user.accessString" : accessString,
		"user.accessIP" : accessIP,
		"user.userCode" : userCode,
		"user.channel" : channel,
//		"user.startTime" : add_begintime,
//		"user.endTime" : add_endtime,
		"roleIds" : selectRoles

	}, true);
	var url = baseUrl + "accessauth/addUser.action";
//	if (compareDate(add_begintime, add_endtime)) {
//		// 开始时间不小于结束时间
//		alert("有效期截止时间必须大于起始时间");
//	} else {

		$.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			// data : $('#myform').serialize(),
			data : param,
			scriptCharset : 'utf-8',
			success : function(data, response, status) {
				// console.log(data); console.log(response);
				// console.log(status);
				var resultInfo = data;
				if (resultInfo.serverStatus == "true") {
					$("#add_user").window("close");
					$.messager.show({
						title : "提示",
						msg : "添加成功！",
						timeout : 5000,
						showType : 'slide'
					});
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("操作失败", "添加失败", "warning");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status + " "
						+ textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
		});
	
}



function changeAuth(baseUrl) {
	var change_endtime = $("#change_endtime").textbox('getValue');
	var chang_effective = $("#chang_effective").switchbutton("options").checked;
	var url = baseUrl + "accessauth/changeApi.action";
	var selectData = $("#dg").datagrid("getChecked");

	if (change_endtime == null || selectData == null) {
		$.messager.alert("操作失败", "请选择受该时间", "warning");
		return;
	}

	$.ajax({
		url : url,
		type : 'post',
		dataType : 'json',
		scriptCharset : 'utf-8',
		// data : $('#myform').serialize(),
		data : {
			"api.id" : selectData[0].tid,
			"api.status" : chang_effective,
			"api.endTime" : change_endtime
		},
		success : function(data, response, status) {
			// console.log(data); console.log(response); console.log(status);
			var resultInfo = data;
			if (resultInfo.serverStatus == "true") {
				$("#change_div").window("close");
				$.messager.show({
					title : "提示",
					msg : "修改成功！",
					timeout : 5000,
					showType : 'slide'
				});
				$("#dg").datagrid("reload");
			} else {
				$.messager.alert("操作失败", "修改失败", "warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status + " "
					+ textStatus;
			$.messager.alert("异常", errorInof, "error");
			console.log(XMLHttpRequest.readyState);
		}
	});
}