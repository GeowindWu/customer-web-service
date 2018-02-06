function updatePw(baseUrl,userName){
		var old = $("#oldPw").val();
		var newPw = $("#newPw").val();
		var newPwComfirm = $("#newPwComfirm").val();
		if(newPw != newPwComfirm){
			$.messager.alert('提醒','新密码不一致');
			return;
		}
		var data = $.param({
			'userName' : userName,
			'oldPw' : old,
			'newPw' : newPw
		},true);
		var url = baseUrl + "accessauth/updatePassword.action";
		myAjax(url,data,successCallback);
	}
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		 return unescape(arr[2]);
	else
		return null;
}	

	function successCallback(data,response,status){
		var resultInfo = data;
		if (resultInfo.serverStatus == "true") {
			$.messager.show({
				title : "提示",
				msg : "变更成功！",
				timeout : 5000,
				showType : 'slide'
			});
//			document.location = "/login.jsp";
		} else {
			$.messager.alert("操作失败", "变更失败", "warning");
		}
	}
	function myAjax(url, params, funcSuccess) {
	$.ajax({
			url : url,
			type : "post",
			dataType : 'json',
			data : params,
			success : funcSuccess,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status + " "
						+ textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			}
		});
	}
