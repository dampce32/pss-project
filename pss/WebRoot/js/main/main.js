$(function(){
	//当前选中的节点
    var currentNode = null;
	tabCloseEven();
 	$('#navi a').click(function(){
		var href =$(this).attr('name');
		var title = $(this).text();
		if(href==null){
			return false;
		}
		
		addTab(title,href);
		return false;
	});
 	$('#rightTree').tree({
		url: 'system/getRootRightMainUser.do',
		onBeforeExpand:function(node,param){
			$('#rightTree').tree('options').url = 'system/queryChildrenRightUser.do?rightId='+node.id;  
		},
		onSelect:function (node) {
			$(this).tree('toggle',node.target);
            if (currentNode != null && node.id == currentNode.id) return;
            if (node.attributes == null) return;
            var url = node.attributes.rightUrl;
            var title = node.text;
            addTab(title, url);
        }
	});
 	//退出系统
 	 $('#exitSystem').click(function(){
     	$.messager.confirm('提示','确定要退出系统吗?',function(r){
     		if(r){
     			$.post('system/logoutUser.do',function(result){
     				if(result.isSuccess){
     					window.location.href='login.html';
     				}
     			},'json');
     		}
     	})
     })
})

function addTab(title,href){
	if($('#tabs').tabs('exists',title)){//选择并更新tab
		$('#tabs').tabs('select',title);
	}else{
		var index = href.indexOf('.');
		var postfix = href.substring(index+1);
			var panelInfo = {
                title:title,
                closable:true,
                href:href, border:false, plain:true,
                extractor:function (d) {
                    if (!d) {
                        return d;
                    }
                    if (window['LYS']) {
                        var id = LYS.genId();
                        return d.replace(/\$\{id\}/, id);
                    }
                    return d;
                },
                onLoad:function (panel) {
                    var tab = $('.plugins', this);
                    if ($(tab).size() == 0) {
                        return;
                    }
                   LYS.initContent(this);
                }
            };
            $('#tabs').tabs('add', panelInfo);
	}
	tabClose();
}

function createFrame(url) {
	$('#tabs').tabs('add',{
		title:title,  
	    content:createFrame(href),  
	    closable:true 
	})
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}	


//绑定右键菜单事件
function tabCloseEven() {
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			})
		}
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != '首页') {
				$('#tabs').tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		return false;
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==1){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}