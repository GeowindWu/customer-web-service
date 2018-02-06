function getRealPath(){
	       var localObj = window.location;
	       var contextPath = localObj.pathname.split("/")[1];
	       var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
	       return basePath  ;
}
	function init () {
		var url = getRealPath() +  '';
		$('#dg').datagrid({
			url : url,
			checkOnSelect: false,
			columns:[[
			      	{field:'ck',checkbox:true,align:'center'},
					{field:'accountId',width:80,align:'center',hidden:true,title:'ID'},
					{field:'accountNO',width:100,align:'center',title:'序号'},
					{field:'ohterSideHandle',width:100,align:'center',title:'对方处理状态'},
					{field:'otherSideTime',width:180,align:'center',title:'对方实际时间'},
					{field:'intoAccount',width:80,align:'center',title:'是否到账',
						formatter:function(value){
							if(value == 0){
								return '已到账';
							}else if(value == 1){
								return '未到账';
							}else{
								return '未知';
							}
						}
					}
//					{field:'operator',width:160,align:'center',title:'操作',
//						formatter:function(value,row,rowIndex){
//							   var str = "<button onclick='enable(" + rowIndex + ");'>启用</butto>";  
//							   str +=  "<button   onclick='disable(" + rowIndex + ");'>禁用</button>";  
//							   return "";	
//						}
					
//					}
			          
			  ]]
		});
		
		dataGridTraggle('#dg');
	}
	