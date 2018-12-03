/**
 * Created by 72707 on 2018/9/12.
 */
$(document).ready(function () {
	var count = 0;
	$("#demo-list").empty()
    $.ajax({
        cache: true,
        type: "POST",
        url:'/serviceBP/resources/loadMenu',
        dataType:"json",
        success: function(data) {
        	 $.ajax({
                 cache: true,
                 type: "POST",
                 url:'/serviceBP/resources/loadMenu1',
                 dataType:"json",
                 success: function(data1) {
                	 $.ajax({
                         cache: true,
                         type: "POST",
                         url:'/serviceBP/resources/loadMenu2',
                         dataType:"json",
                         success: function(data2) {
        for(var i=0;i<data.length;i++){
			count =  1;
           //获取一级菜单的id为parId
           var parId = data[i].id;
           //在id为demo-list的ul上拼接一级菜单
           $("#demo-list").append("<li id = "+ parId+"><a class = 'nav-item platform' f = 'a'><i class='font-icon'></i><span>"+data[i].name+"</span></a> </li>")
          //遍历二级菜单
          for(var j=0;j<data1.length;j++){
        	  	//获取二级菜单的id
            	 var chIdd = data1[j].id; 
        	  	//获取二级菜单的父id
            	 var chId = data1[j].parentid
              //判断，如果二级菜单的父id和以及菜单的id相等，在一级菜单里面拼接二级菜单
              if(chId == parId){
            	  //判断是否是二级菜单，如果是二级菜单，数据库默认url是空。
            	  if("" == data1[j].resurl){
            		  $("#" + parId + "").append("<ul f = 'ul' ><li id = "+ chIdd+" class = 'submenu'><a href = 'javascript:void(0)' class = 'nav-item' f = 'a'>"+data1[j].name+"<i class= 'font-icon'></i> </a></li></ul>")
            		  //遍历三级菜单
            		  for(var k=0;k<data2.length;k++){
            			     //获取三级菜单的父id
                    		 var sonId = data2[k].parentid
                    		 //如果三级菜单的父id和二级菜单的id相等，在二级菜单里面拼接三级菜单
                    		 if(chIdd == sonId){
                    			 $("#" + chIdd + "").append("<ul><li><a href = '用户管理.html' class = 'nav-item' f = 'a'>"+data2[k].name+"</a><li></ul> ")
                    		 }
                    	 }
            	  }else{
            		  
            	  
            		$("#" + parId + "").append("<ul f = 'ul'><li id = "+ chIdd+"><a href = '"+data1[j].resurl+"' class = 'nav-item' f = 'a'>"+data1[j].name+"</a> </li></ul>")
            	
            		/* for(var k=0;k<data2.length;k++){
               		 
               		 var sonId = data2[k].parentid
               		 if(chIdd == sonId){
               			 $("#" + chIdd + "").append("<ul class='submenu'><li><a href = 'javascript:void(0)' onclick=partRefresh(\""+data2[k].resurl+"\") ><i class='icon icon-home1'></i>"+data2[k].name+"</a><li></ul> ")
               		 }
               	 } */
            	  
              } 
              }	 
            	 
              }
            
            }	
                      	  var a = count;
				if(count == 0){
					alert("登录超时，请重新登录！");
					window.location.href = "login.html";
				}   
                         }
        	 });
    		}
    	});
    }
    });


});
 
 
 

