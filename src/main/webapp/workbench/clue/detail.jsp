<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/datatime.js"></script>


	<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){

		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "top-left"
		});

		showRemarkList()
		showRelationList()

		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});

		$("#remark").blur(function(){
			if(!cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","90px");
				//显示
				$("#cancelAndSaveBtn").hide("2000");
				cancelAndSaveBtnDefault = true;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});

		$("#remarkBody").on("mouseover",".remarkDiv",function () {
			$(this).children("div").children("div").show();
		})
		$("#remarkBody").on("mouseout",".remarkDiv",function (){
			$(this).children("div").children("div").hide();
		})
		$("#remarkBody").on("mouseover",".myHref",function () {
			$(this).children("span").css("color","red");
		})
		$("#remarkBody").on("mouseout",".myHref",function () {
			$(this).children("span").css("color","#E6E6E6");
		})

		$("#deleteBtn").click(function () {
			if(confirm("确定要删除该线索吗？")){
				$.ajax({
					url:"workbench/clue/deleteClueByIds.do",
					data:{
						"id":"${clue.id}"
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if (data.success){
							window.location.replace(document.referrer);
						}else{
							alert(data.msg)
						}
					}
				})
			}
		})

		$("#editBtn").click(function () {
			$.ajax({
				url:"workbench/clue/getUsersAndClueById.do",
				data:{
					id:"${clue.id}",
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					var html = ""
					$.each(data.users,function (i,n) {
						html += '<option value="'+n.id+'">'+n.name+'</option>'
					})
					$("#edit-clueOwner").html(html)
					$("#edit-clueOwner").val(data.clue.owner)
					$("#edit-clueId").val(data.clue.id)
					$("#edit-company").val(data.clue.company)
					$("#edit-call").val(data.clue.appellation)
					$("#edit-surname").val(data.clue.fullname)
					$("#edit-job").val(data.clue.job)
					$("#edit-email").val(data.clue.email)
					$("#edit-phone").val(data.clue.phone)
					$("#edit-website").val(data.clue.website)
					$("#edit-mphone").val(data.clue.mphone)
					$("#edit-status").val(data.clue.state)
					$("#edit-source").val(data.clue.source)
					$("#edit-describe").val(data.clue.description)
					$("#edit-contactSummary").val(data.clue.contactSummary)
					$("#edit-nextContactTime").val(data.clue.nextContactTime)
					$("#edit-address").val(data.clue.address)
					$("#editClueModal").modal("show")
				}
			})
		})

		$("#updateBtn").click(function () {
			$.ajax({
				url:"workbench/clue/updateClue.do",
				data:{
					"id":$.trim($("#edit-clueId").val()),
					"fullname":$.trim($("#edit-surname").val()),
					"appellation":$.trim($("#edit-call").val()),
					"owner":$.trim($("#edit-clueOwner").val()),
					"company":$.trim($("#edit-company").val()),
					"job":$.trim($("#edit-job").val()),
					"email":$.trim($("#edit-email").val()),
					"phone":$.trim($("#edit-phone").val()),
					"website":$.trim($("#edit-website").val()),
					"mphone":$.trim($("#edit-mphone").val()),
					"state":$.trim($("#edit-status").val()),
					"source":$.trim($("#edit-source").val()),
					"description":$.trim($("#edit-describe").val()),
					"contactSummary":$.trim($("#edit-contactSummary").val()),
					"nextContactTime":$.trim($("#edit-nextContactTime").val()),
					"address":$.trim($("#edit-address").val())
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if (data.success){
						$("#editClueModal").modal("hide")
						location.reload();
					}else{
						alert(data.msg)
					}
				}
			})
		})

		$("#createRemarkBtn").click(function () {
			$.ajax({
				url:"workbench/clue/saveClueRemark.do",
				data:{
					"noteContent":$.trim($("#remark").val()),
					"clueId":"${clue.id}"
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if (data.success){
						var html = ""
						html+='<div class="remarkDiv" style="height: 60px;" id="'+data.clueRemark.id+'">'
						html+='	<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">'
						html+='		<div style="position: relative; top: -40px; left: 40px;" >'
						html+='			<h5>'+data.clueRemark.noteContent+'</h5>'
						html+='			<font color="gray">线索</font> <font color="gray">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style="color: gray;"> '+(data.clueRemark.editFlag==0?data.clueRemark.createTime:data.clueRemark.editTime)+' 由'+(data.clueRemark.editFlag==0?data.clueRemark.createBy:data.clueRemark.editBy)+'</small>'
						html+='			<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">'
						html+='				<a class="myHref" href="javascript:void(0); " onclick="editRemark(\''+data.clueRemark.id+'\',\''+data.clueRemark.noteContent+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>'
						html+='				&nbsp;&nbsp;&nbsp;&nbsp;'
						html+='				<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+data.clueRemark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>'
						html+='			</div>'
						html+='		</div>'
						html+='</div>'
						$("#remarkDiv").before(html)
						$("#remark").val("")
					}else{
						alert(data.msg)
					}
				}
			})
		})

		$("#updateRemarkBtn").click(function () {
			$.ajax({
				url:"workbench/clue/updateRemark.do",
				data:{
					"id":$("#remarkId").val(),
					"noteContent":$.trim($("#noteContent").val())
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if (data.success){
						var html = ""
						html+='	<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">'
						html+='		<div style="position: relative; top: -40px; left: 40px;" >'
						html+='			<h5>'+data.clueRemark.noteContent+'</h5>'
						html+='			<font color="gray">线索</font> <font color="gray">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style="color: gray;"> '+(data.clueRemark.editFlag==0?data.clueRemark.createTime:data.clueRemark.editTime)+' 由'+(data.clueRemark.editFlag==0?data.clueRemark.createBy:data.clueRemark.editBy)+'</small>'
						html+='			<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">'
						html+='				<a class="myHref" href="javascript:void(0); " onclick="editRemark(\''+data.clueRemark.id+'\',\''+data.clueRemark.noteContent+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>'
						html+='				&nbsp;&nbsp;&nbsp;&nbsp;'
						html+='				<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+data.clueRemark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>'
						html+='			</div>'
						html+='		</div>'
						$("#"+data.clueRemark.id).html(html)
						$("#editRemarkModal").modal("hide")
					}else{
						alert(data.msg)
					}
				}
			})
		})
		
		
		$("#bundBtn").click(function () {
			$("#aname").val("")
			$("#bundModal").modal("show")
			getActivity()
		})

		$("#aname").keydown(function (event) {
			if (event.keyCode==13){
				getActivity()
				return false
			}

		})

		$("#qx").click(function (){
			$("input[name=xz]").prop("checked",this.checked)
		})
		$("#actBody").on("click",$("input[name=xz]"),function () {
			$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
		})

		$("#bundACBtn").click(function () {
			var $xz = $("input[name=xz]:checked")
			if($xz == 0){
				alert("至少选择一条市场活动进行关联！")
			}else{
				var param = "clueId=${clue.id}&"
				for (var i=0;i<$xz.length;i++){
					param+=("activityId="+$($xz[i]).val())
					if(i<$xz.length){
						param+="&"
					}
				}
				$.ajax({
					url:"workbench/clue/bundActClue.do",
					data:param,
					type:"post",
					dataType:"json",
					success:function (data) {
						if (data.success){
							$("#bundModal").modal("hide")
							showRelationList()
						}else{
							alert("关联市场活动失败!")
						}
					}
				})
			}
		})

	});

	function showRemarkList() {
		$.ajax({
			url:"workbench/clue/getRemarkListByCid.do",
			data:{
				"clueId":"${clue.id}"
			},
			type:"post",
			dataType:"json",
			success:function (data) {
				var html = ""
				$.each(data,function (i,n) {
					html+='<div class="remarkDiv" style="height: 60px;" id="'+n.id+'">'
					html+='	<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">'
					html+='		<div style="position: relative; top: -40px; left: 40px;" >'
					html+='			<h5>'+n.noteContent+'</h5>'
					html+='			<font color="gray">线索</font> <font color="gray">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style="color: gray;"> '+(n.editFlag==0?n.createTime:n.editTime)+' 由'+(n.editFlag==0?n.createBy:n.editBy)+'</small>'
					html+='			<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">'
					html+='				<a class="myHref" href="javascript:void(0); " onclick="editRemark(\''+n.id+'\',\''+n.noteContent+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>'
					html+='				&nbsp;&nbsp;&nbsp;&nbsp;'
					html+='				<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+n.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>'
					html+='			</div>'
					html+='		</div>'
					html+='</div>'
				})
				$("#remarkDiv").before(html)
			}
		})
	}

	function showRelationList() {
		$.ajax({
			url:"workbench/clue/getRelationListByCid.do",
			data:{
				"clueId":"${clue.id}"
			},
			type:"post",
			dataType:"json",
			success:function (data) {
				var html = ""
				$.each(data,function (i,n) {
					html+='<tr>'
					html+='	<td>'+n.name+'</td>'
					html+='	<td>'+n.startDate+'</td>'
					html+='	<td>'+n.endDate+'</td>'
					html+='	<td>'+n.owner+'</td>'
					html+='	<td><a href="javascript:void(0);"  style="text-decoration: none;" onclick="deleteRelation(\''+n.id+'\')"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>'
					html+='</tr>'
				})
				$("#ACRelBody").html(html);
			}
		})
	}

	function editRemark(id,noteContent) {
		$("#noteContent").val(noteContent)
		$("#remarkId").val(id)
		$("#editRemarkModal").modal("show")
	}

	function deleteRemark(id) {
		if (confirm("确定要删除该备注？")){
			$.ajax({
				url:"workbench/clue/deleteRemarkById.do",
				data:{
					"id":id,
				},
				type:"get",
				dataType:"json",
				success:function(data){
					if(data.success){
						$("#"+id).remove()
					}else{
						alert(data.msg)
					}
				}
			})
		}
	}

	function deleteRelation(id) {
		if(confirm("确定要解除该市场活动关联吗？")){
			$.ajax({
				url:"workbench/clue/deleteRelationByCAId.do",
				data:{
					"activityId":id,
					"clueId":"${clue.id}"
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if (data.success){
						showRelationList()
					}else{
						alert(data.msg)
					}
				}
			})
		}
	}

	function getActivity() {
		$("#qx").prop("checked",false)
		$.ajax({
			url:"workbench/clue/getActivityByNameAndNotClueId.do",
			data:{
				"name":$.trim($("#aname").val()),
				"clueId":"${clue.id}"
			},
			type:"post",
			dataType:"json",
			success:function (data) {
				var html = ""
				$.each(data,function (i,n) {
					html += '<tr>'
					html += '	<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>'
					html += '	<td>'+n.name+'</td>'
					html += '	<td>'+n.startDate+'</td>'
					html += '	<td>'+n.endDate+'</td>'
					html += '	<td>'+n.owner+'</td>'
					html += '</tr>'
				})
				$("#actBody").html(html)
			}
		})
	}
