
	function initMenuHref() {
		var arr = document.getElementsByName("menuhref");
		var len = arr.length;
		var i = 0;
		
		for (i; i < len; i++) {
			if (arr[i].nodeName == "A".toString()) {
				arr[i].onclick = function() {
					this.className = "a1";
					for (var j = 0; j < len; ++j) {
						if (arr[j].nodeName == "A".toString() && arr[j] != this)
							arr[j].className = "a2";
					}
				}
			}
		}


	}

	function quit() {

		//ajax异步提交  
		var url = getRealPath() + '/exit';
		$.ajax({
			type : "POST", //post提交方式默认是get
			dataType : 'json',
			url : url,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var errorInof = "请求失败，请联系维护员！" + XMLHttpRequest.status
					+ " " + textStatus;
				$.messager.alert("异常", errorInof, "error");
				console.log(XMLHttpRequest.readyState);
			},
			success : function(data, response, status) {
				var returnInfo = data;
				$.messager.show({
					msg : data.MSG,
					timeout : 5000,
					showType : 'slide'
				});
				if (returnInfo.serverStatus == "true".toString()) {
					document.location = "login.jsp";
				}
			}
		});
	}