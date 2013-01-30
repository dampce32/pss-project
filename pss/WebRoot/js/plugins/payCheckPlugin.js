// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.payCheckInit = function() {
	  $(this).mask({maskMsg:'正在加载界面'});
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	 $(this).mask('hide');
  }
})(jQuery);   