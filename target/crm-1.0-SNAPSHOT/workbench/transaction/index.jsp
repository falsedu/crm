<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
%>



<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){

		pageList(1,3);

		$("#qx").prop("checked",false);

		$("#tranBody").on("click",$("input[name=xz]"),function () {
			$("#qx").prop("checked",$("input[name=xz]:checked").length==$("input[name=xz]").length);
		})

		$("#qx").click(function () {
			$("input[name=xz]").prop("checked",this.checked);
		})
		
		
		
	});
	function  pageList(pageNo,pageSize) {

		$("#qx").prop("checked",false);

		$("#search-owner").val(	$("#hidden-owner").val());
		$("#search-name").val(	$("#hidden-name").val());
		$("#search-customer").val(		$("#hidden-customer").val());
		$("#search-source").val(	$("#hidden-source").val());
		$("#search-stage").val($("#hidden-stage").val());
		$("#search-type").val(		$("#hidden-type").val());
		$("#search-contacts").val(		$("#hidden-contacts").val());

		var owner		=$("#search-owner"	).val();
		var name		=$("#search-name"	).val();
		var customer		=$("#search-customer"		).val();
		var source		=$("#search-source"		).val();
		var stage		=$("#hidden-stage").val();
		var type		=$("#search-type"		).val();
		var contacts	=$("#search-contacts"	).val();


		$.ajax({
			url:"workbench/transaction/pageList.do",
			data:{
				"owner" 	:owner,
				"name"  	:name	,
				"customer"    	:customer	,
				"source"	:source	,
				"stage":stage,
				"type"	:type	,
				"contacts"		:contacts,
				"pageNo"	:pageNo,
				"pageSize"	:pageSize

			},
			type:"post",
			dataType:"json",
			success:function (data) {

				var html="";
				$.each(data.dataList,function (i,n) {
					html+='<tr class="active">';
					html+='<td><input type="checkbox" name="xz" value='+n.id+'/></td>';
					html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/transaction/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					html+='<td>'+n.customerId+'</td>';
					html+='<td>'+n.stage+'</td>';
					html+='<td>'+n.type+'</td>';
					html+='<td>'+n.owner+'</td>';
					html+='<td>'+n.source+'</td>';
					html+='<td>'+n.contactsId+'</td>';
					html+='</tr>';
				});
				$("#tranBody").html(html);


				var totalPages=(data.total%pageSize)==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
				$("#tranPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						$("#qx")[0].checked=false;
						pageList(data.currentPage , data.rowsPerPage);
					}
				});


			}
		})


	}
	
</script>
</head>
<body>

<input type="hidden" id="hidden-owner">
<input type="hidden" id="hidden-name">
<input type="hidden" id="hidden-customer">
<input type="hidden" id="hidden-source">
<input type="hidden" id="hidden-stage">
<input type="hidden" id="hidden-type">
<input type="hidden" id="hidden-contacts">

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="search-customer">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="search-stage">
					  	<option></option>
<%--					  	<option>资质审查</option>--%>
<%--					  	<option>需求分析</option>--%>
<%--					  	<option>价值建议</option>--%>
<%--					  	<option>确定决策者</option>--%>
<%--					  	<option>提案/报价</option>--%>
<%--					  	<option>谈判/复审</option>--%>
<%--					  	<option>成交</option>--%>
<%--					  	<option>丢失的线索</option>--%>
<%--					  	<option>因竞争丢失关闭</option>--%>
						  <c:forEach items="${stage}" var="s">
							  <option value="${s.value}">${s.text}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="search-type">
					  	<option></option>
<%--					  	<option>已有业务</option>--%>
<%--					  	<option>新业务</option>--%>
						  <c:forEach items="${transactionType}" var="t">
							  <option value="${t.value}">${t.text}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="search-source">
						  <option></option>
<%--						  <option>广告</option>--%>
<%--						  <option>推销电话</option>--%>
<%--						  <option>员工介绍</option>--%>
<%--						  <option>外部介绍</option>--%>
<%--						  <option>在线商场</option>--%>
<%--						  <option>合作伙伴</option>--%>
<%--						  <option>公开媒介</option>--%>
<%--						  <option>销售邮件</option>--%>
<%--						  <option>合作伙伴研讨会</option>--%>
<%--						  <option>内部研讨会</option>--%>
<%--						  <option>交易会</option>--%>
<%--						  <option>web下载</option>--%>
<%--						  <option>web调研</option>--%>
<%--						  <option>聊天</option>--%>
						  <c:forEach items="${source}" var="s">
							  <option value="${s.value}">${s.text}</option>
						  </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="search-contacts">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/add.do';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='workbench/transaction/edit.do';"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tranBody">
<%--						<tr>--%>
<%--							<td><input type="checkbox" name="xz"/></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>--%>
<%--							<td>动力节点</td>--%>
<%--							<td>谈判/复审</td>--%>
<%--							<td>新业务</td>--%>
<%--							<td>zhangsan</td>--%>
<%--							<td>广告</td>--%>
<%--							<td>李四</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" name="xz"/></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>--%>
<%--                            <td>动力节点</td>--%>
<%--                            <td>谈判/复审</td>--%>
<%--                            <td>新业务</td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>广告</td>--%>
<%--                            <td>李四</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 20px;">
				<div id="tranPage">

				</div>
			</div>
			
		</div>
		
	</div>
</body>
</html>