 //页面加载用户信息
$(document).ready(function () {
	var addParam = {};
	$.ajax({
			cache: true,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: '/serviceBP/resources',
			data: JSON.stringify(addParam),
			async: false,
			success: function(result) {
			   renderData1(result.data);
			}
          });

});

//页面加载拼接数据
function renderData1(data) {
    var list  = data.list;
    var html = '';
    $("#show_count").html('共有数据：'+data.total+'条');

    for ( var a in list){
        var reId = list[a].id;
        var reName = list[a].name||'';
        var reUrl = list[a].resurl||'';
        var reType = list[a].type||'';
		var reParentId = list[a].parentid||'';
		html += '<tr f="tr" name="tr" id="tr">';
       
        html += '<td f="td" class="text-center"><input type="checkbox" f="checkbox" class="checkchild" value = "'+reId+'"></td>';
        html += '<td f="td">'+reId+ 	'</td>';
        html += '<td f="td">'+reName+ 	'</td>';
		html += '<td f="td">'+reParentId+ 	'</td>';
		html += '<td f="td">'+reUrl+ 	'</td>';
		if(reType == 1){
			html += '<td f="td"><font color="red">一级菜单</font></td>';
		}else if(reType == 2){
			html += '<td f="td"><font color="green">二级菜单</font></td>';
		}else if(reType == 3){
			html += '<td f="td"><font color="blue">三级菜单</font></td>';
		}else{
			html += '<td f="td"><font color="black">其他</font></td>';
		}
		html += '<td f="td" class="text-primary"><a href="javascript:;" onclick="resource_show('+ reId +')" f="a" class="mgr-sm text-primary">查看</a>|<a href="javascript:;" onclick="find_resource('+ reId +')" f="a" class="mgl-sm text-primary">修改</a></td>'
     
        html += '</tr>';
    }
    $("#sList").html(html);
	fenye(data);
}

function fenye(data){
	$('#pageList').jqPaginator({
		totalPages: data.pages,
		visiblePages: 3,
		currentPage: data.pageNum,
		first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
		prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
		next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
		last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
		page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
		onPageChange: function (num, type) {
			var resId = $("#resId").val();
			var resName = $("#resName").val();
			var resParent = $("#resParent").val();
			var resUrl = $("#resUrl").val();
			var resType = $("#resType").val();
			var param = {id:resId,name:resName,parentid:resParent,resurl:resUrl,type:resType,PAGE_NUM:num};
			if (type == 'change'){
				$.ajax({
					async: false,
					type: "POST",
					contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/resources',
					data: JSON.stringify(param),
					dataType: "json",
					success: function (result) {
						if(result.message == "success"){
							renderData1(result.data);
						} else {
							alert('数据异常');
						}						
					},
					error: function () {
					}
				});
			}

		}
	});
}
//页面查看按钮
function resource_show(reid){
	var findre = {id : reid};
	  //修改用户的js
	var editTpl = $('#tpl-resourceLook').html();
        event.preventDefault();
    	dialog({
            title: '资源查看',
            content: editTpl,
            cancelValue: '取消', 
            cancel: function () {},	//取消回调
            onshow: function(){		//弹窗显示回调
			$.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/resources/findResourceById',
                    /* data:$('#userForm').serialize(),// 你的formid */
                    data: JSON.stringify(findre),
                    // 你的formid
                    async: false,
                    success: function(result) {
				    $("#reId").val(result.data.name);
				    $("#rePar").val(result.data.parentid);
				    $("#reUrl").val(result.data.resurl);
				    $("#reType").val(result.data.type);
               
                    }
          });
	
            }
    	}).show();
    
}

//页面修改按钮
function find_resource(reid){
	
	var findre = {id : reid};
	  //修改用户的js
	var editTpl = $('#tpl-resourceupdate').html();
        event.preventDefault();
    	dialog({
            title: '修改',
            content: editTpl,
            okValue: '确定',
            ok: function () {
            var resName = $("#reId").val();
			var resPanentId = $("#rePar").val();
			var resUrl = $("#reUrl").val();
			var resType = $("#reType").val();
			var param = {id:reid,name:resName,resurl:resUrl,type:resType,parentid:resPanentId};
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/resources/updateResource',
				data: JSON.stringify(param),
				dataType: "json",
				success: function (result) {
					if(result.message == "fail"){
						alert("参数错误，请重新输入！");
					}else if (result.message == "success"){
						alert("修改成功");
						dialog('close');
						window.location.reload();
					}
				},
				error: function () {
				}
					
			});
            },	//确定回调
            cancelValue: '取消', 
            cancel: function () {},	//取消回调
            onshow: function(){		//弹窗显示回调
			$.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/resources/findResourceById',
                    data: JSON.stringify(findre),
                    async: false,
                    success: function(result) {
                    $("#reId").val(result.data.name);
					$("#rePar").val(result.data.parentid);
					$("#reUrl").val(result.data.resurl);
					$("#reType").val(result.data.type);
                    }
          });
	
            }
    	}).show();
    

}

//页面查询按钮
$("#subRes").on('click',function(){
	  var resId = $("#resId").val();
	  var resName = $("#resName").val();
	   var resParent = $("#resParent").val();
	  var resUrl = $("#resUrl").val();
	   var resType = $("#resType").val();
	  var param = {id:resId,name:resName,parentid:resParent,resurl:resUrl,type:resType};
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json; charset=utf-8",
			url: '/serviceBP/resources',
			data: JSON.stringify(param),
            dataType: "json",
            success: function (result) {
                renderData1(result.data);
            },
            error: function () {
            }
        })
    });
	
