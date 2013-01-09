// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.productStoreInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	  var searchBtn = $('#searchBtn',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var pager = $('#pager',$this);
	  var pageNumber = 1;
	  var pageSize = 10;
	  
	  
	  //列表
	  $(viewList).datagrid({
		  singleSelect:true,
		  fit:true,
		  columns:[[
			    {field:'productCode',title:'商品编号',width:120,align:"center"},
				{field:'productName',title:'商品名称',width:300,align:"center"},
			    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
			    {field:'unitName',title:'单位',width:120,align:"center"},
			    {field:'sizeName',title:'规格',width:120,align:"center"},
			    {field:'colorName',title:'颜色',width:120,align:"center"},
			    {field:'priceStore',title:'库存单价',width:120,align:"center"},
			    {field:'qtyStore',title:'库存数量',width:120,align:"center"},
			    {field:'amountStore',title:'库存总价',width:120,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
		  }
	 });
	//分页条
	$(pager).pagination({   
	    onSelectPage: function(page, rows){
			pageNumber = page;
			pageSize = rows;
			search();
	    }
	});
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(flag){
		var productCode = $('#productCodeSearch',queryContent).val();
		var productName = $('#productNameSearch',queryContent).val();
		
		var url = "dict/queryProduct.do";
		var content = {productCode:productCode,productName:productName,page:pageNumber,rows:pageSize};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(viewList).datagrid('loadData',eval("("+data.datagridData+")"));
			//需要重新重新分页信息
			if(flag){
				getTotal(content);
			}
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	} 
	//统计总数
	var getTotal = function(content){
		var url = "dict/getTotalCountProduct.do";
		asyncCallService(url,content,
		function(result){
			if(result.isSuccess){
				var data = result.data;
				$(pager).pagination({  
					pageNumber:1,
					total:data.total
				});
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		})
	}	
  }
})(jQuery);   