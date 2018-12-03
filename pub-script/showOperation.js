/**
 * Created by 72707 on 2018/9/12.
 */
 //页面加载用户信息
$(document).ready(function () {
	var addParam = {};
	$.ajax({
			cache: true,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: 'serviceBP/users/operation',
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
	console.log(list);
    var html = '';
    $("#show_count").html('共有数据：'+data.total+'条');

    for ( var a in list){
        var oid = list[a].oid;
        var opeName = list[a].opeName||'';
        var username = list[a].username||'';
		var dateTime = list[a].dateTime||'';
		html += '<tr f="tr" name="tr" id="tr">';
       
        html += '<td f="td" class="text-center"><input type="checkbox" f="checkbox" class="checkchild" value = "'+oid+'"></td>';
        html += '<td f="td">'+oid+ 	'</td>';
        html += '<td f="td">'+opeName+ 	'</td>';
        html += '<td f="td">'+username+	'</td>';
        html += '<td f="td">'+dateTime+	'</td>';
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
			var oid = $("#oId").val();
			var opeName = $("#oName").val();
			var username = $("#oUser").val();
			var param = {oid:oid,opeName:opeName,username:username,PAGE_NUM:num};
			if (type == 'change'){
				$.ajax({
					async: false,
					type: "POST",
					contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/users/operation',
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

//页面查询按钮
$("#subIdOpera").on('click',function(){
	  var oid = $("#oId").val();
	  var opeName = $("#oName").val();
	  var username = $("#oUser").val();
	  var param = {oid:oid,opeName:opeName,username:username};
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json; charset=utf-8",
			url: '/serviceBP/users/operation',
			data: JSON.stringify(param),
            dataType: "json",
            success: function (result) {
                renderData1(result.data);
            },
            error: function () {
            }
        })
    });
	


   
    
  
    
   
	
	
	
 
 
 

