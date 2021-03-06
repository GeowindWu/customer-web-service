<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="/easyui/demo.css" />
<script type="text/javascript" src="/easyui/jquery.min.js" ></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="/easyui/easyui-lang-zh_CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="/easyui/datagrid-detailview.js"></script>
<script src='/js/comm.js' type="text/javascript"></script>
<script src='/js/comm.js' type="text/javascript"></script>

<form action="/accessauth/exportLog.action" method="post">
		<div id="logs_center" style="margin-bottom:20px;width:90%;">
			<select id="access_api"  panelHeight="80px"
				class="easyui-combobox" name="access_api" style="width:25%;margin-top:2px;"
				data-options="label:'接口名称：'">
	
			<option value="" selected="true">所有</option>
			<option value="5002">统一充值接口_充值申请</option>
			<option value="1003">统一充值接口_充值确认</option>	
			<option value="5004">统一充值接口_撤销申请</option>
			<option value="1005">统一充值接口_撤销确认</option>
			<option value="2057">统一充值接口_物理卡号查询</option>
			</select>
			<input  id = "access_user"
					class="easyui-textbox" name="access_user"  style="width:25%;margin-top:2px;"
					data-options="label:'访问用户:'">
			<input id="accessid"
				class="easyui-textbox" name="accessid" style="width:25%;margin-top:2px;"
				data-options="label:'日志序号:'"/>
			<br>
			<select id = "access_status" panelHeight="60px"
				class="easyui-combobox" name="access_status" label="状态:"
				style="width:25%;margin-top:2px;">
				<option></option>
				<option value="11">正常</option>
				<option value="00">异常</option>
			</select>
			<input id = "access_start" data-options="formatter:ww4,parser:w4"
				class="easyui-datetimebox" name="access_start" style="width:25%;margin-top:2px;" label="开始日期:" >
			<input id = "access_end" data-options="formatter:ww4,parser:w4"
				class="easyui-datetimebox" name="access_end" style="width:25%;margin-top:2px;" label="结束日期:" >
			<input  class="easyui-linkbutton" value="查询日志"
				onclick="doSearch()" style="width:80px;"/>
			<input type='submit' class="easyui-linkbutton"  style="width:80px;" value='导出日志'/>	
			</br>

		</div>
</form>
		<table id="dg" title="日志记录" style="width:100%;height:80%" striped="true" 
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'business/api001',method:'post',showPageList:'false',showRefersh:'false'">
			<thead>
				<tr>
					<th data-options="field:'accessid',width:'8%',align:'center'">日志序号</th>
					<th data-options="field:'access_ip',width:'10%',align:'center'">访问Ip</th>
					<th data-options="field:'access_user',width:'8%',align:'center'">访问用户</th>
					<th data-options="field:'access_begin',width:'15%',align:'center'">访问时间</th>
					<th data-options="field:'api_code',width:'15%',align:'center',formatter:bussineessCode">接口名称</th>
					<th data-options="field:'response_code',width:'8%',align:'center'">响应代码</th>
					<th data-options="field:'error_desc',width:'20%',align:'center',formatter:errorDescr">错误描述</th>
					<th data-options="field:'status',width:'8%',align:'center',formatter:statusJudge">处理状态</th>