</script>

</head>
<body>

	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
		<div class="modal-dialog" role="document" style="width: 40%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">修改备注</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">内容</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="noteContent"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 关联市场活动的模态窗口 -->
	<div class="modal fade" id="bundModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">关联市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询" id="aname">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td><input type="checkbox" id="qx"/></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="actBody">

						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="bundACBtn">关联</button>
				</div>
			</div>
		</div>
	</div>

    <!-- 修改线索的模态窗口 -->
    <div class="modal fade" id="editClueModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 90%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改线索</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
						<input type="hidden" id="edit-clueId">
                        <div class="form-group">
                            <label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-clueOwner">

                                </select>
                            </div>
                            <label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-company">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-call" class="col-sm-2 control-label">称呼</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-call">
                                    <option></option>
									<c:forEach items="${appellation}" var="a">
										<option value="${a.value}">${a.text}</option>
									</c:forEach>
                                </select>
                            </div>
                            <label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-surname">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-job" class="col-sm-2 control-label">职位</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-job" value="CTO">
                            </div>
                            <label for="edit-email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-email">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-phone">
                            </div>
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-mphone" class="col-sm-2 control-label">手机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-mphone">
                            </div>
                            <label for="edit-status" class="col-sm-2 control-label">线索状态</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-status">
                                    <option></option>
									<c:forEach items="${clueState}" var="c">
										<option value="${c.value}">${c.text}</option>
									</c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-source" class="col-sm-2 control-label">线索来源</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-source">
                                    <option></option>
									<c:forEach items="${source}" var="sou">
										<option value="${sou.value}">${sou.text}</option>
									</c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control time" id="edit-nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${clue.fullname}${clue.appellation} <small>${clue.company}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" onclick="window.location.href='workbench/clue/convertClue.do?id=${clue.id}'"><span class="glyphicon glyphicon-retweet"></span> 转换</button>
			<button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.fullname}${clue.appellation}&nbsp;&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.owner}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">公司</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.company}&nbsp;&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">职位</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.job}&nbsp;&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">邮箱</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.email}&nbsp;&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.phone}&nbsp;&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">公司网站</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.website}&nbsp;&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.mphone}&nbsp;&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">线索状态</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.state}&nbsp;&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">线索来源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.source}&nbsp;&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.createTime}&nbsp;&nbsp;</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.editTime}&nbsp;&nbsp;</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.description}&nbsp;&nbsp;
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.contactSummary}&nbsp;&nbsp;
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.nextContactTime}&nbsp;&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 100px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                    ${clue.address}&nbsp;&nbsp;
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 40px; left: 40px;" id="remarkBody">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" id="createRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 市场活动 -->
	<div>
		<div style="position: relative; top: 60px; left: 40px;">
			<div class="page-header">
				<h4>市场活动</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>所有者</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="ACRelBody">
						
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0);" id="bundBtn" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
			</div>
		</div>
	</div>
	
	
	<div style="height: 200px;"></div>
</body>
</html>