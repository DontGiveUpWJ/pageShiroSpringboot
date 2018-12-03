$(function(){
    //激活或取消收藏请求
	$('#js_collect').click(function(event){
    	event.stopPropagation();
        $(this).toggleClass('active');
    });
});