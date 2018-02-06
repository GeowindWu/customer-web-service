function getRealPath(){
	       var localObj = window.location;
	       var contextPath = localObj.pathname.split("/")[1];
	       var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
	       return basePath  ;
	  }
function init () {
		var url = getRealPath() +  '/accessauth/logStatistic.action';
		$('#logCollectionDg').datagrid({
			url : url,
			checkOnSelect: false,
			columns:[[
			      	{field:'ck',checkbox:true,align:'center'},
					{field:'accessid',width:80,align:'center',hidden:true,title:'ID'},
					{field:'access_ip',width:150,align:'center',title:'访问IP'},
					{field:'access_user',width:150,align:'center',title:'访问用户'},
					{field:'api_code',width:200,align:'center',title:'访问接口',formatter:bussineessCode},
					{field:'failCount',width:150,align:'center',title:'失败次数'},
					{field:'successCount',width:150,align:'center',title:'成功次数'},
			          
			  ]],
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

function bussineessCode(value){
				 var bussniss = "";
				switch(value){
					case "5088":
						bussniss =  "售卡请求";
						break;
					case "0089":
						bussniss =  "售卡确认";	
						break;
					case "5021":
						bussniss =  "个人信息修改请求";
						break;
					case "2002":
						bussniss =  "个人信息修改确认";
						break;
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
					case "5093":
						bussniss =  "有卡退卡申请";
						break;
					case "0094":
						bussniss =  "有卡退卡确认";
						break;
					case "5095":
						bussniss =  "无卡退卡";
						break;
					case "2057":
						bussniss =  "统一充值接口_物理卡号查询";
						break;
					case "5029":
						bussniss =  "转值申请接口";
						break;
					case "5023":
						bussniss =  "查询可领余额信息";
						break;
					case "5096":
						bussniss =  "领取余额";
						break;
					default:
						bussniss =  value;
				}
				return bussniss;
			}