<!-- 					<th data-options="field:'button',width:'10%',align:'center',formatter:operation" >操作</th> -->
				</tr>
			</thead>
		</table>
		<script type="text/javascript">
			function statusJudge(value){
				if(null == value){
					return "异常";
				}
				// 11 为接口正常，其他异常
				if(value == "11"){
					return "正常";
				}else{
					return "异常";
				}
			}
			function operation(value,rowData,rowIndex){  
                        //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始） 
                        var str = "<a href='#' onclick='doselected(&apos;" + rowData.accessid + "&apos;)'>详情</a>";  
						return str;	
			}
			function doselected(accessid1){		
				 //$('#dg').form( {href: 'logs_selectedrow.jsp?accessid=' + accessid1 });
				//$('#dg').dialog({ href: 'logs_selectedrow.jsp?accessid=' + accessid1 }); 
			//	var baseUrl = $("#mainFrom_baseUrl").val();
			//	var reUrl = baseUrl + "logs/logs_detil.jsp?v=1";
			//	reUrl += "&accessId="+row.accessid;
				console.log(accessid1);		   
			    window.self.location = " javascript:changeCenter('logs_selectedrow.jsp?"+ "accessid" +"="+accessid1+" ')";				
				return ;
			}
			function errorDescr(value){
				if(null == value){
					return "";
				}
				var code = value.substring(7,12); //取后五位
				var response = "";
				switch(code){
					case "00000":
						response = "成功处理";
					 break;
					case "00002":
						response = "证件信息重复";
					 break;
					case "00010":
						response = "提交参数异常";
					 break;		
					case "00011":
						response = "终端不存在";
					 break;
					case "00012":
						response = "终端状态异常";
					 break;
					 
					case "00013":
						response = "卡已挂失";
					 break;
					case "00014":
						response = "卡已注销";
					 break;
					case "00015":
						response = "该卡为非系统卡";
					 break;
				
					case "00016":
						response = "该卡是黑名单卡";
					 break;
					case "00017":
						response = "超出充值上限";
					 break;
					case "00018":
						response = "卡已过期（超出卡片有效日期）";
					 break;
					case "00020":
						response = "终端登陆已失效（登陆时长超出规定时间）";
					 break;
					case "00021":
						response = "终端闲超时（接口系统等待终端请求间隔时间超长）";
					 break;
					 case "00023":
						response = "重复发送请求";
					 break;
					 case "00024":
						response = "该卡不可挂失";
					 break;
					 case "00025":
						response = "该卡不可注销";
					 break;
					 case "00026":
						response = "终端不支持此业务";
					 break;
					 case "00027":
						response = "当前网点不支持此卡类型功能";
					 break;
					 case "00028":
						response = "存在卡类转换半条记录，请手工上传半条记录";
					 break;
					 case "00029":
						response = "违法卡不允许办理此业务";
					 break;
					 case "00030":
						response = "当前时间段不允许办理业务";
					 break;	
					 case "00034":
						response = "请求处理超时";
					 break;	
					 case "00036":
						response = "卡片未发行";
					 break;	
					 case "00038":
						response = "该卡为记名卡，请添加个人信息";
					 break;	
					 case "00040":
						response = "卡片即将到达年审有效期，请及时年审";
					 break;	
					 case "00041":
						response = "卡片已过年审有效期，不能继续使用";
					 break;	
					 case "00042":
						response = "存在两张以上需要办理的卡";
					 break;		
					 case "00051":
						response = "用户名称或密码不对";
					 break;
					 case "00052":
						response = "操作不属于该网点";
					 break;
					 case "00053":
						response = "无操作权限";
					 break;
					 case "00054":
						response = "密码已过期";
					 break;
					 case "00060":
						response = "卡片未退卡";
					 break;
					 case "00061":
						response = "卡片已领余额或转值";
					 break;
					 case "00072":
						response = "未过争议期";
					 break;	
					 case "00073":
						response = "未该卡未发售";
					 break;	
					 case "00074":
						response = "卡片信息不正确";
					 break;	
					 case "00075":
						response = "卡片已经发售";
					 break;	
					 case "00082":
						response = "非法网点";
					 break;	
					 case "00083":
						response = "接口代码错误";
					 break;	
					 case "00084":
						response = "存储过程调用错误";
					 break;	
					 case "00085":
						response = "请检查帐户或密码是否正确";
					 break;	
					 case "00087":
						response = "客户信息与数据库不匹配";
					 break;	
					 case "00088":
						response = "系统还未结算";
					 break;	
					 case "00090":
						response = "无相应记录";
					 break;	
					 case "00095":
						response = "该卡没有办理申请,不能办理此业务";
					 break;	
					 case "00098":
						response = "数据库错误";
					 break;	
					 case "00099":
						response = "未知错误";
					 break;	
					 case "01001":
						response = "旧卡信息不存在";
					 break;	
					 case "01002":
						response = "旧卡已补售或已领余额，不可再补售";
					 break;	
					 case "01003":
						response = "旧卡与新卡类型不一致";
					 break;	
					 case "01004":
						response = "卡片押金审核中";
					 break;	
					 case "01005":
						response = "卡片成本审核中";
					 break;	
					 case "01008":
						response = "需要个人信息,或者个人信息不正确";
					 break;	
					 case "01055":
						response = "不是补卡售卡，不能转值";
					 break;	
					 case "01018":
						response = "新卡已经转值，不能再次转值";
					 break;	
					 
					 default:
					 	response = value;
				}
				return response;
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
		</script>
		<script type="text/javascript">
			$(function() {
				var pager = $('#dg').datagrid().datagrid('getPager'); // get the pager of datagrid
				pager.pagination({
					pagePosition: "bottom",  
			        beforePageText: '第', // 页数文本框前显示的汉字  
			        afterPageText: '页    共 {pages} 页',  
			        displayMsg: '当前显示  {to} 条记录   共 {total} 条记录', 
				    });
			});
		function doSearch(){
// 				url : 'business/api001'; 
				$('#dg').datagrid('load', {
					"accessid" : $('#accessid').val(),//将编辑框的值传入后台
					"access_user" : $('#access_user').val(),
					"access_api" : $('#access_api').combobox('getValue'),
					"access_status" : $('#access_status').combobox('getValue'),
					"access_start" : $('#access_start').val(),
					"access_end" : $('#access_end').val(),
				});
		}
		</script>
		<script>
			 function ww4(date){
			   var y = date.getFullYear();
			   var m = date.getMonth()+1;
			   var d = date.getDate();
			   var h = date.getHours();
			   var M = date.getMinutes();
			   var s = date.getSeconds();
			   function formatNumber(value){
			    return (value < 10 ? '0' : '') + value;
						}
			  // alert(formatNumber(h));
			   return y+'-'+m+'-'+d+' '+ formatNumber(h)+':'+formatNumber(M)+':'+formatNumber(s);
			  };
			  
			   function w4(s){
			   var t = Date.parse(s);
			   if (!isNaN(t)){
			    return new Date(t);
			   } else {
			    return new Date();
						}
			 };
		</script>
