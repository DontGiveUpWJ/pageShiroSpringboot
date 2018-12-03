$(function() {

    // 左侧导航
    var _sideNav = $('.js_sideNav');
    _sideNav.on('click', '.nav-item', function(event) {
        event.preventDefault();
        var _this = $(this);
        var _nextUl = _this.next('ul')
        if (!_nextUl.length) {
            // _sideNav.find('.nav-item').parent('li').removeClass('active');
            // _this.parent('li').addClass('active');
            pageTab.open(_this.attr('href'), _this.text());
            return;
        }
        _nextUl.slideToggle('fast', function() {
            _this.parent('li').toggleClass('open');
        });
    });

    // tab标签点击
    var _tabList = $('.js_tabList');
    _tabList.on('click', 'li:not(.active)', function(event) {
        event.preventDefault();
        pageTab.goTo($(this).attr('data-url'));
    }).on('click', '.del', function(event) {
        event.preventDefault();
        event.stopPropagation();
        pageTab.close($(this).parent('li').attr('data-url'));
    });

    // 标签左右切换
    _mainTab = $('.js_mainTab');

    function getTabData() {
        return {
            wrapWidth: _tabList.parent().outerWidth(),
            listWidth: _tabList.outerWidth(),
            listLeft: -parseInt(_tabList.css('margin-left')),
            step: 300
        }
    }
    _mainTab.on('click', '.ptab-left', function(event) {
        event.preventDefault();
        var tabData = getTabData();
        if (tabData.listLeft <= 0) return;
        var moveLeft = tabData.listLeft - tabData.step;
        moveLeft = moveLeft < 0 ? 0 : moveLeft
        _tabList.stop().animate({ 'margin-left': -moveLeft }, 300);

    }).on('click', '.ptab-right', function(event) {
        event.preventDefault();
        var tabData = getTabData();
        var maxLeft = tabData.listWidth - tabData.wrapWidth;
        var moveLeft = tabData.listLeft + tabData.step;
        moveLeft = moveLeft > maxLeft ? maxLeft : moveLeft;
        moveLeft = moveLeft < 0 ? 0 : moveLeft;
        _tabList.stop().animate({ 'margin-left': -moveLeft }, 200);
    });

    // 标签统一api入口
    var pageTab = {
        _iframeList: $('.js_iframeList'),
        // 打开一个标签页，refresh为强制刷新
        open: function(url, title, refresh) {
            refresh = refresh || false;
            var openedLi = _tabList.find('li[data-url="' + url + '"]');
            if (!openedLi.length) {
                _tabList.append('<li data-url="' + url + '">' + title + '<i class="del"></i></li>');
                this._iframeList.append('<iframe src="' + url + '" class="pub-iframe"></iframe>');
            }
            this.goTo(url, refresh);
        },
        // 跳转到一个标签页，refresh为强制刷新
        goTo: function(url, refresh) {
            refresh = refresh || false;
            var tarIframe = this._iframeList.find('iframe[src="' + url + '"]');
            var tarLi = _tabList.find('li[data-url="' + url + '"]');
            var tarSide = _sideNav.find('a.nav-item[href="' + url + '"]').parent('li');
            tarLi.addClass('active').siblings('li').removeClass('active');
            tarIframe.show().siblings('iframe').hide();
            refresh && tarIframe.attr('src', url);
            _sideNav.find('.nav-item').parent('li').removeClass('active');
            tarSide.addClass('active');
        },
        // 关闭一个标签页
        close: function(url) {
            var tarLi = _tabList.find('li[data-url="' + url + '"]');
            var tarIframe = this._iframeList.find('iframe[src="' + url + '"]');
            if (!tarLi.length) {
                return;
            }
            if (tarLi.hasClass('active')) {
                var url = tarLi.prev('li').length ? tarLi.prev('li').attr('data-url') : tarLi.next('li').attr('data-url');
                this.goTo(url);
            }
            tarLi.remove();
            tarIframe.attr('src', 'about:blank').remove();
        }
    };
    window.pageTab = pageTab;
    
    //帮助页导航
    var _helpNav = $('#js_helpNav');
    var _helpMain = $('#js_helpMain');
    
    _helpNav.on('click', '.nav-item', function(event) {
        event.preventDefault();
        var _this = $(this);
        var _nextUl = _this.next('ul');
        if (!_nextUl.length) {
        	_helpNav.find('.nav-item').parent('li').removeClass('active');
            _this.parent('li').addClass('active');
            _helpMain.animate({
            	scrollTop: $(_this.attr('href')).position().top
            },300);
        }
        _nextUl.slideToggle('fast', function() {
            _this.parent('li').toggleClass('open');
        });
    });
    
    var scrollId = '';
    _helpMain.scroll(function(event){
        var items = _helpMain.find("ol h3");
        var sTop = _helpMain.scrollTop();	//垂直滚动距离
        var currentLink = _helpNav.find("li.active");
        var currentId = ""; //滚动条现在所在位置的item id
        items.each(function () {
            var that = $(this);
            var wTop = that.offset().top - 50;	//元素相对滚动窗口顶部距离
            if ((wTop>=0 && wTop<=_helpMain.height()/2) || (wTop<0 && wTop+that.parent().height()>_helpMain.height()/2)) {	
                currentId = "#" + that.attr("id");
                return false;	
            }else {
            	currentId = "";
            }
        });
        if (currentId && currentId!==scrollId && currentLink.attr("href") !== currentId) {
            currentLink.removeClass("active");
            _helpNav.find("[href=" + currentId + "]").parent('li').addClass("active").parent().closest('li').addClass('open');
            scrollId = currentId;
        }
    });
    
    
});