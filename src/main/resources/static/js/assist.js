/**
 * datagrid初始化并读取数据
 * @param id 目标datagrid的id
 * @param url 请求路径
 */
function datagridInitAndShow(id,url) {
	$(id).datagrid({
		url:url,
		rownumbers:true,
		columns:[[
		          {field:'check',width:100,checkbox:true,align:'center'},
		          {field:'accessid',width:80,title:'id',align:'center'},	
		          {field:'accessUser',width:100,title:'访问用户',align:'center'},
		          {field:'api_code',width:120,title:'接口类型',align:'center',
		        	  formatter:bussineessCode
		          },
		          {field:'access_ip',width:100,title:'IP',align:'center'},
		          {field:'access_begin',width:180,title:'时间',align:'center'},
		          {field:'cardNo',width:100,title:'卡号',align:'center'},
		          {field:'terminalNo',width:100,title:'终端编号',align:'center'},
		          {field:'outletCode',width:100,title:'网点编码',align:'center'},
		          {field:'rechargeAmount',width:100,title:'充值金额',align:'center'},
		          {field:'writeCardResult',width:80,title:'写卡标识',align:'center',
		        	  formatter:function(value){
		        		  if(value == "1") {
		        			  return '成功';
		        		  }else if(value == "0"){
		        			  return '失败';	
		        		  }else{
		        			  return value;
		        		  }
		        		  
		        		  
		        	  }
		          },
		          {field:'rechargeManner',width:100,title:'充值方式',align:'center',
		        	  formatter:function(value){
		        		  if(value == "1") {
		        			  return '转值';
		        		  }else if(value == "0"){
		        			  return '普通充值';
		        		  }else{
		        			  return value;
		        		  }
		        	  }
		        	  
		          
		          }
		  ]],
		  idField:'recordId',
		  singleSelect: true,
		  method: 'post',
		  loadMsg: '正在加载，请稍后...',
		  pagination: true,
		  checkOnSelect: false,
		  fitColumns: true,
		  striped: true
//		  data:[
//			  {'access_ip':'10.1.1.1','access_begin':'2017-10-10','accessUser':'test1'},
//			  {'access_ip':'10.1.1.1','access_begin':'2017-10-10','accessUser':'test2'},
//			  {'access_ip':'10.1.1.1','access_begin':'2017-10-10','accessUser':'test3'},
//			  {'access_ip':'10.1.1.1','access_begin':'2017-10-10','accessUser':'test4'},
//		  ]
		
	});
	dataGridTraggle(id);
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

function queryAssistAccount(){
	$("#assistAccountDg").datagrid('load',{
		user: $("#costomer").val(),
		startDate: $('#startTime').val(),
		endDate: $('#endTime').val()
	});
	
}
function getRealPath(){
    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath  ;
}
function exportData(){
//	var data = $("#assistAccountDg").datagrid('getData');
//	var jsonData = JSON.stringify(data);
	var url = getRealPath() + "/accessauth/exportData.action";
	formSub(url);
}

function formSub(url){
	//创建form
	var form=$("<form></form>");
	//设置属性
	form.attr("action",url);
	form.attr("method","post");
	//创建input，即参数
	var inputUser=$("<input type='text' name='user'/>");
	inputUser.attr("value",$("#costomer").val());
	var inputStartTime=$("<input type='text' name='startDate'/>");
	inputStartTime.attr("value",$('#startTime').val());
	var inputEndTime=$("<input type='text' name='endDate'/>");
	inputEndTime.attr("value",$('#endTime').val());
	//注入参数到表单
	form.append(inputUser);
	form.append(inputStartTime);
	form.append(inputEndTime);
	form.appendTo("body");  
	form.hide();
	//提交表单
	form.submit();
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
						bussniss =  "普通钱包充值申请";
						break;
					case "1003":
						bussniss =  "普通钱包充值确认";
						break;
					case "5004":
						bussniss =  "普通钱包充值撤销申请";
						break;
					case "1005":
						bussniss =  "普通钱包充值撤销确认";
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
						bussniss =  "查询卡片芯片提供商、 卡片制造商";
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