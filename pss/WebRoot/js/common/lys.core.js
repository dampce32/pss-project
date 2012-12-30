var LYS={};
LYS.id_index = 0;
LYS.genId = function () {
    return 'LYS_UI_' + (LYS.id_index++);
};
LYS.Join = ',';
LYS.initContent = function (el) {
	var athis = $(el);
	 $('[ui]', el).each(function () {
		 var $this = $(this);
		 var uiName = $(this).attr('ui');
		 if ($.fn[uiName]) {//如果js已经加载则直接执行js
				athis[uiName]();
		    }else{
		    	var index = uiName.indexOf('Init');
		     	var jsBase = uiName.substring(0,index);
		     	var js = 'js/plugins/' + jsBase + 'Plugin.js';
		     	$.xLazyLoader({
		             js:js,
		             success:function () {
		            	 $this[uiName]();
		             }
		         })
		     } 
     });
};
LYS.ClearUrl='common/clearDatagridCommon.do';