//页面删除按钮
$("#subDel").on('click',function(){
	var id = $(".checkchild:checked").val();
	if ($(".checkchild:checked").length < 1) {
		alert("请选择一条数据");
		return;
	}
	if ($(".checkchild:checked").length > 1) {
		alert("一次只能修改一条数据");
		return;
	}
	if (confirm("确定删除吗")) {
		$.ajax({
			cache: true,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: '/serviceBP/resources/delete',
			data: JSON.stringify({
				id: id
			}),
			async: false,
			success: function(data) {
				if (data.message == "success") {
					window.location.reload();
				} else {
					alert('删除失败');
				}
			}
		});
	} 
	
	
	
 });
 
 

 
	
//页面添加按钮
$('#resource-add').click(function(){
	var resadd = $('#tpl-resourceAdd').html();
	dialog({
		title: '添加资源',
		content: resadd,
		okValue: '确定',  
		ok: function () {
		var resName = $("#reId").val();
		var resPanentId = $("#rePar").val();
		var resUrl = $("#reUrl").val();
		var resType = $("#reType").val();
		if(resName == "" || resName == undefined || resName == null){
			alert("资源名不能为空");
			return false;
		}
		if(resPanentId == "" || resPanentId == undefined || resPanentId == null){
			alert("父资源不能为空");
			return false;
		}
		if(resUrl == "" || resUrl == undefined || resUrl == null){
			alert("资源路径不能为空");
			return false;
		}
		if(resType == "" || resType == undefined || resType == null){
			alert("资源类型不能为空");
			return false;
		}
		var param = {name:resName,resurl:resUrl,type:resType,parentid:resPanentId};
	$.ajax({
		async: false,
		type: "POST",
		contentType: "application/json; charset=utf-8",
		url: '/serviceBP/resources/add',
		data: JSON.stringify(param),
		dataType: "json",
		success: function (result) {
			if(result.message == "success"){
			alert("添加成功");
			dialog('close');
			window.location.reload();
		}else if(result.message == "fail"){
			alert("参数异常，请重新输入！");
			
		}else{
			alert("服务器错误，请重新输入！");
			dialog('close');
		}
			
			
		},
		error: function () {
		}
	})
		
			
		},	//确定回调
		cancelValue: '取消', 
		cancel: function () {}	//取消回调
	}).show();
});

    
    
//查看用户的js
var lookTpl = $('#tpl-look').html();
$('#table').on('click','.js_look',function(event){
	event.preventDefault();
	dialog({
		title: '查看',
		content: lookTpl,
		okValue: '确定',
		ok: function () {
			var editForm = $('#edit-form');
			 var username = editForm.find('.js_userName').val();
			
		},	//确定回调
		cancelValue: '取消', 
		cancel: function () {},	//取消回调
		onshow: function(){		//弹窗显示回调
			var editForm = $('#edit-form');
			editForm.find('.js_userName').val("董开旺");
			editForm.find('.js_pwd').val('董开旺是笨蛋');
			var username = editForm.find('.js_userName').val();
			if(username == "wj"){
				editForm.find('[name="radio-box_3"][value="'+ 1 +'"]')[0].checked = true;
			}else{
				editForm.find('[name="radio-box_3"][value="'+ 0 +'"]')[0].checked = true;
			}
			
		}
	}).show();
});
    
//查看用户角色信息
var html = $('#tpl-fenpei').html();
$('#user-fenpei').click(function(){
	
var id = $(".checkchild:checked").val();
var p = {
	id: id
};
if ($(".checkchild:checked").length < 1) {
	alert("请选择一条数据3");
	return;
}
if ($(".checkchild:checked").length > 1) {
	alert("一次只能修改一条数据");
	return;
}


dialog({
		title: '角色信息',
		content: html,
		okValue: '确定',
		ok: function () {
		 
			var e = $('#findUserRole');
			var roleids = $('input[name="roleid"]'); 
			var userId =  e.find('#userId').val();
			/**var roleids = e.find('[name="roleid"]').val();**/
			var roleIds = new Array();
			for (var i = 0; i < roleids.length; i++) {
				if (roleids[i].checked) {
					roleIds.push(roleids[i].value);
				}

			}
			$.ajax({
				cache: true,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/users/saveUserRoles',
				data: JSON.stringify({
					roleid: roleIds.join(","),
					userid: userId
				}),
				// 你的formid
				async: false,
				success: function(data) {
					if (data.message == "success") {
						alert("保存成功");
						window.location.reload();
					   
					} else {
						alert("保存失败");
						window.location.reload();
					}
				}
			})
		
		  
		  
		  
		  
		  
		  
		},	//确定回调
		cancelValue: '取消', 
		cancel: function () {},	//取消回调
		onshow: function(){		//弹窗显示回调
		$.ajax({
			async: false,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(p),
			url: '/serviceBP/roles/rolesWithSelected',
			dataType: 'json',
			success: function(data) {
				$("#findUserRole").empty();
				var htm = "<input type='hidden'  id = 'userId' name='userid' value='" + id + "'>";
				for (var i = 0; i < data.data.length; i++) {
					htm += "<div class='checkbox'><label><input type='checkbox' id = 'roleId' name='roleid' value='" + data.data[i].id + "'";
					if (data.data[i].selected == 1) {
						htm += " checked='checked'";
					}
					htm += "/>" + data.data[i].roledesc + "</label></div>";
				}
				$("#findUserRole").append(htm);
			}
		});	
			
		}
	}).show();

});
    
	
	
	
 
 
 

