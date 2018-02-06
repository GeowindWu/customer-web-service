<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" 	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>easyui/demo.css" />
<script type="text/javascript" src="<%=basePath%>easyui/jquery.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>easyui/easyui-lang-zh_CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=basePath%>easyui/datagrid-detailview.js"></script>