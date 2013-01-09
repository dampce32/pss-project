// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.receiveInit = function() {
	  
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
	  
	  var warehouseData = null;
	  var bankData = null;
	  var employeeData = null;
	  var invoiceTypeData = null;
	  
	  //列表
	  $(viewList).datagrid({
		  fit:true,
		  columns:[[
		        {field:'ck',title:'选择',checkbox:true},
				{field:'receiveCode',title:'入库单号',width:120,align:"center"},
				{field:'receiveDate',title:'入库日期',width:80,align:"center"},
				{field:'deliverCode',title:'送货单号',width:120,align:"center"},
				{field:'warehouseName',title:'存入仓库',width:90,align:"center"},
				{field:'supplierName',title:'供应商',width:90,align:"center"},
				{field:'amount',title:'应付款',width:80,align:"center"},
				{field:'payAmount',title:'已付款',width:80,align:"center"},
				{field:'discountAmount',title:'优惠',width:80,align:"center"},
				{field:'noPayAmount',title:'欠款',width:80,align:"center",
					formatter: function(value,row,index){
						return row.amount-row.payAmount-row.discountAmount;
					}
				},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'note',title:'备注',width:90,align:"center"},
				{field:'shzt',title:'状态',width:80,align:"center",
					formatter: function(value,row,index){
						if(row.shzt==0){
							return '未审';
						}else if(row.shzt==1){
							return '已审';
						}
					}},
				{field:'payzt',title:'付款',width:80,align:"center"},
				{field:'invoiceTypeName',title:'开票信息',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
				{text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}},'-',
				{text:'已审',iconCls:'icon-edit',handler:function(){onUpdateShzt(1)}},'-',
				{text:'反审',iconCls:'icon-edit',handler:function(){onUpdateShzt(0)}}
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
		var receiveCode = $('#receiveCodeSearch',queryContent).val();
		
		var url = "inWarehouse/queryReceive.do";
		var content = {receiveCode:receiveCode,page:pageNumber,rows:pageSize};
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
		var url = "inWarehouse/getTotalCountReceive.do";
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
	    title: '采购入库单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    }
	}); 
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		initChoose();
		$('#amount',editForm).val(0);
		$('#discountAmount',editForm).val(0);
		$('#payAmount',editForm).val(0);
		$(editDialog).dialog('open');
	}
	
	var initChoose = function(){
		//供应商
		$('#supplier',editForm).combogrid({  
		    panelWidth:450,  
		    idField:'supplierId',  
		    textField:'supplierName',  
		    url:'dict/queryCombogridSupplier.do',  
		    columns:[[  
		        {field:'supplierCode',title:'供应商编号',width:120},
		        {field:'supplierName',title:'供应商名称',width:120}
		    ]]  
		});
		//仓库
		if(warehouseData==null){
			var url = 'dict/queryComboboxWarehouse.do';
			warehouseData = syncCallService(url);
		}
		$('#warehouse',editDialog).combobox({
			valueField:'warehouseId',
			textField:'warehouseName',
			width:150,
			data:warehouseData
		})
		//银行
		if(bankData==null){
			var url = 'dict/queryComboboxBank.do';
			bankData = syncCallService(url);
		}
		$('#bank',editDialog).combobox({
			valueField:'bankId',
			textField:'bankShortName',
			width:150,
			data:bankData
		})		  
		//经办人
		if(employeeData==null){
			var url = 'dict/queryComboboxEmployee.do';
			employeeData = syncCallService(url);
		}
		$('#employee',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:150,
			data:employeeData
		})
		//发票类型
		if(invoiceTypeData==null){
			var url = 'dict/queryComboboxInvoiceType.do';
			invoiceTypeData = syncCallService(url);
		}
		$('#invoiceType',editDialog).combobox({
			valueField:'invoiceTypeId',
			textField:'invoiceTypeName',
			width:150,
			data:invoiceTypeData
		})
	}
	//保存前的赋值操作
	var setValue = function(){
		//供应商
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		if(supplierId==''){
			$.messager.alert('提示','请选择供应商','warning');
			return false;
		}
		$('#supplierId',editForm).val(supplierId);
		//存放仓库
		var warehouseId = $('#warehouse',editForm).combobox('getValue');
		if(warehouseId==''){
			$.messager.alert('提示','请选择存放仓库','warning');
			return false;
		}
		$('#warehouseId',editForm).val(warehouseId);
		//收货日期
		var receiveDate = $('#receiveDate',editForm).val();
		if(receiveDate==''){
			$.messager.alert('提示','请选择收货日期','warning');
			return false;
		}
		//银行
		var bankId = $('#bank',editForm).combobox('getValue');
		if(bankId==''){
			$.messager.alert('提示','请选择银行','warning');
			return false;
		}
		$('#bankId',editForm).val(bankId);
		//经办人
		var employeeId = $('#employee',editForm).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		$('#employeeId',editForm).val(employeeId);
		//发票
		var invoiceTypeId = $('#invoiceType',editForm).combobox('getValue');
		if(invoiceTypeId==''){
			$.messager.alert('提示','请选择发票','warning');
			return false;
		}
		$('#invoiceTypeId',editForm).val(invoiceTypeId);
		//验证添加的商品行
		$(receiveDetail).datagrid('endEdit', lastIndex);
		 $(receiveDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(receiveDetail).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请选择添加商品','warning');
			return false;
		}
		for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			if(row.qty==0){
				var msg = '第'+(int+1)+'行商品的数量为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
		}
		var receiveDetailIdArray = new Array();
		var productIdArray = new Array();
		var colorIdArray = new Array();
		var qtyArray = new Array();
		var priceArray = new Array();
		var note1Array = new Array();
		var note2Array = new Array();
		var note3Array = new Array();
		for ( var i = 0; i < rows.length; i++) {
			receiveDetailIdArray.push(rows[i].receiveDetailId);
			productIdArray.push(rows[i].productId);
			colorIdArray.push(rows[i].colorId);
			qtyArray.push(rows[i].qty);
			priceArray.push(rows[i].price);
			note1Array.push(rows[i].note1);
			note2Array.push(rows[i].note2);
			note3Array.push(rows[i].note3);
		}
		
		delReceiveDetailIdArray = new Array();
		//统计原记录中被删除的记录
		for ( var int = 0; int < oldReceiveDetailIdArray.length; int++) {
			var haveDel = true;
			for(var i=0;i<rows.length;i++){
				if(oldReceiveDetailIdArray[int]==rows[i].receiveDetailId){
					haveDel = false;
					break;
				}
			}
			if(haveDel){
				delReceiveDetailIdArray.push(oldReceiveDetailIdArray[int]);
			}
		}
		$('#receiveDetailIds',editForm).val(receiveDetailIdArray.join(LYS.join));
		$('#delReceiveDetailIds',editForm).val(delReceiveDetailIdArray.join(LYS.join));
		$('#productIds',editForm).val(productIdArray.join(LYS.join));
		$('#colorIds',editForm).val(colorIdArray.join(LYS.join));
		$('#qtys',editForm).val(qtyArray.join(LYS.join));
		$('#prices',editForm).val(priceArray.join(LYS.join));
		$('#note1s',editForm).val(note1Array.join(LYS.join));
		$('#note2s',editForm).val(note2Array.join(LYS.join));
		$('#note3s',editForm).val(note3Array.join(LYS.join));
		
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'inWarehouse/saveReceive.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var data = result.data;
						//赋值receiveId，并加载receiveDetail
						onOpen(data.receiveId);
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
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		initChoose();
		onOpen(selectRow.receiveId);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要删除的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var int = 0; int < rows.length; int++) {
			idArray.push(rows[int].receiveId);
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/mulDelReceive.do';
				var content ={ids:idArray.join(LYS.join)};
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
	//修改审核状态
	var onUpdateShzt = function(shzt){
		var rows =  $(viewList).datagrid('getSelections');
		var msg = '';
		if(shzt==1){
			msg = '已审';
		}else{
			msg = '反审';
		}
		if(rows.length==0){
			$.messager.alert("提示","请选择要"+msg+"的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var int = 0; int < rows.length; int++) {
			idArray.push(rows[int].receiveId);
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?审核后单据不能再修改和删除，系统将进行库存和财务计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/mulUpdateShztReceive.do';
				var content ={ids:idArray.join(LYS.join),shzt:shzt};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//打开
	var onOpen = function(receiveId){
		var url = 'inWarehouse/initReceive.do';
		var content ={receiveId:receiveId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				var receiveData = eval("("+data.receiveData+")");
				$('#receiveId',editDialog).val(receiveData.receiveId);
				$('#receiveCode',editDialog).val(receiveData.receiveCode);
				$('#receiveDate',editDialog).val(receiveData.receiveDate);
				$('#deliverCode',editDialog).val(receiveData.deliverCode);
				if(receiveData.shzt==1){
					$('#shzt',editDialog).val('已审核');
				}else{
					$('#shzt',editDialog).val('未审核');
				}
				
				$('#supplier',editDialog).combogrid('setValue',receiveData.supplierId);
				$('#warehouse',editDialog).combobox('setValue',receiveData.warehouseId);
				
				$('#amount',editDialog).val(receiveData.amount);
				$('#discountAmount',editDialog).val(receiveData.discountAmount);
				$('#payAmount',editDialog).val(receiveData.payAmount);
				
				$('#bank',editDialog).combobox('setValue',receiveData.bankId);
				$('#employee',editDialog).combobox('setValue',receiveData.employeeId);
				$('#invoiceType',editDialog).combobox('setValue',receiveData.invoiceTypeId);
				$('#note',editDialog).val(receiveData.note);
				
				var detailData = eval("("+data.detailData+")");
				$(receiveDetail).datagrid('loadData',detailData);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//-----收货明细----------
	var receiveDetail = $('#receiveDetail',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var productList = $('#productList');
	var lastIndex=null;
	var colors=PSS.getColorList();
	function colorFormatter(value){
		for(var i=0; i<colors.length; i++){
			if (colors[i].colorId == value) return colors[i].colorName;
		}
		return value;
	}
	var oldReceiveDetailIdArray = new Array();
	$(receiveDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  columns:[[
		    {field:'productCode',title:'商品编号',width:90,align:"center"},
			{field:'productName',title:'商品名称',width:200,align:"center"},
		    {field:'unitName',title:'单位',width:90,align:"center"},
		    {field:'sizeName',title:'规格',width:90,align:"center"},
		    {field:'colorId',title:'颜色',width:90,align:"center",editor:{type:'combobox',options:{valueField:'colorId',
				textField:'colorName',
				data:colors}},formatter:colorFormatter},
		    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2,onChange:function(newValue,oldValue){
		    	 var totalAmount = 0 ;
				 var rows =  $(receiveDetail).datagrid('getRows');
				 var row = $(receiveDetail).datagrid('getSelected');
				 var rowIndex = $(receiveDetail).datagrid('getRowIndex',row); 
				 for ( var int = 0; int < rows.length; int++) {
					 if(int!=rowIndex){
						row = rows[int];
						totalAmount += parseFloat(row.amount);
					 }
				}
				totalAmount+=parseFloat(newValue); 
		    	$('#amount',editForm).val(totalAmount);
		    	$('#amount',editForm).change();
		    }}}},
		    {field:'note1',title:'备注1',width:120,align:"center",editor:{type:'text'}},
		    {field:'note2',title:'备注2',width:120,align:"center",editor:{type:'text'}},
		    {field:'note3',title:'备注3',width:120,align:"center",editor:{type:'text'}}
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}},'-',
			{text:'保存',iconCls:'icon-save',handler:function(){
					onSave();
				}
			},'-',{text:'退出',iconCls:'icon-cancel',handler:function(){
					$(editDialog).dialog('close');
					$(editForm).form('clear');
					$(receiveDetail).datagrid({url:LYS.ClearUrl});
				}
			}
	  ],
	  onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
	  },
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(receiveDetail).datagrid('endEdit', lastIndex);
			$(receiveDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  },onLoadSuccess:function(data){
			var rows = data.rows;
			oldReceiveDetailIdArray = new Array();
			for ( var int = 0; int < rows.length; int++) {
				oldReceiveDetailIdArray.push(rows[int].receiveDetailId);
			}
		}
	 });
	function setEditing(rowIndex){  
	    var editors = $(receiveDetail).datagrid('getEditors', rowIndex);  
	    var qtyEditor = editors[1];  
	    var priceEditor = editors[2];  
	    var amountEditor = editors[3];  
	    qtyEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    priceEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    function calculate(rowIndex){  
	        var cost = qtyEditor.target.val() * priceEditor.target.val();  
	        $(amountEditor.target).numberbox('setValue',cost);
	    }  
	}  
	//编辑框
	$(selectDialog).dialog({  
	    title: '选择商品',  
	    width:800,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false
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
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'选择',iconCls:'icon-save',handler:function(){onSelectOKProduct()}},
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectProduct();
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
		
		var url = "dict/selectProduct.do";
		var content = {productCode:productCode,productName:productName};
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
		 for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			 $(receiveDetail).datagrid('appendRow',{
				 receiveDetailId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 colorId:row.colorId,
				 qty:0,
				 price:0,
				 amount:0,
				 note1:'',
				 note2:'',
				 note3:''
			});
		}
		 $(receiveDetail).datagrid('endEdit', lastIndex);
		 $(receiveDetail).datagrid('unselectAll');
		lastIndex = null;
		onExitSelectProduct();
	 }
	 //退出选择商品界面
	 var onExitSelectProduct = function(){
		 $(selectDialog).dialog('close');
		 $(productList).datagrid({url:LYS.ClearUrl});
	 }
	 //删除商品
	 var onDeleteProduct = function(){
		 var row = $(receiveDetail).datagrid('getSelected');
		 var rowIndex = $(receiveDetail).datagrid('getRowIndex',row);
		 $(receiveDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 //统计删除后的应付金额
		 var totalAmount = 0 ;
		 var rows =  $(receiveDetail).datagrid('getRows');
		 for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			totalAmount += parseFloat(row.amount);
		}
		$('#amount',editForm).val(totalAmount);
		$('#amount',editForm).change();
	 }
	 //应付金额发生改变
	 $('#amount',editForm).change( function() {
		 var amount = $('#amount',editForm).val();
		 var discountAmount = $('#discountAmount',editForm).val();
		 $('#payAmount',editForm).val(amount-discountAmount);
	});
	 $('#discountAmount',editForm).change( function() {
		 var amount = $('#amount',editForm).val();
		 var discountAmount = $('#discountAmount',editForm).val();
		 $('#payAmount',editForm).val(amount-discountAmount);
	});
	 
  }
})(jQuery);   