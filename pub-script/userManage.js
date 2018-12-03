$(function(){
	var html = $('#tpl-user-add').html();
   
    $('#user-add').click(function(){
    	dialog({
            title: '添加用户',
            content: html,
            okValue: '确定',  
            ok: function () {
               
            },	//确定回调
            cancelValue: '取消', 
            cancel: function () {}	//取消回调
    	}).show();
    });
});


