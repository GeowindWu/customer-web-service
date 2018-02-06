/**
 * datagrid初始化并读取数据
 * 
 * @param id
 *            目标datagrid的id
 * @param url
 *            请求路径
 */
function datagridInitAndShow(id) {
	$(id).datagrid({
		columns : [ [ {
			field : 'check',
			width : 100,
			checkbox : true,
			align : 'center'
		}, {
			field : 'refillId',
			width : 100,
			title : '充值记录ID',
			align : 'center'
		}, {
			field : 'refillTime',
			width : 100,
			title : '充值时间',
			align : 'center'
		}, {
			field : 'userId',
			width : 100,
			title : '用户ID',
			align : 'center'
		}, {
			field : 'refillMoney',
			width : 100,
			title : '充值金额',
			align : 'center'
		}, {
			field : 'cardNo',
			width : 100,
			title : '卡号',
			align : 'center'
		}, {
			field : 'refillStatus',
			width : 100,
			title : '充值状态',
			align : 'center'
		} ] ],
		idField : 'refillId',
		singleSelect : true,
		method : 'post',
		loadMsg : '正在加载，请稍后...',
		pagination : true,
		checkOnSelect : false,
		fitColumns : true,
		striped : true,
		onLoadSuccess : function(data) {
			if (data.total > 0)
				return;
			$.messager.show({
					title:'提示',
					msg:'无相关记录',
					timeout:5000,
					showType:'slide'});
//			$('#dg').datagrid('appendRow', {
//				中间显示的列的字段名称 : '没有相关记录'
//			});
		}
	// data: [
	// {'recordId':'1', 'logNo':'001','time':'2017-10-10','costomer':
	// '微付充','rechargeValue':100,'cardNo':'2017001','status':'正常·'},
	// {'recordId':'2', 'logNo':'002','time':'2017-10-10','costomer':
	// '雪球','rechargeValue':200,'cardNo':'2017002','status':'正常'},
	// ]

	});
	dataGridTraggle(id);
}

function dataGridTraggle(id) {

	var selectIndex = -1;
	$(id).datagrid({
		onCheck : function(index, rowData) {
			if (index == selectIndex) {
				// 第一次单击选中,第二次单击取消选中
				$(this).datagrid('uncheckRow', index);
				selectIndex = -1;
			} else {
				selectIndex = index;
			}

		}
	});
 
}

function doSearch(url) {
	 var opts = $("#rechargeRecordDg").datagrid("options");
	 opts.url = url;
	$("#rechargeRecordDg").datagrid('load', {
		cardNo:$("#card_no").val()
//		costomer : $("#costomer").val(),
//		logNO : $('#logNO').val(),
//		status : $('#status').val(),
//		startTime : $('#startTime').val(),
//		endTime : $('#endTime').val()
	});
}