function page(data){
	var html1 = '';
	var pageN = data.pageNum;
	var noPage = pageN -1;
	var nextPage = pageN + 1;
	var lastNum = data.navigatepageNums.length;
	html1 += ' <li><a href="javascript:;"  onclick="nav(1)">首页</a></li>';
	if(data.isFirstPage){
		html1 += '<li> <a href="javascript:;">上一页</a></li>';
	}else{
		html1 += '<li> <a href="javascript:;"  onclick="nav('+noPage+')">上一页</a></li>';
	}
for (var i = 0; i < data.pages; i++){
		var num = 1 + parseInt(i) ;
		if(pageN == num){
		html1 += '<li class="active"> <a  href="javascript:;" onclick="nav('+ num +')">'+ num +'</a></li>';
		}else{
			html1 += '<li > <a  href="javascript:;" onclick="nav('+ num +')">'+ num +'</a></li>';
		}
	}
	if(data.isLastPage){
		html1 += '<li> <a href="javascript:;" >下一页</a></li>';
	}else{
		html1 += '<li> <a href="javascript:;"  onclick="nav('+ nextPage+')">下一页</a></li>';
	}
	html1 += ' <li><a href="javascript:;"  onclick="nav('+lastNum+')">尾页</a></li>';
	$("#pageId").html(html1);
}
function nav(index){
	  var uid = $("#userId").val();
	  var uname = $("#uName").val();
	  var pagesize = index ;
	  var param = {id:uid,username:uname,PAGE_NUM:pagesize};
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
   
}