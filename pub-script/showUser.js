//页面加载用户信息
$(document).ready(function () {
	var addParam = {};
	$.ajax({
				cache: true,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/users',
				/* data:$('#userForm').serialize(),// 你的formid */
				data: JSON.stringify(addParam),
				// 你的formid
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
        var uid = list[a].id;
        var username = list[a].username||'';
        var password = list[a].password||'';
        var enable = list[a].enable||'';
		html += '<tr f="tr" name="tr" id="tr">';
       
        html += '<td f="td" class="text-center"><input type="checkbox" f="checkbox" class="checkchild" value = "'+uid+'"></td>';
        html += '<td f="td">'+uid+ 	'</td>';
        html += '<td f="td">'+username+ 	'</td>';
		if(enable == 1){
			html += '<td f="td"><i class="effective" f="font-icon"></i></td>';
		}else{
			 html += '<td f="td"><i class="effective no-effective" f="font-icon"></i></td>';
		}
       
        html += '<td f="td">'+password+	'</td>';
		html += '<td f="td" class="text-primary"><a href="javascript:;" onclick="user_show('+ uid +')" f="a" class="mgr-sm text-primary">查看</a>|<a href="javascript:;" onclick="find_user('+ uid +')" f="a" class="mgl-sm text-primary">修改</a></td>'
     
        html += '</tr>';
    }
	

    $("#sList").html(html);
	//fenye(data);
	commonfunction(data);
}
//用户分页信息
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
			var uid = $("#userId").val();
			var uname = $("#uName").val();
			var param = {id:uid,username:uname,PAGE_NUM:num};
			if (type == 'change'){
				$.ajax({
					async: false,
					type: "POST",
					contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/users',
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
function user_show(uid){
	var findu = {id : uid};
	var editTpl = $('#tpl-look').html();
        event.preventDefault();
    	dialog({
            title: '修改',
            content: editTpl,
            cancelValue: '取消', 
            cancel: function () {},	//取消回调
            onshow: function(){		//弹窗显示回调
			$.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/users/findUserByUid',
                    data: JSON.stringify(findu),
                    async: false,
                    success: function(result) {
                        var editForm = $('#edit-form');
						editForm.find('.js_userName').val(result.data.username);
						editForm.find('.js_pwd').val(result.data.password);
						var username = editForm.find('.js_userName').val();
						if(result.data.enable == 1){
							$('input[name="enAbleName"]:checked').val(); 
							editForm.find('[name="radio-box_3"][value="'+ 1 +'"]')[0].checked = true;
						}else{
							editForm.find('[name="radio-box_3"][value="'+ 0 +'"]')[0].checked = true;
						}
                    }
				});
            }
    	}).show();
    
}

//页面修改按钮
function find_user(uid){
	var findu = {id : uid};
	var editTpl = $('#find-user').html();
        event.preventDefault();
    	dialog({
            title: '修改',
            content: editTpl,
            okValue: '确定',
            ok: function () {
                var editForm = $('#edit-form');
            	var username = editForm.find('.js_userName').val();
				var password = editForm.find('.js_pwd').val();
				var enable = $('input[name="radio-box_3"]:checked').val();
				var param = {id:uid,username:username,password:password,enable:enable};
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/users/update',
				data: JSON.stringify(param),
				dataType: "json",
				success: function (result) {
					if(result.message == "fail"){
						alert("用户名或密码必须同时修改！");
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
                    url: '/serviceBP/users/findUserByUid',
                    /* data:$('#userForm').serialize(),// 你的formid */
                    data: JSON.stringify(findu),
                    // 你的formid
                    async: false,
                    success: function(result) {
                       var editForm = $('#edit-form');
                editForm.find('.js_userName').val(result.data.username);
            	editForm.find('.js_pwd').val(result.data.password);
                var username = editForm.find('.js_userName').val();
                if(result.data.enable == 1){
					
                    editForm.find('[name="radio-box_3"][value="'+ 1 +'"]')[0].checked = true;
                }else{
                    editForm.find('[name="radio-box_3"][value="'+ 0 +'"]')[0].checked = true;
                }
                    }
          });
	
            }
    	}).show();
    

}

//页面查询按钮
$("#subId").on('click',function(){
	  var uid = $("#userId").val();
	  var uname = $("#uName").val();
	  var param = {id:uid,username:uname};
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json; charset=utf-8",
			url: '/serviceBP/users',
			data: JSON.stringify(param),
            dataType: "json",
            success: function (result) {
                renderData1(result.data);
            },
            error: function () {
				alert("数据类型填写错误");
            }
        })
    });
	

	
//页面删除按钮
$("#delId").on('click',function(){
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
			url: '/serviceBP/users/delete',
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
	} else {
		alert("false");
	}
 });
 
 //页面改变状态按钮
 $("#user-change").on('click',function(){ 
	var uid = $(".checkchild:checked").val();
	if ($(".checkchild:checked").length < 1) {
		alert("请选择一条数据");
		return;
	}
	if ($(".checkchild:checked").length > 1) {
		alert("一次只能修改一条数据");
		return;
	}

	var changeUser = $('#tpl-change').html();
        event.preventDefault();
    	dialog({
            title: '用户状态？',
            content: changeUser,
            okValue: '确定',
            ok: function () {
				var param = {id:uid};
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/users/change',
				data: JSON.stringify(param),
				dataType: "json",
				success: function (result) {
					if(result.message == "fail"){
						alert("用户信息已存在！");
					}else if (result.message == "success"){
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
			
            }
    	}).show();

 
 });
 
//页面添加按钮
$('#user-add').click(function(){
	var htmladd = $('#tpl-user-add').html();
	dialog({
		title: '添加用户',
		content: htmladd,
		okValue: '确定',  
		ok: function () {
		var uid = $("#uId").val();	
		var uName = $("#pId").val();
		var uEnable = $('input[name="enAbleName"]:checked').val(); 
		if(uid == "" || uid == undefined || uid == null){
           alert("用户名不能为空");
		   return false;
        }
		if(uName == "" || uName == undefined || uName == null){
           alert("密码不能为空");
		   return false;
        }
		var param = {username:uid,password:uName,enable:uEnable};
	$.ajax({
		async: false,
		type: "POST",
		contentType: "application/json; charset=utf-8",
		url: '/serviceBP/users/add',
		data: JSON.stringify(param),
		dataType: "json",
		success: function (result) {
			if(result.message == "success"){
			alert("添加成功");
			dialog('close');
			window.location.reload();
		}else if(result.message == "fail"){
			alert("用户已存在，请重新输入！");
			return false;
			
		}else{
			alert("输入错误，请重新输入！");
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
                alert(username);
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
		alert("请选择一条数据");
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
                    async: false,
                    success: function(data) {
                        if (data.message == "success") {
                            alert("保存成功");
							window.location.reload();
                           
                        } else if(data.message == "fail"){
                            alert("请至少给用户选择一个角色");
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
 
	
	
 
 
 

