function init() {
	var url = getRealPath() + "/accessauth/apiMonitor.action";
	$("#showApiStatus").datagrid({
			url: url,
			rownumbers:true,
			columns : [ [ {
				field : 'check',
				width : 100,
				checkbox : true,
				align : 'center'
			}, {
				field : 'accessid',
				width : 100,
				align : 'center',
				hidden: true
			}, 
//			{
//				field : 'api_name',
//				width : 100,
//				title : '接口名称',
//				align : 'center'
//			}, 
			{
				field : 'api_code',
				width : 100,
				title : '接口名称',
				align : 'center',
				formatter:bussineessCode
			}, {
				field : 'access_begin',
				width : 100,
				title : '访问时间',
				align : 'center'
			}, {
				field : 'response_code',
				width : 100,
				title : '状态',
				align : 'center',
				formatter:function(value){
					if(value == "00000"){
						return "正常";
					}else{
						return "异常";
					}
				}
			}
			] ],
			idField : 'accessid',
			singleSelect : true,
			method : 'post',
			loadMsg : '正在加载，请稍后...',
			pagination : true,
			checkOnSelect : false,
			fitColumns : true,
			striped : true
		});
		

	}
	function getRealPath() {
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		var basePath = localObj.protocol + "//" + localObj.host + "/"
				+ contextPath;
		return basePath;
	}
	function queryAccess() {
		var userCode = $("#userCode").textbox('getValue');
		if (userCode == null || userCode == "") {
			alert("请输入客户代码");
			return;
		}
		var param = $.param({
			'userCode' : userCode
		}, true);
		var url = getRealPath() + "/accessauth/getAccessByCode.action";
		myAjax(param, url, "#showApps");
	}

	function myAjax(data, url, dgId) {
		$.ajax({
			url : url,
			type : "post",
			data : data,
			dataType : "json",
			success : function(data, response, status) {
				$(dgId).datagrid('loadData', data);
				$.messager.show({
					msg : '加载完成',
					timeout : 5000,
					showType : 'slide'
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status + " "
						+ textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
		});
	}
	
	function queryAccessByAppId(){
			var appId = $("#appId").textbox('getValue');
			if(appId == null || appId == ""){
				alert("请输入应用id");
				return ;
			}
			var param = $.param({
				'appId' : appId
				
			},true);
			var url = getRealPath() + "/accessauth/getAccessById.action";
			myAjax(param, url,"#showpermission");
	}
	
	
		function bussineessCode(value){
				 var bussniss = "";
				switch(value){
					case "5002":
						bussniss =  "统一充值接口_充值申请";
						break;
					case "1003":
						bussniss =  "统一充值接口_充值确认";
						break;
					case "5004":
						bussniss =  "统一充值接口_取消申请";
						break;
					case "1005":
						bussniss =  "统一充值接口_取消确认";
						break;
					case "2057":
						bussniss =  "统一充值接口_物理卡号查询";
						break;
					default:
						bussniss =  value;
				
				}
				return bussniss;
			}