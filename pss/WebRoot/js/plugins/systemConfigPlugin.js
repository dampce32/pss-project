// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.systemConfigInit = function() {
	  $(this).mask({maskMsg:'正在加载界面'});
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var editForm = $('#editForm',$this);
	  
	  var url = 'system/initSystemConfig.do';
	  asyncCallService(url,'json',function(result){
		  if(result.isSuccess){
			  var data = result.data;
			  var systemConfigData = eval("("+data.systemConfigData+")");
			  $('#systemConfigId',$this).val(systemConfigData.systemConfigId);
			  $('#companyName',$this).val(systemConfigData.companyName);
			  $('#companyPhone',$this).val(systemConfigData.companyPhone);
			  $('#companyFax',$this).val(systemConfigData.companyFax);
			  $('#companyAddr',$this).val(systemConfigData.companyAddr);
			  if(systemConfigData.isAmountEqPayAmount==1){
				  $('#isAmountEqPayAmount',$this).prop('checked',true);
			  }else{
				  $('#isAmountEqPayAmount',$this).prop('checked',false);
			  }
			  if(systemConfigData.isOtherAmountInPayAmount==1){
				  $('#isOtherAmountInPayAmount',$this).prop('checked',true);
			  }else{
				  $('#isOtherAmountInPayAmount',$this).prop('checked',false);
			  }
		  }else{
			  $.messager.alert('提示',result.message,'error');
		  }
		  
	  })
	 var setValue = function(){
		  if($('#isAmountEqPayAmount',$this).prop('checked')){
			  $('#isAmountEqPayAmount',$this).val(1);
		  }else{
			  $('#isAmountEqPayAmount',$this).val(-1);
		  }
		  if($('#isOtherAmountInPayAmount',$this).prop('checked')){
			  $('#isOtherAmountInPayAmount',$this).val(1);
		  }else{
			  $('#isOtherAmountInPayAmount',$this).val(-1);
		  }
		  $(editForm).mask({maskMsg:'正在保存'});
		  return true;
	  }
	 $('#saveBtn',$this).click(function(){
		 $(editForm).form('submit',{
				url: 'system/saveSystemConfig.do',
				onSubmit: function(){
					return setValue();
				},
				success: function(data){
					$(editForm).mask('hide');
					var result = eval('('+data+')');
					if(result.isSuccess){
						$.messager.alert('提示','保存成功','info');
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}
			});
	 })
	  
	 $(this).mask('hide'); 
  }
})(jQuery);   