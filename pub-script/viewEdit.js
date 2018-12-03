$(function(){
    var flowTab = $('#js_flowTab');
	flowTab.find('.ptab-list').ui_tab({
    	tabCont: $('#js_flowTabCont'),
    	btnAddTab: flowTab.find('.add-tag'),
    	btnCloseTab: 'i',
    	btnLeftScroll: flowTab.find('.ptab-left'),
    	btnRightScroll: flowTab.find('.ptab-right')
    });
    
    var viewMenu = $('#js_viewMenu');
    viewMenu.on('click','.checkable',function(event){
        event.preventDefault();
    	$(this).toggleClass('checked');
        
    }).on('click','.js_display',function(event){
    	//侧边栏显示与隐藏
        event.preventDefault();
        $('#organize-left,#organize-right').toggleClass('folded');
        
    });
});