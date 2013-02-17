// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.storeInit = function() {
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
	  
	  //列表
	  $(viewList).datagrid({
		  singleSelect:true,
		  fit:true,
		  columns:[[
			    {field:'productCode',title:'商品编号',width:120,align:"center"},
				{field:'productName',title:'商品名称',width:300,align:"center"},
				{field:'productTypeName',title:'商品类型',width:120,align:"center"},
				{field:'unitName',title:'单位',width:90,align:"center"},
				{field:'sizeName',title:'规格',width:90,align:"center"},
				{field:'colorName',title:'颜色',width:90,align:"center"},
			    {field:'price',title:'库存单价',width:90,align:"center",formatter: function(value,row,index){
			    	var price = 0; 
			    	if(row.qtyStore!=0){
			    		price = (row.amountStore/row.qtyStore).toFixed(2);
			    	}
			    	row.price = price;
			    	return price;
				}},
			    {field:'qtyStore',title:'库存数量',width:90,align:"center"},
			    {field:'amountStore',title:'库存总价',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
				//加载库存明细
				var url = "store/queryByProductStore.do";
				var content = {'product.productId':rowData.productId};
				var result = syncCallService(url,content);
				if(result.isSuccess){
					var data = result.data;
					$(storeList).datagrid('loadData',eval("("+data.datagridData+")"));
				}else{
					$.messager.alert('提示',result.message,"error");
				}
		  }
	 });
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(){
		var productCode = $('#productCodeSearch',queryContent).val();
		var productName = $('#productNameSearch',queryContent).val();
		var options = $(viewList).datagrid('options');
		var url = "dict/queryStoreProduct.do";
		var content = {'productCode':productCode,'productName':productName,productName:productName,page:options.pageNumber,rows:options.pageSize};
		
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(viewList).datagrid('loadData',eval("("+data.datagridData+")"));
			//清空库存明细
			$(storeList).datagrid('loadData',LYS.ClearData);
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	} 
	//分页条
	$(viewList).datagrid('getPager').pagination({   
	    onSelectPage: function(page, rows){
			search();
	    }
	});
	//----------库存明细-------------
	var storeList = $('#storeList',$this);
	//列表
	$(storeList).datagrid({
		  singleSelect:true,
		  fit:true,
		  columns:[[
			    {field:'productCode',title:'商品编号',width:120,align:"center"},
				{field:'productName',title:'商品名称',width:300,align:"center"},
				 {field:'warehouseName',title:'仓库',width:120,align:"center"},
			    {field:'price',title:'库存单价',width:90,align:"center",formatter: function(value,row,index){
			    	return selectRow.price;
				}},
			    {field:'qty',title:'库存数量',width:90,align:"center"},
			    {field:'amount',title:'库存总价',width:90,align:"center",formatter: function(value,row,index){
					return (selectRow.price*row.qty).toFixed(2);
				}}
		  ]],
		  rownumbers:true,
		  pagination:true
	 });
  }
})(jQuery);   