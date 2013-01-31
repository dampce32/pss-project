// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.reportInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var printDialog = $('#printDialog',$this);
	  
	  var paramArray = new Array();
	  
	  paramArray.push('txtBeginDate');
	  paramArray.push('supplier');
	  paramArray.push('warehouse');
	  
	  var appendStr = "";
		  appendStr += "<tr class = \"hadAdd\" style=\"margin-top:6px\">"+
			"<td style=\"padding-top:6px\">"+
				"<a href=\"#\" class=\"easyui-linkbutton\" id=\"searchBtn\"  style=\"cursor: pointer;\" value=\"供应商对账\">供应商对账</a>"+
			"</td>"+
			"</tr>";
	  if ("" != appendStr) {
			$(appendStr).insertAfter("#reportTable");
		}
	 $('.easyui-linkbutton').linkbutton({  
	    iconCls: 'icon-search'  
	 }); 
	 
	 
	 $('.easyui-linkbutton').bind('click', function(){  
		 var id =  $(this).attr('id');
		 var text =  $(this).attr('value');
		 $(".hadAddPrint").remove();
		 
		 for ( var i = paramArray.length-1; i >=0; i--) {
			 createChoose(paramArray[i]);
		 }
		 
	     $(printDialog).dialog({
			    title:text,
			    width:400,
			    height:200+2*20
		 })
		$(printDialog).dialog('open');
	 });
	 
	//编辑框
	$(printDialog).dialog({  
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'打印预览',
			iconCls:'icon-print',
			handler:function(){
				onPrint();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(printDialog).dialog('close');
			}
		}]
	});    
	//生成选择条件
	var createChoose = function(kind){
		if('supplier'==kind){
			var appendStr = "";
			appendStr += "<tr class = \"hadAddPrint\" style=\"padding-top:6px\">"+
				"<td style=\"padding:10px 16px\">供应商:</td>"+
				"<td>"+
					"<input type=\"text\" id=\"supplier\"  style=\"width: 250px\" size=\"40\" />"+
				"</td>"+
				"</tr>";
		    if ("" != appendStr) {
				$(appendStr).insertAfter("#printTable");
			}
		     
		   //供应商
			$('#supplier',printDialog).combogrid({  
			    panelWidth:450,  
			    idField:'supplierId',  
			    textField:'supplierName',  
			    url:'dict/queryCombogridSupplier.do',  
			    columns:[[  
			        {field:'supplierCode',title:'供应商编号',width:120},
			        {field:'supplierName',title:'供应商名称',width:120}
			    ]]  
			});
		}else if('txtBeginDate'==kind){
			var appendStr = "";
			appendStr += "<tr class = \"hadAddPrint\" style=\"padding-top:6px\">"+
					"<td style=\"padding:10px 16px\">日期:</td>"+
					"<td>"+
						"<input type=\"text\" id=\"txtBeginDate\"  style=\"width: 110px\" class=\"Wdate\" size=\"40\" onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" />"+
						"&nbsp;&nbsp;至&nbsp;&nbsp;<input type=\"text\" id=\"txtEndDate\"  style=\"width: 110px\" class=\"Wdate\" size=\"40\" onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" />"+
					"</td>"+
				"</tr>";
		    if ("" != appendStr) {
				$(appendStr).insertAfter("#printTable");
			}
		}else if('warehouse'==kind){
			var appendStr = "";
			appendStr += "<tr class = \"hadAddPrint\" style=\"padding-top:6px\">"+
					"<td style=\"padding:10px 16px\">存放仓库:</td>"+
					"<td>"+
						"<input type=\"text\" id=\"warehouse\"  style=\"width: 250px\" size=\"40\" />"+
					"</td>"+
				"</tr>";
		    if ("" != appendStr) {
				$(appendStr).insertAfter("#printTable");
			}
		    
		    //仓库
			$('#warehouse',printDialog).combobox({
				valueField:'warehouseId',
				textField:'warehouseName',
				data:PSS.getWarehouseList()
			})
		}
	}
	//打印前检查值
	var onPrintValue = function(){
		for ( var i = 0; i < paramArray.length; i++) {
			var param = paramArray[i];
			if(param=='txtBeginDate'){
				var txtBeginDate = $('#txtBeginDate').val();
				if(''==txtBeginDate){
					$.messager.alert('提示','请选择开始日期','warning');
					return false;
				}
				var txtEndDate = $('#txtEndDate').val();
				if(''==txtEndDate){
					$.messager.alert('提示','请选择结束日期','warning');
					return false;
				}
				var diff = DateDiff(txtEndDate,txtBeginDate);
				if(diff<0){
					$.messager.alert('提示','结束日期必须大于等于开始日期','warning');
					return false;
				}
			}
		}
		return true;
	}
	//打印预览
	var onPrint = function(){
		if(onPrintValue()){
			
			window.open("printReport.jsp?report=buy&data=ReportServlet?buyId=402880bb3c8542f0013c854975af0001");
		}
	}
  }
})(jQuery);   