function getRealPath() {
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
		return basePath;
}

function ajaxCommon(url, params, funcSuccess) {

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

function dataGridTraggle(id){
	
	var selectIndex = -1;
	$(id).datagrid({
		onCheck: function(index,rowData){
			if (index == selectIndex) {
	       		 //第一次单击选中,第二次单击取消选中
					 $(this).datagrid('unselectRow', index);
				selectIndex = -1;
			}else{
			   selectIndex = index;
			}
		
		}
	});
	
}

function compareDate(startTime,endTime){
	var startDate = new Date(startTime);
	var endDate = new Date(endTime);
	if(startDate.getTime() <= endDate.getTime() ){
		 return false;
	}
	return true;

}


