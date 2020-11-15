<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>


	<script type="text/javascript">

	$(function(){

		pageList(1,$("#pageSize").val())

		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});


		$("#createBtn").click(function () {
			$.ajax({
				url:"workbench/activity/getUsers.do",
				type:"get",
				dataType:"json",
				success:function (data){
					var html = ""
					$.each(data,function (i,n){
						html += "<option value='"+n.id+"'>"+n.name+"</option>"
					})
					$("#create-marketActivityOwner").html(html)
					$("#create-marketActivityOwner").val("${user.id}")
				}
			})

			$("#createActivityModal").modal("show")
		})

		$("#saveBtn").click(function (){
			$.ajax({
				url:"workbench/activity/saveActivity.do",
				data:{
					"owner" : $.trim($("#create-marketActivityOwner").val()),
					"name" : $.trim($("#create-marketActivityName").val()),
					"startDate" : $.trim($("#create-startTime").val()),
					"endDate" : $.trim($("#create-endTime").val()),
					"cost" : $.trim($("#create-cost").val()),
					"description" : $.trim($("#create-describe").val())
				},
				type:"get",
				dataType:"json",
				success:function (data){
					if(data.success){
						$("#activityCreateForm")[0].reset()
						$("#createActivityModal").modal("hide")
						pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'))
					}else{
						alert(data.msg)
					}
				}
			})
		})

		$("#searchBtn").click(function (){
			$("#hidden-name").val($.trim($("#search-name").val()))
			$("#hidden-owner").val($.trim($("#search-owner").val()))
			$("#hidden-startDate").val($.trim($("#startTime").val()))
			$("#hidden-endDate").val($.trim($("#endTime").val()))
			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'))
		})
		
		$("#qx").click(function () {
			$("input[name=xz]").prop("checked",this.checked);
		})
		$("#activityBody").on("click",$("input[name=xz]"),function() {
			$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
		})
		
		$("#deleteBtn").click(function () {
			var $xz = $("input[name=xz]:checked");
			if($xz.length == 0){
				alert("请选择需要删除的记录")
			}else{
				if (confirm("确定删除所选中的记录吗？")){
					var param = ""
					for (var i=0;i<$xz.length;i++){
						param += "id="+$($xz[i]).val()
						if (i<$xz.length-1){
							param += "&"
						}
					}
					$.ajax({
						url:"workbench/activity/deleteActivityByIds.do",
						data:param,
						type:"post",
						dataType:"json",
						success:function (data){
							if (data.success){
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'))
							}else{
								alert(data.msg)
							}
						}
					})
				}
			}
		})

		$("#editBtn").click(function () {
			var $xz = $("input[name=xz]:checked");
			if($xz.length == 0){
				alert("请选择需要修改的记录")
			}else if($xz.length > 1){
				alert("一次只能修改一条记录")
			}else{
				$.ajax({
					url:"workbench/activity/getUsersAndActivityById.do",
					data:{
						"id" : $xz.val()
					},
					type:"post",
					dataType:"json",
					success:function (data){
						var html = ""
						$.each(data.users,function (i,n){
							html += "<option value='"+n.id+"'>"+n.name+"</option>"
						})
						$("#edit-marketActivityOwner").html(html)
						$("#edit-marketActivityId").val(data.activity.id)
						$("#edit-marketActivityOwner").val(data.activity.owner)
						$("#edit-marketActivityName").val(data.activity.name)
						$("#edit-startTime").val(data.activity.startDate)
						$("#edit-endTime").val(data.activity.endDate)
						$("#edit-cost").val(data.activity.cost)
						$("#edit-describe").val(data.activity.description)
						$("#editActivityModal").modal("show")
					}
				})
			}
		})

		$("#updateBtn").click(function () {
			$.ajax({
				url:"workbench/activity/updateActivity.do",
				data:{
					"id" : $("#edit-marketActivityId").val(),
					"owner" : $("#edit-marketActivityOwner").val(),
					"name" : $.trim($("#edit-marketActivityName").val()),
					"startDate" : $.trim($("#edit-startTime").val()),
					"endDate" : $.trim($("#edit-endTime").val()),
					"cost" : $.trim($("#edit-cost").val()),
					"description" : $.trim($("#edit-describe").val())
				},
				type:"post",
				dataType:"json",
				success:function (data){
					if (data.success){
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						$("#editActivityModal").modal("hide")
					}else{
						alert(data.msg)
					}
				}
			})
		})
	})

	function pageList(pageNo,pageSize){
		$("#qx").prop("checked",false)
		$("#search-name").val($.trim($("#hidden-name").val()))
		$("#search-owner").val($.trim($("#hidden-owner").val()))
		$("#startTime").val($.trim($("#hidden-startDate").val()))
		$("#endTime").val($.trim($("#hidden-endDate").val()))
		$.ajax({
			url:"workbench/activity/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-owner").val()),
				"startDate":$.trim($("#startTime").val()),
				"endDate":$.trim($("#endTime").val())
			},
			type:"get",
			dataType:"json",
			success:function (data){
				var html = ""
				$.each(data.dataList,function (i,n) {
					html += '<tr class="active">'
					html += '	<td><input type="checkbox" value="'+n.id+'" name="xz"/></td>'
					html += '	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/selectActivityById.do?id='+n.id+'\';">'+n.name+'</a></td>'
					html += '	<td>'+n.owner+'</td>'
					html += '	<td>'+n.startDate+'</td>'
					html += '	<td>'+n.endDate+'</td>'
					html += '</tr>'
				})
				if (data.dataList.length <pageSize){
					for(var i=0;i<pageSize-data.dataList.length;i++){
						html += '<tr class="active">'
						html += '	<td><input type="checkbox" disabled/></td>'
						html += '	<td><a style="text-decoration: none; cursor: pointer;"></a></td>'
						html += '	<td></td>'
						html += '	<td></td>'
						html += '	<td></td>'
						html += '</tr>'
					}
				}
				$("#activityBody").html(html)
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: data.total%pageSize==0?data.total/pageSize:parseInt(data.total / pageSize)+1, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})
	}

	$.fn.datetimepicker.dates['zh-CN'] = {
		days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
		daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
		daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
		months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
		today: "今天",
		suffix: [],
		meridiem: ["上午", "下午"]
	};

	var rsc_bs_pag = {
		go_to_page_title: 'Go to page',
		rows_per_page_title: 'Rows per page',
		current_page_label: 'Page',
		current_page_abbr_label: 'p.',
		total_pages_label: 'of',
		total_pages_abbr_label: '/',
		total_rows_label: 'of',
		rows_info_records: 'records',
		go_top_text: '上一页',
		go_prev_text: '首页',
		go_next_text: '下一页',
		go_last_text: '末页'
	};
	
</script>
</head>
<body>

	<input type="hidden" id="pageSize" value="6">
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="activityCreateForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-marketActivityId"/>
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn" >更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="endTime">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">

					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>