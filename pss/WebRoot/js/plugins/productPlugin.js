// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.productInit = function() {
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
	  var pageSize = 20;
	  
	  var result = null;
	  
	  //列表
	  $(viewList).datagrid({
		  fit:true,
		  columns:[[
			    {field:'productCode',title:'商品编号',width:120,align:"center"},
				{field:'productName',title:'商品名称',width:300,align:"center"},
			    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
			    {field:'unitName',title:'单位',width:120,align:"center"},
			    {field:'sizeName',title:'规格',width:120,align:"center"},
			    {field:'colorName',title:'颜色',width:120,align:"center"},
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},
				{text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}}
		  ],
		  onDblClickRow:function(rowIndex, rowData){
				onUpdate();
		  },
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
		
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑系统商品信息',  
	    width:500,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				onSave();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(editDialog).dialog('close');
			}
		}]
	});    
	//添加
	var onAdd = function(){
		isAdd = true;
		$(editForm).form('clear');
		if(result==null){
			var url = 'dict/queryByDictionaryKindsDataDict.do';
			var content ={ids:'size,color,unit'};
			result = syncCallService(url,content);
		}
		//规格
	   $('#size',editDialog).combobox({
			valueField:'dataDictionaryId',
			textField:'dataDictionaryName',
			width:150,
			data:result.size,
			onSelect:function(record){
				$('#sizeId',editDialog).val(record.dataDictionaryId);
			}
	  })
	  //颜色
	   $('#color',editDialog).combobox({
			valueField:'dataDictionaryId',
			textField:'dataDictionaryName',
			width:150,
			data:result.color,
			onSelect:function(record){
				$('#colorId',editDialog).val(record.dataDictionaryId);
			}
	  })
	  //单位
	   $('#unit',editDialog).combobox({
			valueField:'dataDictionaryId',
			textField:'dataDictionaryName',
			width:150,
			data:result.unit,
			onSelect:function(record){
				$('#unitId',editDialog).val(record.dataDictionaryId);
			}
	  })
	  //商品类型
	  $('#productType',editForm).combogrid({
		    panelWidth:480, 
			mode: 'remote',  
			url: 'dict/queryCombogridProductType.do',
			idField: 'productTypeId',  
			textField: 'productTypeName',  
			pagination:true,
			columns: [[  
			    {field:'productTypeName',title:'商品类型',width:80,sortable:true},  
			    {field:'productTypeCode',title:'商品类型编号',width:80,sortable:true}
			]],
			onSelect:function(rowIndex, rowData){
				$('#productTypeId',editDialog).val(rowData.productTypeId);
			}
		});
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var productCode = $.trim($('#productCode',editForm).val());
		if(productCode==''){
			$.messager.alert('提示','请填写商品编号','warning');
			return false;
		}
		var productName = $.trim($('#productName',editForm).val());
		if(productName==''){
			$.messager.alert('提示','请填写商品名称','warning');
			return false;
		}
		var productTypeId = $('#productType',editForm).combogrid('getValue');
		if(productTypeId==''){
			$.messager.alert('提示','请选择商品类别','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveProduct.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var productId = $.trim($('#productId',editForm).val());
						if(productId==''){//新增
							search(true);
						}else{
							var row = $(editForm).serializeObject();
							$(viewList).datagrid('updateRow',{index:selectIndex,row:row});	
						}
						$(editDialog).dialog('close');
						$(editForm).form('clear');
					}
					$.messager.alert('提示','保存成功','info',fn);
					
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
	//修改
	var onUpdate = function(){
		isAdd = false;
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		var rows = $(productTypeList).datagrid('getSelections');
		if(rows.length==0){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			if(rows.length==0){
				 $.messager.alert('提示',"请选中要删除的纪录","warming");
				 return;	
			}
			var idArray = new Array();
			
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].productTypeId);
			}
			var ids = idArray.join(LYS.Join);
			var url = "dict/mulDeleteProductType.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var rows = $(productTypeList).datagrid('getSelections');
						for(var i=0;i<rows.length>0;i++){
							$(productTypeTree).tree('remove',$(productTypeTree).tree('find',rows[i].productTypeId).target);
						}
						$(productTypeList).datagrid('reload');
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
  }
})(jQuery);   