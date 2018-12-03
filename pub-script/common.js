(function($){
    /** ui-tab切换，应用在tab标签列表容器上 */
    $.fn.ui_tab = function(options) {
        var defaults = {
            click: function() {},	//tab点击切换回调
            itemWidth: 0,
            itemMinWidth: 0,
            flex: false,
            tabTag: 'li',	//单个tab标签选择器，默认'li'
            tabCont: null,	//tab标签对应内容区父容器
            tabItem: '.tab-item',	//tab标签对应内容单元容器选择器，默认为'.tab-item'
            btnCloseTab: '',	//标签上关闭tab标签按钮选择器
            btnAddTab: null,	//添加tab新标签按钮
            btnLeftScroll: null,	//tab标签左滑按钮
            btnRightScroll: null 	//tab标签右滑按钮
        }
        var opt = $.extend({}, defaults, options);

        var _tabHead = $(this);
        if (opt.flex) {
            _tabHead.addClass('tab-flex');
        } else {
            opt.itemWidth && _tabHead.children(opt.tabTag).css('width', opt.itemWidth);
            opt.itemMinWidth && _tabHead.children(opt.tabTag).css('min-width', opt.itemMinWidth);
        }

        //标签切换
        _tabHead.on('click', opt.tabTag, function(event) {
            event.preventDefault();
            var _this = $(this);
            $(this).addClass('active').siblings(opt.tabTag).removeClass('active');
            opt.tabCont.children(opt.tabItem).removeClass('active').eq(_this.index()).addClass('active');
            typeof opt.click == 'function' && opt.click(_this.index(), _this);
        });
        
        //添加标签
        if (opt.btnAddTab) {
            opt.btnAddTab.click(function(event){
            	event.preventDefault();
                var tag = _tabHead.children().removeClass('active').first().clone().addClass('active');
                var cont = opt.tabCont.children(opt.tabItem).removeClass('active').first().clone().addClass('active').empty();
                tag.find('span').text('未命名');
                _tabHead.append(tag);
                opt.tabCont.append(cont);
            });
        }
        
        //关闭标签
        if (opt.btnCloseTab) {
            _tabHead.on('click', opt.tabTag+' '+opt.btnCloseTab, function(event){
            	event.preventDefault();
            	event.stopPropagation();
                if(_tabHead.children().length<2) return;	//只有一个标签时不可关闭
                var tag = $(this).closest(opt.tabTag);
                var cont = opt.tabCont.children(opt.tabItem).eq(tag.index());
                if(tag.hasClass('active')){
                	tag.prev().addClass('active');
                	cont.prev().addClass('active');
                }
                tag.remove();
                cont.remove();
            });
        }
        
        //标签左右滚动
        function getTabData() {
            return {
                wrapWidth: _tabHead.parent().outerWidth(),
                listWidth: _tabHead.outerWidth(),
                listLeft: -parseInt(_tabHead.css('margin-left')),
                step: 200
            }
        }
        if (opt.btnLeftScroll) {
            //左滚
            opt.btnLeftScroll.click(function(event){
            	event.preventDefault();
            	event.stopPropagation();
                var tabData = getTabData();
                if (tabData.listLeft <= 0) return;
                var moveLeft = tabData.listLeft - tabData.step;
                moveLeft = moveLeft < 0 ? 0 : moveLeft
                _tabHead.stop().animate({ 'margin-left': -moveLeft }, 300);
            });
            //右滚
            opt.btnRightScroll.click(function(event){
            	event.preventDefault();
            	event.stopPropagation();
                var tabData = getTabData();
                var maxLeft = tabData.listWidth - tabData.wrapWidth;
                var moveLeft = tabData.listLeft + tabData.step;
                moveLeft = moveLeft > maxLeft ? maxLeft : moveLeft;
                moveLeft = moveLeft < 0 ? 0 : moveLeft
                _tabHead.stop().animate({ 'margin-left': -moveLeft }, 300);
            });
        }
    };
})(jQuery);