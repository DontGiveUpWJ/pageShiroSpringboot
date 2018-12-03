//抽取拼串的方法
function commonfunction(data){
	
	var pading =  data.pages;//总页码href="javascript:void(0);"
    var nowpage = data.pageNum;//当前页
	var dataList = data.list;
    //$('#projectlist').find("li").remove();//开始是分页的核心了
	var html = '';
	html += '<li id="firstPage" class="p-prev disabled"><a href="javascript:void(0);">首 页</a></li>';
	html += '<li id="prePage" class="p-prev disabled" ><a href="javascript:void(0);"><i></i>上一页</a></li>';
	html += '<li ><a id="one" href="javascript:void(0);" >1</a></li>';
	html += '<li ><a id="two" href="javascript:void(0);" >1</a></li>';
	html += '<li ><a id="three" href="javascript:void(0);" >1</a></li>';
	html += '<li ><a id="fore" href="javascript:void(0);" >1</a></li>';
	html += '<li ><a id="five" href="javascript:void(0);" >1</a></li>';
	html += '<li id="nextPage"class="p-next"><a href="javascript:void(0);">下一页<i></i></a></li>';
    html += '<li id="lastPage" class="p-next"><a href="javascript:void(0);" >尾 页</a></li>';
	//html += '<li class="total"><span id="span_number">共13855页 到第<input type="text" id="input_number" class="page-txtbox" />页<input id="sub" value="确定" type="button"></span></li>'
	$('#projectlist').html(html);
	$('#lastPage').attr('onclick','nav('+data.pages+');');
	$('#firstPage').attr('onclick','nav('+1+');');
	$('#prePage').attr('onclick','nav('+(nowpage - 1)+');');
	$('#nextPage').attr('onclick','nav('+(nowpage + 1)+');');
	$('#sub').attr('onclick','test()');
	
	if(dataList.length>0){
            //$('#totalpage').val(pading);
            //var page = data.pageNum;//当前页
             //$('#countpage').html("<b id='currentPageNo'>"+dataList.pages+"</b>/"+pading+"");
              
             //$('#span_number').html("共"+pading+"页 到第<input type='text' id='input_number' class='page-txtbox' />页<input name='' value='确定' type='button' onclick='indexpage(-2)'/goods/ajaxqueryGoodsList.do.html','','goodsListContainer','"+pading+"', listPageCallback);' class='page-btn'/>")
      }else{
          //$('#countpage').html("<b id='currentPageNo'>"+0+"</b>/"+0+"");
      }
        if(nowpage< 5 ){
          $('#one').text(1);
          $('#one').attr('onclick','nav('+1+');');
          $('#two').text(2);
          $('#two').attr('onclick','nav('+2+');');
          $('#three').text(3);
          $('#three').attr('onclick','nav('+3+');');
          $('#fore').text(4);
          $('#fore').attr('onclick','nav('+4+');');
          $('#five').text(5);
          $('#five').attr('onclick','nav('+5+');');
           
           
      }else{
		   var one = nowpage-2;
		   var two = nowpage-1;
		   var fore = nowpage + 1;
		   var five = nowpage + 2;
          //alert("已经不是第五页了");
          //设置中间的为当前页
          $('#one').text(one);
		  $('#one').attr('onclick','nav('+one+');');
          $('#two').text(two);
		  $('#two').attr('onclick','nav('+two+');');
          $('#three').text(nowpage);
		  $('#three').attr('onclick','nav('+nowpage+');');
		    $('#fore').text(fore);
		  $('#fore').attr('onclick','nav('+fore+');');
		    $('#five').text(five);
		  $('#five').attr('onclick','nav('+five+');');
          //判断下一页是否超过了总页数
          if(nowpage + 1 >data.pages){
              $('#five').parent().hide();
              $('#fore').parent().hide();
          }else{
              $('#fore').parent().show();
              $('#fore').text(Number(nowpage)+1);
			  var nowp = nowpage +1;
			  $('#five').attr('onclick','nav('+nowp+');');
              
          }
          //判断下一页的第二页是否超过了总页数
          if(nowpage+2>data.pages){
              $('#five').parent().hide();
          }else{
              $('#five').parent().show();
              $('#five').text(Number(nowpage)+2);
			  var nowp2 = nowpage + 2;
			  $('#five').attr('onclick','nav('+nowp2+');	');
          }
           
           
            
      }
      
      //设置分页的底部 就是 首页  1   2   3   4   5   6   尾页
      
	  //如果总页数小于5，这块代码主要就是设置  1  2  3  4  5  这些的显示和隐藏的
      
	  if(pading==0){
          $('#one').parent().hide();
          $('#two').parent().hide();
          $('#three').parent().hide();
          $('#five').parent().hide();
          $('#fore').parent().hide();
      }else if(pading==1){
          $('#firstPage').hide();
          $('#lastPage').hide();
          $('#one').parent().show();
          $('#two').parent().hide();
          $('#three').parent().hide();
          $('#five').parent().hide();
          $('#fore').parent().hide();
      }else if(pading==2){
          $('#one').parent().show();
          $('#two').parent().show();
          $('#three').parent().hide();
          $('#five').parent().hide();
          $('#fore').parent().hide();
      }else if(pading==3){
          $('#one').parent().show();
          $('#two').parent().show();
          $('#three').parent().show();
          $('#five').parent().hide();
          $('#fore').parent().hide();
      }else if(pading==4){
          $('#one').parent().show();
          $('#two').parent().show();
          $('#three').parent().show();
          $('#five').parent().show();
          $('#fore').parent().hide();
      }else if(pading==5){
          $('#one').parent().show();
          $('#two').parent().show();
          $('#three').parent().show();
          $('#five').parent().show();
          $('#fore').parent().show();
      }
      //one  two  three five fore<br>　
	  　　　　　//下面代码看着是比较麻烦，但是也不难理解  全是一样的代码，只不过是加了些判断
    
      //设置高亮显示的，就是是第一页时，1亮，第二页时  2亮
      $('#projectlist a').each(function() {
          $(this).parent().removeClass("active");
            if($(this).text()==nowpage){
                $(this).parent().addClass("active");
            }
        });
      //分页完返回页面顶端
      $("html,body").animate({scrollTop:0}, 500);

}
function nav(pageNum){
	  var uid = $("#userId").val();
	  var uname = $("#uName").val();
	  var pagesize = pageNum ;
	  var param = {id:uid,username:uname,PAGE_NUM:pagesize};
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
            }
        })
   
}

