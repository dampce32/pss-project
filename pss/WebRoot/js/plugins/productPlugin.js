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
	  var pageSize = 10;
	  
	  var result = null;
	  var deleleIdArray = new Array();
	  
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
			    {field:'buyingPrice',title:'预设进价',width:120,align:"center"},
			    {field:'salePrice',title:'零售单价',width:120,align:"center"},
			    {field:'note',title:'备注',width:120,align:"center"}
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
	    width:1000,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    	$(editForm).form('clear');
	    	var rows = $(defaultPackagingList).datagrid('getRows');
	    	if(rows.length!=0){
	    		$(defaultPackagingList).datagrid('loadData',LYS.ClearData);
	    	}
	    },
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
	var initChoose = function(){
		//规格
		   $('#size',editDialog).combobox({
				valueField:'sizeId',
				textField:'sizeName',
				width:250,
				data:PSS.getSizeList()
		  })
		  //颜色
		   $('#color',editDialog).combobox({
				valueField:'colorId',
				textField:'colorName',
				width:250,
				data:PSS.getColorList()
		  })
		  //单位
		   $('#unit',editDialog).combobox({
				valueField:'unitId',
				textField:'unitName',
				width:250,
				data:PSS.getUnitList()
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
				    {field:'productTypeName',title:'商品类型',width:150,sortable:true},  
				    {field:'productTypeCode',title:'商品类型编号',width:150,sortable:true}
				]],
				onSelect:function(rowIndex, rowData){
					$('#productTypeId',editDialog).val(rowData.productTypeId);
					$('#productCode',editDialog).val(rowData.productTypeCode);
				}
			});
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		initChoose();
	   	$('#buyingPrice',editDialog).numberbox('setValue',0.0);
	   	$('#salePrice',editDialog).numberbox('setValue',0.0);
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
		//商品类别
		var productTypeId = $('#productType',editForm).combogrid('getValue');
		var productTypeName = $('#productType',editForm).combogrid('getText');
		if(productTypeId==''){
			$.messager.alert('提示','请选择商品类别','warning');
			return false;
		}
		$('#productTypeId',editForm).val(productTypeId);
		$('#productTypeName',editForm).val(productTypeName);
		//商品单位
		var unitId = $('#unit',editForm).combobox('getValue');
		var unitName = $('#unit',editForm).combobox('getText');
		$('#unitId',editForm).val(unitId);
		$('#unitName',editForm).val(unitName);
		//商品颜色
		var colorId = $('#color',editForm).combobox('getValue');
		var colorName = $('#color',editForm).combobox('getText');
		$('#colorId',editForm).val(colorId);
		$('#colorName',editForm).val(colorName);
		//商品尺码
		var sizeId = $('#size',editForm).combobox('getValue');
		var sizeName = $('#size',editForm).combobox('getText');
		$('#sizeId',editForm).val(sizeId);
		$('#sizeName',editForm).val(sizeName);
		$(defaultPackagingList).datagrid('endEdit', lastIndex);
		$(defaultPackagingList).datagrid('unselectAll');
		lastIndex = null;
		var rows =  $(defaultPackagingList).datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(row.qty==0){
				var msg = '第'+(i+1)+'行商品的数量为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
		}
		var defaultPackagingIdArray = new Array();
		var productIdArray = new Array();
		var qtyArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			defaultPackagingIdArray.push(rows[i].defaultPackagingId);
			productIdArray.push(rows[i].productId);
			qtyArray.push(rows[i].qty);
		}
		$('#defaultPackagingIds',editForm).val(defaultPackagingIdArray.join(LYS.join));
		$('#deleleIds',editForm).val(deleleIdArray.join(LYS.join));
		$('#productIds',editForm).val(productIdArray.join(LYS.join));
		$('#qtys',editForm).val(qtyArray.join(LYS.join));
		
		$(editDialog).mask({maskMsg:'正在保存'});
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
				$(editDialog).mask('hide');
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
	//打开
	var onOpen = function(productId){
		var url = 'dict/initProduct.do';
		var content ={productId:productId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				var productData = eval("("+data.productData+")");
				$('#productId',editDialog).val(productData.productId);
				$('#productCode',editDialog).val(productData.productCode);
				$('#productName',editDialog).val(productData.productName);
				
				$('#productType',editForm).combogrid('setValue',productData.productTypeId);
				$('#color',editForm).combobox('setValue',productData.colorId);
				$('#size',editForm).combobox('setValue',productData.sizeId);
				$('#unit',editForm).combobox('setValue',productData.unitId);
				
				$('#buyingPrice',editDialog).numberbox('setValue',productData.buyingPrice);
			   	$('#salePrice',editDialog).numberbox('setValue',productData.salePrice);
			   	$('#note',editDialog).val(productData.note);
			   	
			   	var defaultPackagingData = eval("("+data.defaultPackagingData+")");
				$(defaultPackagingList).datagrid('loadData',defaultPackagingData);
				$(editDialog).dialog('open');
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//修改
	var onUpdate = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(editForm).form('clear');
		initChoose();
		deleteIdArray = new Array();
		onOpen(selectRow.productId);
	 }
	//删除
	var onDelete = function(){
		if(selectRow==null){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'dict/deleteProduct.do';
				var content ={productId:selectRow.productId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						}
						$.messager.alert('提示','删除成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//------默认商品组装--------
	var defaultPackagingList = $('#defaultPackagingList',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var productList = $('#productList',selectDialog);
	var lastIndex=null;
	$(defaultPackagingList).datagrid({
	  title:'默认商品组装',
	  singleSelect:true,	
	  fit:true,
	  columns:[[
		    {field:'productCode',title:'商品编号',width:90,align:"center"},
			{field:'productName',title:'商品名称',width:200,align:"center"},
		    {field:'unitName',title:'单位',width:90,align:"center"},
		    {field:'sizeName',title:'规格',width:90,align:"center"},
		    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:0}}},
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:5}}},
		    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}}
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addProduct'+id,text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{id:'deleteProduct'+id,text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}}
	  ],
	  onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
	  },
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(defaultPackagingList).datagrid('endEdit', lastIndex);
			$(defaultPackagingList).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  }
	 });
	function setEditing(rowIndex){  
	    var editors = $(defaultPackagingList).datagrid('getEditors', rowIndex);  
	    var qtyEditor = editors[0];  
	    var priceEditor = editors[1];  
	    var amountEditor = editors[2];  
	    qtyEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    function calculate(rowIndex){  
	    	if(qtyEditor.target.val()==''){
	    		$(qtyEditor.target).numberbox('setValue',0.00);
	    	}
	        var cost = qtyEditor.target.val() * priceEditor.target.val();  
	        $(amountEditor.target).numberbox('setValue',cost);
	    }  
	}  
	//编辑框
	$(selectDialog).dialog({  
	    title: '选择商品',  
	    width:1000,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	var rows = $(productList).datagrid('getRows');
	    	if(rows.length!=0){
	    		$(productList).datagrid({url:LYS.ClearUrl});
	    	}
	    }
	});
	$(productList).datagrid({
		  fit:true,
		  cache: false, 
		  columns:[[
			    {field:'ck',checkbox:true},
			    {field:'productCode',title:'商品编号',width:90,align:"center"},
				{field:'productName',title:'商品名称',width:120,align:"center"},
			    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
			    {field:'unitName',title:'单位',width:90,align:"center"},
			    {field:'sizeName',title:'规格',width:90,align:"center"},
			    {field:'colorName',title:'颜色',width:90,align:"center"},
			    {field:'buyingPrice',title:'进价',width:90,align:"center"},
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProduct()}},
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					 $(selectDialog).dialog('close');
				}}
		  ]
	 });
	 var onSelectProduct = function(){
		 $(selectDialog).dialog('open');
	 }
	 //查询
	 $('#searchBtnSelectDialog',selectDialog).click(function(){
		 searchBtnSelect();
	 })
	 var searchBtnSelect = function(){
		var productCode = $('#productCodeSelectDialog',selectDialog).val();
		var productName = $('#productNameSelectDialog',selectDialog).val();
		var idArray = new Array();
		var rows = $(defaultPackagingList).datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			idArray.push(row.productId);
		}
		var productId = $.trim($('#productId',editDialog).val());
		if(productId!=''){
			idArray.push(productId);
		}
		
		var url = "dict/selectDefaultPackingProduct.do";
		var content = {productCode:productCode,productName:productName,ids:idArray.join(LYS.join)};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(productList).datagrid('loadData',eval("("+data.datagridData+")"));
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	//选择商品
	 var onSelectOKProduct = function(){
		 var rows = $(productList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择商品',"warning");
			 return;
		 }
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			 $(defaultPackagingList).datagrid('appendRow',{
				 defaultPackagingId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 qty:1,
				 price:row.buyingPrice,
				 amount:row.buyingPrice
			});
		}
		$(defaultPackagingList).datagrid('endEdit', lastIndex);
		$(defaultPackagingList).datagrid('unselectAll');
		lastIndex = null;
		$(selectDialog).dialog('close');
	 }
	//删除商品
	 var onDeleteProduct = function(){
		 var row = $(defaultPackagingList).datagrid('getSelected');
		 var rowIndex = $(defaultPackagingList).datagrid('getRowIndex',row);
		 if(row.defaultPackagingId!=''){
			 deleteIdArray.push(defaultPackagingId);
		 }
		 $(defaultPackagingList).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
	 }
  }
})(jQuery);   