/**
 * Created by 72707 on 2018/9/12.
 */
$(document).ready(function () {
	var addParam = {};
	$.ajax({
			cache: true,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: '/serviceBP/roles',
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
        var rid = list[a].id;
        var roledesc = list[a].roledesc||'';
		html += '<tr f="tr" name="tr" id="tr">';
       
        html += '<td f="td" class="text-center"><input type="checkbox" f="checkbox" class="checkchild" value = "'+rid+'"></td>';
        html += '<td f="td">'+rid+ 	'</td>';
        html += '<td f="td">'+roledesc+ 	'</td>';
		html += '<td f="td" class="text-primary"><a href="javascript:;" onclick="role_show('+ rid +')" f="a" class="mgr-sm text-primary">查看</a>|<a href="javascript:;" onclick="find_role('+ rid +')" f="a" class="mgl-sm text-primary">修改</a></td>'
     
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
			var rid = $("#rId").val();
			var roledesc = $("#rName").val();
			var param = {id:rid,roledesc:roledesc,PAGE_NUM:num};
			if (type == 'change'){
				$.ajax({
					async: false,
					type: "POST",
					contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/roles',
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
function role_show(rid){
	var findr = {id : rid};
	  //修改用户的js
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
                    url: '/serviceBP/roles/findRoleById',
                    data: JSON.stringify(findr),
                    async: false,
                    success: function(result) {
                       var editForm = $('#edit-form');
						editForm.find('.js_roleName').val(result.data.roledesc);
            	
                    }
          });
	
            }
    	}).show();
    
}

//页面修改按钮
function find_role(rid){
	
	var findr = {id : rid};
	  //修改用户的js
	var editTpl = $('#tpl-update').html();
        event.preventDefault();
    	dialog({
            title: '修改',
            content: editTpl,
            okValue: '确定',
            ok: function () {
                var editForm = $('#role-form');
            	var roledesc = editForm.find('.js_roleName').val();
				var param = {id:rid,roledesc:roledesc};
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/roles/updateRole',
				data: JSON.stringify(param),
				dataType: "json",
				success: function (result) {
					if(result.message == "error"){
						alert("修改错误！");
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
			$.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/roles/findRoleById',
                    data: JSON.stringify(findr),
                    // 你的formid
                    async: false,
                    success: function(result) {
						var editForm = $('#role-form');
						editForm.find('.js_roleName').val(result.data.roledesc); 
                    }
          });
	
            }
    	}).show();
    

}

//页面查询
$("#subRole").on('click',function(){
	  var rid = $("#rId").val();
	  var roledesc = $("#rName").val();
	  var param = {id:rid,roledesc:roledesc};
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json; charset=utf-8",
			url: '/serviceBP/roles',
			data: JSON.stringify(param),
            dataType: "json",
            success: function (result) {
                renderData1(result.data);
            },
            error: function () {
            }
        })
    });
	
//删除
$("#roleDel").on('click',function(){
	var id = $(".checkchild:checked").val();
	if ($(".checkchild:checked").length < 1) {
		alert("请选择一条数据2");
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
			url: '/serviceBP/roles/delete',
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
 
 //角色添加按钮
 $("#role-add").on('click',function(){ 

	var changeUser = $('#tpl-add').html();
		event.preventDefault();
		dialog({
			title: '添加角色信息',
			content: changeUser,
			okValue: '确定',
			ok: function () {
			var rid = $("#roleId").val();
			if(rid == "" || rid == undefined || rid == null){
           alert("角色名不能为空");
		   return false;
        }
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/roles/add',
				data: JSON.stringify({roledesc:rid}),
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
 
 
 $("#role-Resource").on('click',function(){ 

 var roler = $('#tpl-roleResource').html();
  event.preventDefault();
        var rid = $(".checkchild:checked").val();
		if ($(".checkchild:checked").length < 1) {
			alert("请选择一条数据");
			return;
		}
		if ($(".checkchild:checked").length > 1) {
			alert("一次只能修改一条数据");
			return;
		}
	    roleId = rid;
		var setting = {
			check: {
				enable: true,
				chkboxType: {
					"Y": "p",
					"N": "s"
				}
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentid",
				}
			}
		};
		
	
        
    	dialog({
            title: '分配资源',
            content: roler,
            okValue: '确定',
            ok: function () {	
			var rid = $("#rId").val();	
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: '/serviceBP/roles/add',
				data: JSON.stringify({roledesc:rid}),
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
			  $.ajax({
                    async: false,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({
                        roleid: roleId
                    }),
                    url: "/serviceBP/resources/resourcesWithSelected",
                    dataType: 'json',
                    success: function(data) {

                        $.fn.zTree.init($("#treeDemo"), setting, data.data);
                        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        zTree.expandAll(true);
                    }
                });
			
			
			
			
            }
    	}).show();
	
	
	
	
 
 });
 
 function findResources() {

	var rid = $(".checkchild:checked").val();
	if ($(".checkchild:checked").length < 1) {
		alert("请选择一条数据");
		return;
	}
	if ($(".checkchild:checked").length > 1) {
		alert("一次只能修改一条数据");
		return;
	}
	roleId = rid;
	var setting = {
		check: {
			enable: true,
			chkboxType: {
				"Y": "p",
				"N": "s"
			}
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentid",
			}
		}
	};

	$.ajax({
		async: false,
		type: "POST",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			roleid: roleId
		}),
		url: "/serviceBP/resources/resourcesWithSelected",
		dataType: 'json',
		success: function(data) {

			$.fn.zTree.init($("#treeDemo"), setting, data.data);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.expandAll(true);
			$('#selectResources').modal();
		}
	});

}
 
 function saveRoleResources() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	checkNode = zTree.getCheckedNodes(true);
	var ids = new Array();
	for (var i = 0; i < checkNode.length; i++) {
		ids.push(checkNode[i].id);
	}
	$.ajax({
		async: false,
		type: "POST",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			roleid: roleId,
			resourcesid: ids.join(",")
		}),
		url: "/serviceBP/roles/saveRoleResources",
		success: function(data) {
			if (data.message == "success") {
				alert("分配成功");
				$('#selectResources').modal('hide');
				$("#button").trigger("click");

			} else {
				alert("分配失败");
				$('#selectResources').modal('hide');
				$("#button").trigger("click");
			}
		}
	});
}
 

