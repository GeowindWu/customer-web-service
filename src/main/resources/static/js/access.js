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
                ips=value.split("~"); //字符分割 
                for(var i = 0; i < ips.length; i ++){
                	var check = reg.test(ips[i]);
                	if(!check){
                		return false;
                	}
                }
                return true;  
            },  
            message : "IP地址格式不正确,请检查，且要求多个IP地址以~符号分隔"  
        } ,
        checkEmpty: {
        	validator: function(value) {  
        		if(value != null && value.length > 0){
        			return true;
        		}
        		return false;
        	},
        	message : "请输入值"
        }
}); 
function changeAuthUi(baseUrl) {

		var selectData = $("#dg").datagrid("getChecked");
		if (selectData != null && selectData.length > 0) {
			$("#change_div").window("open");
			$("#change_name").textbox("setValue",selectData[0].api_name); 
			$("#change_code").textbox("setValue",selectData[0].api_code); 
			$("#change_startTime").textbox("setValue",selectData[0].start_time);
 			$("#change_endTime").textbox("setValue",selectData[0].end_time);
			if (selectData[0].status == '1') {
				$("#chang_effective").switchbutton("check");
			} else {
				$("#chang_effective").switchbutton("uncheck");
			}
		} else {
			$.messager.show({
				title : "提示",
				msg : "请选择要变更的记录！",
				timeout : 3000,
				showType : 'slide'
			});
		}
	}

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

	function deleteAuthconfirm(baseUrl) {
		var selectData = $("#dg").datagrid("getChecked");
		if (selectData == null || selectData.length == 0) {
			$.messager.show({
				title : "提示",
				msg : "请选择要删除的记录",
				timeout : 3000,
				showType : 'slide'
			});
		} else {
			$.messager.confirm("警告", "确认删除吗？", function(sureTrage) {
				if (sureTrage) {
					var url = baseUrl + "accessauth/deleteApi.action";
					var params = {
						"api.id" : selectData[0].api_id
					};
					ajaxCommon(url, params, delSuccess);
				}
			});
		}

	}
	function getRealPath(){
	       var localObj = window.location;
	       var contextPath = localObj.pathname.split("/")[1];
	       var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
	       return basePath  ;
	  }
	function init () {
		var url = getRealPath() +  '/accessauth/queryApi.action';
		$('#dg').datagrid({
			url : url,
			checkOnSelect: false,
			columns:[[
			      	{field:'ck',checkbox:true,align:'center'},
					{field:'api_id',width:80,align:'center',hidden:true,title:'ID'},
					{field:'api_name',width:200,align:'center',title:'接口名称'},
					{field:'api_code',width:100,align:'center',title:'接口码'},
					{field:'start_time',width:180,align:'center',title:'有效时间（启）'},
					{field:'end_time',width:180,align:'center',title:'有效时间（止）'},
					{field:'status',width:80,align:'center',title:'接口状态',
						formatter:function(value){
							if(value == 1){
								return '启用';
							}else{
								return '禁用';
							}
						}
					},
					{field:'operator',width:160,align:'center',title:'操作',
						formatter:function(value,row,rowIndex){
							   var str = "<a href='#' class='easyui-linkbutton' onclick='javascript:enable(" + rowIndex + ");'>启用</a> &nbsp;";  
							   str +=  "<a href='#'  class='easyui-linkbutton' onclick='javascript:disable(" + rowIndex + ");'>禁用</a>";  
							   return str;	
						}
					
					}
			          
			  ]]
		});
		dataGridTraggle('#dg');
	
	}

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
	function enable(rowIndex){
		var rows = $('#dg').datagrid('getRows');
		var rowData = rows[rowIndex];
		if(rowData.status == 1){
			$.messager.alert("提示", "接口已启用", "warning");
			return;
		}
		var url = getRealPath() + '/accessauth/apiEnable.action';
		$.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			data : {
				"api.id" : rowData.api_id
			},
			success : function(data, response, status) {
				 console.log(data); console.log(response); console.log(status);
				var resultInfo = data;
				if (resultInfo.serverStatus == "true") {
					$("#add_div").window("close");
					$.messager.show({
						title : "提示",
						msg : "接口启用成功",
						timeout : 5000,
						showType : 'slide'
					});
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("操作失败", "启用失败", "warning");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status
						+ " " + textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
		});
	}
	
	function disable(rowIndex){
		var rows = $('#dg').datagrid('getRows');
		var rowData = rows[rowIndex];
		if(rowData.status != 1){
			$.messager.alert("提示", "接口已禁用", "warning");
			return;
		}
		var url = getRealPath() + '/accessauth/apiDisable.action';
		$.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			data : {
				"api.id" : rowData.api_id
			},
			success : function(data, response, status) {
				 console.log(data); console.log(response); console.log(status);
				var resultInfo = data;
				if (resultInfo.serverStatus == "true") {
					$("#add_div").window("close");
					$.messager.show({
						title : "提示",
						msg : "接口禁用成功",
						timeout : 5000,
						showType : 'slide'
					});
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("操作失败", "禁用失败", "warning");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status
						+ " " + textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
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

	function addAuth(baseUrl) {
		var add_name = $("#add_name").textbox('getValue');
		var add_code = $("#add_code").textbox('getValue');
		var add_begintime = $("#add_begintime").textbox('getValue');
		var add_endtime = $("#add_endtime").textbox('getValue');
		var add_status = 0 // 默认禁用
		var  status= $("#add_status").switchbutton("options").checked;
		if(status){
			add_status = 1; //启用
		}
		var url = baseUrl + "accessauth/addApi.action";
		if(compareDate(add_begintime, add_endtime)){
			// 开始时间不小于结束时间
			alert("有效期截止时间必须大于起始时间");
		}else{
			$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				// data : $('#myform').serialize(),
				data : {
					"api.apiName" : add_name,
					"api.apiCode" : add_code,
					"api.startTime" : add_begintime,
					"api.endTime" : add_endtime,
					"api.status" : add_status
				},
				success : function(data, response, status) {
					 console.log(data); console.log(response); console.log(status);
					var resultInfo = data;
					if (resultInfo.serverStatus == "true") {
						$("#add_div").window("close");
						$.messager.show({
							title : "提示",
							msg : "新增成功！",
							timeout : 5000,
							showType : 'slide'
						});
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert("操作失败", "新增失败", "warning");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status
							+ " " + textStatus;
					$.messager.alert("异常", errorInof, "error");
					console.log(XMLHttpRequest.readyState);
				}
			});
		}
	}
	
	
	$('#add_status').combobox({
		formatter : function(row) {
			var opts = $(this).combobox('options');
			return row[opts.textField];
		}
	});
	
	function changeAuth(baseUrl) {
		var change_name = $("#change_name").textbox('getValue');
		var change_code = $("#change_code").textbox('getValue');
		var change_begintime = $("#change_startTime").textbox('getValue');
		var change_endtime = $("#change_endTime").textbox('getValue');
		var change_effective = $("#chang_effective").switchbutton("options").checked;
		var change_status;
		if(change_effective){
			change_status = 1;
		}else{
			change_status = 0;
		}
		var url = baseUrl + "accessauth/changeApi.action";
		var selectData = $("#dg").datagrid("getChecked");
		
		if (change_endtime == null) {
			$.messager.alert("操作失败", "请选择修改时间", "warning");
			return;
		}
		if(compareDate(change_begintime, change_endtime)){
			// 开始时间不小于结束时间
			alert("有效期截止时间必须大于起始时间");
		}else{
			$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				// data : $('#myform').serialize(),
				data : {
					"api.id" : selectData[0].api_id,
					"api.apiName":change_name,
					"api.apiCode":change_code,
					"api.status" : change_status,
					"api.startTime":change_begintime,
					"api.endTime" : change_endtime
				},
				success : function(data, response, status) {
					console.log(data); console.log(response); console.log(status);
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
						$.messager.alert("操作失败", "授权未成功", "warning");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
					var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status
							+ " " + textStatus;
					$.messager.alert("异常", errorInof, "error");
					console.log(XMLHttpRequest.readyState);
				}
			});
		}
	}