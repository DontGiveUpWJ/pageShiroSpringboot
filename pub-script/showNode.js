 //页面加载用户信息
$(document).ready(function () {
	var addParam = {};
	$.ajax({
			cache: true,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: 'serviceBP/staticNode/flowupdate',
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
	console.log(list);
    var html = '';
    $("#show_count").html('共有数据：'+data.total+'条');

    for ( var a in list){
        var nid = list[a].nid;
        var noName = list[a].noName||'';
        var noDesc = list[a].noDesc||'';
		html += '<tr f="tr" name="tr" id="tr">';
       
        html += '<td f="td" class="text-center"><input type="checkbox" f="checkbox" class="checkchild" value = "'+nid+'"></td>';
        html += '<td f="td">'+nid+ 	'</td>';
        html += '<td f="td">'+noName+ 	'</td>';
        html += '<td f="td">'+noDesc+	'</td>';
		html += '<td f="td" class="text-primary"><a href="javascript:;" onclick="node_show('+ nid +')" f="a" class="mgr-sm text-primary">查看</a>|<a href="javascript:;" onclick="find_node('+ nid +')" f="a" class="mgl-sm text-primary">修改</a></td>'
     
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
			var nid = $("#nId").val();
			var nName = $("#nName").val();
			var nDesc = $("#nDesc").val();
			var param = {nid:nid,noName:nName,noDesc:nDesc,PAGE_NUM:num};
			if (type == 'change'){
				$.ajax({
					async: false,
					type: "POST",
					contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/staticNode/flowupdate',
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
function node_show(nid){
	window.location.href = "/staticNode.html?nid="+nid;
	}

//页面修改按钮
function find_node(nid){
	
	var findn = {nid : nid};
	var editTpl = $('#find-node').html();
        event.preventDefault();
    	dialog({
            title: '修改',
            content: editTpl,
            okValue: '确定',
            ok: function () {
                var noName = $("#noId").val();
				var noDesc = $("#noDesc").val();
				var param = {nid:nid,noName:noName,noDesc:noDesc};
			$.ajax({
				async: false,
				type: "POST",url: '/serviceBP/staticNode/StaticNodeUpdate',
				data: param,
				dataType: "json",
				success: function (result) {
					if (result.message == "success"){
						alert("修改成功");
						dialog('close');
						window.location.reload();
					}else{
						alert("用户信息已存在！");
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
					url: '/serviceBP/staticNode/findNodeById',
					data: findn,
                    // 你的formid
                    async: false,
                    success: function(result) {
						$("#noId").val(result.data.noName);
						$("#noDesc").val(result.data.noDesc);
                    }
          });
	
            }
    	}).show();
    

}

//页面查询按钮
$("#subIdNode").on('click',function(){
	var nid = $("#nId").val();
	  var nName = $("#nName").val();
	  var nDesc = $("#nDesc").val();
	  var param = {nid:nid,noName:nName,noDesc:nDesc};
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json; charset=utf-8",
			url: '/serviceBP/staticNode/flowupdate',
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
$("#node-del").on('click',function(){
	var id = $(".checkchild:checked").val();
	if ($(".checkchild:checked").length < 1) {
		alert("请选择一条数据");
		return;
	}
	if ($(".checkchild:checked").length > 1) {
		alert("一次只能修改一条数据");
		return;
	}
	alert(id);
	if (confirm("确定删除吗")) {
		$.ajax({
			cache: true,
			type: "POST",
			url: 'serviceBP/staticNode/delete',
			data: {nid:id},
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
	
//页面添加按钮
$('#node-add').click(function(){
	var nodeadd = $('#tpl-node-add').html();
	dialog({
		title: '添加node',
		content: nodeadd,	
		okValue: '确定',  
		ok: function () {
		var noId = $("#noId").val();	
		var noDesc = $("#noDesc").val();
		if(noId == "" || noId == undefined || noId == null){
           alert("流程名不能为空");
		   return false;
        }
		if(noDesc == "" || noDesc == undefined || noDesc == null){
           alert("流程描述不能为空");
		   return false;
        }
		var param = {noName:noId,noDesc:noDesc};
	$.ajax({
		async: false,
		type: "POST",
		url: '/serviceBP/staticNode/add',
		data: param,
		dataType: "json",
		success: function (result) {
			if(result.message == "success"){
			alert("添加成功");
			dialog('close');
			window.location.reload();
		}else if(result.message == "fail"){
			alert("用户已存在，请重新输入！");
			
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
    
